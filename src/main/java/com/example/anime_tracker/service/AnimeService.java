package com.example.anime_tracker.service;

import com.example.anime_tracker.dto.AnimeDTO;
import com.example.anime_tracker.model.Anime;
import com.example.anime_tracker.model.Genre;
import com.example.anime_tracker.model.JikanResponse;
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

    private static final String API_BASE_URL = "https://api.jikan.moe/v4/seasons/upcoming";
    private static final String REDIS_KEY = "upcoming_anime";

    @Autowired
    public AnimeService(RestTemplate restTemplate, RedisTemplate<String, List<AnimeDTO>> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    public List<AnimeDTO> getUpcomingEpisodes() {

        List<AnimeDTO> cached = redisTemplate.opsForValue().get(REDIS_KEY);
        if (cached != null && !cached.isEmpty()) return cached;

        List<AnimeDTO> result = new ArrayList<>();
        int page = 1;
        boolean hasNextPage = true;

        while (hasNextPage) {
            String url = API_BASE_URL + "?page=" + page;
            JikanResponse response = restTemplate.getForObject(url, JikanResponse.class);

            if (response != null && response.getData() != null) {
                List<AnimeDTO> filtered = response.getData().stream()
                        .filter(anime -> {
                            // anime.getAiredFrom() is LocalDate now
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
