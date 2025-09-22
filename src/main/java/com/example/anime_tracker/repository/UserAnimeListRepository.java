package com.example.anime_tracker.repository;

import com.example.anime_tracker.model.UserAnimeList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnimeListRepository extends JpaRepository<UserAnimeList, Long> {
    List<UserAnimeList> findByUserId(Long userId);
}
