package com.example.anime_tracker.service;

import com.example.anime_tracker.dto.AnimeDTO;
import com.example.anime_tracker.dto.UserAnimeListDTO;
import com.example.anime_tracker.model.*;
import com.example.anime_tracker.repository.AnimeRepository;
import com.example.anime_tracker.repository.UserAnimeListRepository;
import com.example.anime_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAnimeListService {

    private final UserRepository userRepository;
    private final UserAnimeListRepository userAnimeListRepository;
    private final AnimeRepository animeRepository;

    public UserAnimeListService(UserRepository userRepository,
                                UserAnimeListRepository userAnimeListRepository,
                                AnimeRepository animeRepository) {
        this.userRepository = userRepository;
        this.userAnimeListRepository = userAnimeListRepository;
        this.animeRepository = animeRepository;
    }

    @Transactional
    public void createDefaultListsForUser(User user) {
        for (ListType type : ListType.values()) {
            UserAnimeList list = new UserAnimeList(user, type);
            userAnimeListRepository.saveAndFlush(list);
        }
    }

    @Transactional
    public void addAnimeToList(Long userId, Long animeId, ListType listType) {
        UserAnimeList list = userAnimeListRepository.findByUser_IdAndListType(userId, listType)
                .orElseThrow(() -> new RuntimeException("List not found"));

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("Anime not found"));

        list.getAnimes().add(anime);
        userAnimeListRepository.save(list);
    }
    @Transactional(readOnly = true)
    public Set<AnimeDTO> getAnimeForList(Long userId, ListType listType) {
        UserAnimeList list = userAnimeListRepository
                .findByUser_IdAndListType(userId, listType)
                .orElseThrow(() -> new RuntimeException("List not found"));
        return list.getAnimes().stream()
                .map(anime -> {
                    AnimeDTO dto = new AnimeDTO();
                    dto.setTitleEnglish(anime.getTitle());  // assuming title field in Anime entity
                    dto.setImageUrl(anime.getImageUrl());      // assuming image field in Anime entity
                    // leave other fields null for now or populate if needed
                    return dto;
                })
                .collect(Collectors.toSet()); // returns full Anime objects
    }

    private UserAnimeListDTO toDTO(UserAnimeList list) {
        UserAnimeListDTO dto = new UserAnimeListDTO();
        dto.setId(list.getId());
        dto.setName(list.getListType().name());
        dto.setAnimeIds(list.getAnimes().stream().map(anime -> anime.getMalId()).collect(Collectors.toSet()));
        return dto;
    }
}
