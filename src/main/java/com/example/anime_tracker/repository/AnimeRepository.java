package com.example.anime_tracker.repository;

import com.example.anime_tracker.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    // Optional: add custom search methods later
}

