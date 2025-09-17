package com.example.anime_tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Anime {
    @Id
    @JsonProperty("mal_id")
    private Long malId;

    private String title;
    private String url;
    private String synopsis;
    private Integer episodes;
    private String status;

    @Embedded
    @JsonProperty("aired")
    private Aired aired; // nested object for airing dates

    // --- getters and setters ---
    public Long getMalId() { return malId; }
    public void setMalId(Long malId) { this.malId = malId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public Integer getEpisodes() { return episodes; }
    public void setEpisodes(Integer episodes) { this.episodes = episodes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Aired getAired() { return aired; }
    public void setAired(Aired aired) { this.aired = aired; }
}
