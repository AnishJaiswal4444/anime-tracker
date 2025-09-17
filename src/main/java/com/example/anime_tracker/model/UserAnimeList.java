package com.example.anime_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UserAnimeList {
    @Id
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Anime anime;
    private String status; // WATCHING, COMPLETED, PLAN_TO_WATCH
    private String addedDate;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Anime getAnime() { return anime; }
    public void setAnime(Anime anime) { this.anime = anime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAddedDate() { return addedDate; }
    public void setAddedDate(String addedDate) { this.addedDate = addedDate; }
}
