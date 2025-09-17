package com.example.anime_tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class Aired {
    @JsonProperty("from")
    private String from;

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
}
