package com.example.anime_tracker.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_anime_lists")
public class UserAnimeList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ListType listType;

    @ManyToMany
    @JoinTable(
            name = "user_anime_list_anime",
            joinColumns = @JoinColumn(name = "user_anime_list_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_id")
    )
    private Set<Anime> animes = new HashSet<>();

    // Constructors
    public UserAnimeList() {}
    public UserAnimeList(User user, ListType listType) {
        this.user = user;
        this.listType = listType;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public ListType getListType() { return listType; }
    public void setListType(ListType listType) { this.listType = listType; }
    public Set<Anime> getAnimes() { return animes; }
    public void setAnimes(Set<Anime> animes) { this.animes = animes; }
}
