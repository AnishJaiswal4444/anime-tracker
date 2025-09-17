package com.example.anime_tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class Aired {
    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to; // e.g., null or another date

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
}
