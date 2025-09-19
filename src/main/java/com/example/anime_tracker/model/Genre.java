package com.example.anime_tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "genres")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {

    // Use mal_id from Jikan as the PK for simplicity and to make deserialization easy.
    @Id
    @Column(name = "mal_id")
    @JsonProperty("mal_id")
    private Long malId;

    private String name;

    // --- getters & setters ---
    public Long getMalId() { return malId; }
    public void setMalId(Long malId) { this.malId = malId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // equals & hashCode based on malId so Set<Genre> works reliably
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre g = (Genre) o;
        return malId != null && malId.equals(g.getMalId());
    }

    @Override
    public int hashCode() {
        return malId != null ? malId.hashCode() : 0;
    }
}
