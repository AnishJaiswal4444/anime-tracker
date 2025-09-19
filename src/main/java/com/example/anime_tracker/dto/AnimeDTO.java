package com.example.anime_tracker.dto;

public class AnimeDTO {

    private String titleEnglish;
    private String status;
    private String imageUrl;
    private String airedFrom;    // formatted string for frontend
    private String genres;       // comma-separated

    public String getTitleEnglish() { return titleEnglish; }
    public void setTitleEnglish(String titleEnglish) { this.titleEnglish = titleEnglish; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getAiredFrom() { return airedFrom; }
    public void setAiredFrom(String airedFrom) { this.airedFrom = airedFrom; }

    public String getGenres() { return genres; }
    public void setGenres(String genres) { this.genres = genres; }
}
