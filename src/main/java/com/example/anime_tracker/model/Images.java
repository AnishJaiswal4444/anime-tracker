package com.example.anime_tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class Images {

    @JsonProperty("jpg")
    private ImageDetails jpg;

    @JsonProperty("webp")
    private ImageDetails webp;

    public ImageDetails getJpg() { return jpg; }
    public void setJpg(ImageDetails jpg) { this.jpg = jpg; }

    public ImageDetails getWebp() { return webp; }
    public void setWebp(ImageDetails webp) { this.webp = webp; }

    @Embeddable
    public static class ImageDetails {
        @JsonProperty("image_url")
        private String imageUrl;

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }
}
