package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Anime {
    @Id
    private Long malId; // Matches Jikan's mal_id
    private String title;
    private String url;
    private String synopsis;
    private String airingStart; // For upcoming episodes
    private Integer episodes; // Total episodes
    private String status; // e.g., "Airing", "Finished"

    // Getters and setters
    public Long getMalId() { return malId; }
    public void setMalId(Long malId) { this.malId = malId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public String getAiringStart() { return airingStart; }
    public void setAiringStart(String airingStart) { this.airingStart = airingStart; }
    public Integer getEpisodes() { return episodes; }
    public void setEpisodes(Integer episodes) { this.episodes = episodes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
