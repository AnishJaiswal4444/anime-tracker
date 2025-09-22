package com.example.anime_tracker.service;

import com.example.anime_tracker.dto.AnimeDTO;
import com.example.anime_tracker.model.Anime;
import com.example.anime_tracker.model.Genre;
import com.example.anime_tracker.model.JikanResponse;
import com.example.anime_tracker.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    private final RestTemplate restTemplate;
    private final RedisTemplate<String, List<AnimeDTO>> redisTemplate;
    private final AnimeRepository animeRepository;

    private static final String API_BASE_URL = "https://api.jikan.moe/v4/seasons/upcoming";
    private static final String REDIS_KEY = "upcoming_anime";

    @Autowired
    public AnimeService(RestTemplate restTemplate,
                        RedisTemplate<String, List<AnimeDTO>> redisTemplate,
                        AnimeRepository animeRepository) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
        this.animeRepository = animeRepository;
    }

    public List<AnimeDTO> getUpcomingEpisodes() {

        // 1️⃣ Check Redis cache first
        List<AnimeDTO> cached = redisTemplate.opsForValue().get(REDIS_KEY);
        if (cached != null && !cached.isEmpty()) return cached;

        List<AnimeDTO> result = new ArrayList<>();
        int page = 1;
        boolean hasNextPage = true;

        while (hasNextPage) {
            String url = API_BASE_URL + "?page=" + page;
            JikanResponse response = restTemplate.getForObject(url, JikanResponse.class);

            if (response != null && response.getData() != null) {

                // Save Anime to DB
                for (Anime anime : response.getData()) {
                    if (!animeRepository.existsById(anime.getMalId())) {
                        animeRepository.save(anime);
                    }
                }

                List<AnimeDTO> filtered = response.getData().stream()
                        .filter(anime -> {
                            LocalDate d = anime.getAiredFrom();
                            return d == null || d.isAfter(LocalDate.now());
                        })
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());

                result.addAll(filtered);
                hasNextPage = response.getPagination() != null && response.getPagination().isHasNextPage();
                page++;

                try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
            } else break;
        }

        // Cache the result for 10 minutes
        redisTemplate.opsForValue().set(REDIS_KEY, result, 10, TimeUnit.MINUTES);

        return result;
    }

    private AnimeDTO mapToDTO(Anime anime) {
        AnimeDTO dto = new AnimeDTO();
        dto.setTitleEnglish(anime.getTitleEnglish() != null ? anime.getTitleEnglish() : anime.getTitle());
        dto.setStatus(anime.getStatus());
        dto.setImageUrl(anime.getImageUrl());

        if (anime.getAiredFrom() != null) {
            dto.setAiredFrom(anime.getAiredFrom().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }

        if (anime.getGenres() != null && !anime.getGenres().isEmpty()) {
            String genreNames = anime.getGenres().stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining(", "));
            dto.setGenres(genreNames);
        }

        return dto;
    }
}
