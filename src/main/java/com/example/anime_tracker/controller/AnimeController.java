package com.example.anime_tracker.controller;

import com.example.anime_tracker.model.JikanResponse;
import com.example.anime_tracker.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anime")
public class AnimeController {
    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/upcoming")
    public JikanResponse getUpcomingEpisodes() {
        return animeService.getUpcomingEpisodes();
    }
}