package com.example.anime_tracker.service;

import com.example.anime_tracker.model.Anime;
import com.example.anime_tracker.model.JikanResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AnimeService {
    private final RestTemplate restTemplate;

    @Autowired
    public AnimeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Anime> getUpcomingEpisodes() {
        String url = "https://api.jikan.moe/v4/seasons/upcoming";
        JikanResponse response = restTemplate.getForObject(url, JikanResponse.class);
        return response != null ? response.getData() : List.of();
    }
}
