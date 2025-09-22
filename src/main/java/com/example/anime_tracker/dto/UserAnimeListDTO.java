package com.example.anime_tracker.dto;

import java.util.Set;

public class UserAnimeListDTO {
    private Long id;
    private String name;
    private Set<Long> animeIds; // IDs of Anime in this list

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Long> getAnimeIds() { return animeIds; }
    public void setAnimeIds(Set<Long> animeIds) { this.animeIds = animeIds; }
}
