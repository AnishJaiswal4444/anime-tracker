package com.example.anime_tracker.controller;

import com.example.anime_tracker.dto.UserAnimeListDTO;
import com.example.anime_tracker.service.UserAnimeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-anime-lists")
public class UserAnimeListController {

    @Autowired
    private UserAnimeListService userAnimeListService;

    @GetMapping("/{userId}")
    public List<UserAnimeListDTO> getLists(@PathVariable Long userId) {
        return userAnimeListService.getListsForUser(userId);
    }

    @PostMapping("/{userId}")
    public UserAnimeListDTO createList(@PathVariable Long userId, @RequestParam String name) {
        return userAnimeListService.createList(userId, name);
    }

    @PostMapping("/{listId}/add-anime/{animeId}")
    public UserAnimeListDTO addAnime(@PathVariable Long listId, @PathVariable Long animeId) {
        return userAnimeListService.addAnimeToList(listId, animeId);
    }
}
