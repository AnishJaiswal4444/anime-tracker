package com.example.anime_tracker.repository;

import com.example.anime_tracker.model.ListType;
import com.example.anime_tracker.model.UserAnimeList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAnimeListRepository extends JpaRepository<UserAnimeList, Long> {
    Optional<UserAnimeList> findByUser_IdAndListType(Long userId, ListType listType);
}
