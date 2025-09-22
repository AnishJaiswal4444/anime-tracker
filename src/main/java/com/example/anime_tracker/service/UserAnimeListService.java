package com.example.anime_tracker.service;

import com.example.anime_tracker.dto.UserAnimeListDTO;
import com.example.anime_tracker.model.Anime;
import com.example.anime_tracker.model.User;
import com.example.anime_tracker.model.UserAnimeList;
import com.example.anime_tracker.repository.AnimeRepository;
import com.example.anime_tracker.repository.UserAnimeListRepository;
import com.example.anime_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAnimeListService {

    @Autowired
    private UserAnimeListRepository userAnimeListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnimeRepository animeRepository;

    public List<UserAnimeListDTO> getListsForUser(Long userId) {
        return userAnimeListRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserAnimeListDTO createList(Long userId, String name) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserAnimeList list = new UserAnimeList();
        list.setName(name);
        list.setUser(user);
        UserAnimeList saved = userAnimeListRepository.save(list);
        return toDTO(saved);
    }

    public UserAnimeListDTO addAnimeToList(Long listId, Long animeId) {
        UserAnimeList list = userAnimeListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found"));
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("Anime not found"));
        list.getAnimes().add(anime);
        userAnimeListRepository.save(list);
        return toDTO(list);
    }

    private UserAnimeListDTO toDTO(UserAnimeList list) {
        UserAnimeListDTO dto = new UserAnimeListDTO();
        dto.setId(list.getId());
        dto.setName(list.getName());
        Set<Long> animeIds = list.getAnimes().stream().map(Anime::getMalId).collect(Collectors.toSet());
        dto.setAnimeIds(animeIds);
        return dto;
    }
}
