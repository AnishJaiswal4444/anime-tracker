package com.example.anime_tracker.controller;

import com.example.anime_tracker.dto.AnimeDTO;
import com.example.anime_tracker.dto.UserAnimeListDTO;
import com.example.anime_tracker.model.Anime;
import com.example.anime_tracker.model.ListType;
import com.example.anime_tracker.service.UserAnimeListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user-anime-lists")
public class UserAnimeListController {

    private final UserAnimeListService userAnimeListService;

    public UserAnimeListController(UserAnimeListService userAnimeListService) {
        this.userAnimeListService = userAnimeListService;
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addAnimeToList(
            @PathVariable Long userId,
            @RequestParam Long animeId,
            @RequestParam ListType listType) {
        userAnimeListService.addAnimeToList(userId, animeId, listType);
        return ResponseEntity.ok("Anime added to " + listType + " list");
    }
    @GetMapping("/{userId}/{listType}")
    public Set<AnimeDTO> getAnimeForList(
            @PathVariable Long userId,
            @PathVariable ListType listType) {
        return userAnimeListService.getAnimeForList(userId, listType);
    }
}
