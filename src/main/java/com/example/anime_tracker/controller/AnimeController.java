package com.example.anime_tracker.controller;

import com.example.anime_tracker.model.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.anime_tracker.service.AnimeService;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
public class AnimeController {
    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/upcoming")
    public List<Anime> getUpcomingEpisodes() {
        return animeService.getUpcomingEpisodes();
    }
}
