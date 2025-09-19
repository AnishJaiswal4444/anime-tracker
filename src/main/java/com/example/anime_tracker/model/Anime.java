package com.example.anime_tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "anime")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Anime {

    @Id
    @Column(name = "mal_id")
    @JsonProperty("mal_id")
    private Long malId;

    private String title;

    @JsonProperty("title_english")
    private String titleEnglish;

    private String status;

    // persisted field
    @Column(name = "aired_from")
    private LocalDate airedFrom;

    // persisted field
    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "anime_genres",
            joinColumns = @JoinColumn(name = "anime_mal_id", referencedColumnName = "mal_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_mal_id", referencedColumnName = "mal_id")
    )
    private Set<Genre> genres = new HashSet<>();

    // --- JSON -> entity helpers (Jackson will call these) ---
    @JsonProperty("aired")
    private void unpackAired(Aired aired) {
        if (aired != null && aired.getFrom() != null) {
            String raw = aired.getFrom();
            try {
                // Handles timestamps with offsets: "2025-10-01T00:00:00+00:00"
                this.airedFrom = OffsetDateTime.parse(raw).toLocalDate();
            } catch (DateTimeParseException ex) {
                try {
                    // fallback if it's just yyyy-MM-dd
                    this.airedFrom = LocalDate.parse(raw);
                } catch (Exception ignored) {
                    // leave null on failure
                }
            }
        }
    }

    @JsonProperty("images")
    private void unpackImages(Images images) {
        if (images != null && images.getJpg() != null) {
            this.imageUrl = images.getJpg().getImageUrl();
        }
    }

    // --- getters & setters ---
    public Long getMalId() { return malId; }
    public void setMalId(Long malId) { this.malId = malId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTitleEnglish() { return titleEnglish; }
    public void setTitleEnglish(String titleEnglish) { this.titleEnglish = titleEnglish; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getAiredFrom() { return airedFrom; }
    public void setAiredFrom(LocalDate airedFrom) { this.airedFrom = airedFrom; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Set<Genre> getGenres() { return genres; }
    public void setGenres(Set<Genre> genres) { this.genres = genres; }

    // --- small helper classes used only for JSON mapping ---
    private static class Aired {
        private String from;
        public String getFrom() { return from; }
        public void setFrom(String from) { this.from = from; }
    }

    private static class Images {
        private ImageDetails jpg;
        public ImageDetails getJpg() { return jpg; }
        public void setJpg(ImageDetails jpg) { this.jpg = jpg; }

        private static class ImageDetails {
            @JsonProperty("image_url")
            private String imageUrl;
            public String getImageUrl() { return imageUrl; }
            public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        }
    }
}
