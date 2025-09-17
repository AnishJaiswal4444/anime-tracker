package com.example.anime_tracker.service;

import com.example.anime_tracker.model.Anime;
import com.example.anime_tracker.model.JikanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimeService {
    private final RestTemplate restTemplate;
    private static final String API_BASE_URL = "https://api.jikan.moe/v4/seasons/upcoming";
    private static final LocalDateTime CURRENT_DATE = LocalDateTime.of(2025, 9, 17, 0, 0);

    @Autowired
    public AnimeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JikanResponse getUpcomingEpisodes() {
        List<Anime> allAnime = new ArrayList<>();
        JikanResponse finalResponse = new JikanResponse();
        int page = 1;
        boolean hasNextPage = true;

        while (hasNextPage) {
            String url = API_BASE_URL + "?page=" + page;
            JikanResponse response = restTemplate.getForObject(url, JikanResponse.class);

            if (response != null && response.getData() != null) {
                // Filter anime for upcoming (air date after current date or null)
                List<Anime> filteredAnime = response.getData().stream()
                        .filter(anime -> {
                            String airedFrom = anime.getAired() != null ? anime.getAired().getFrom() : null;
                            if (airedFrom == null) {
                                return true; // Include anime with no air date (TBA)
                            }
                            try {
                                LocalDateTime airDate = LocalDateTime.parse(airedFrom.replace("Z", ""));
                                return airDate.isAfter(CURRENT_DATE);
                            } catch (Exception e) {
                                return true; // Include if parsing fails (treat as TBA)
                            }
                        })
                        .toList();

                allAnime.addAll(filteredAnime);

                // Update pagination metadata from the last response
                finalResponse.setPagination(response.getPagination());
                hasNextPage = response.getPagination().isHasNextPage();
                page++;

                // Respect Jikan API rate limit (3 requests/second)
                try {
                    Thread.sleep(1000); // 1-second delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {
                break;
            }
        }

        finalResponse.setData(allAnime);
        return finalResponse;
    }
}