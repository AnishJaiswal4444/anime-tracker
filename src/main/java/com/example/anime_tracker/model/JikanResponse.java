package com.example.anime_tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JikanResponse {
    private Pagination pagination;
    private List<Anime> data;

    public Pagination getPagination() { return pagination; }
    public void setPagination(Pagination pagination) { this.pagination = pagination; }
    public List<Anime> getData() { return data; }
    public void setData(List<Anime> data) { this.data = data; }

    public static class Pagination {
        @JsonProperty("last_visible_page")
        private int lastVisiblePage;
        @JsonProperty("has_next_page")
        private boolean hasNextPage;
        @JsonProperty("current_page")
        private int currentPage;
        private Items items;

        // Getters and setters
        public int getLastVisiblePage() {
            return lastVisiblePage;
        }

        public void setLastVisiblePage(int lastVisiblePage) {
            this.lastVisiblePage = lastVisiblePage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public Items getItems() {
            return items;
        }

        public void setItems(Items items) {
            this.items = items;
        }
    }

    public static class Items {
        private int count;
        private int total;
        @JsonProperty("per_page")
        private int perPage;

        // Getters and setters
        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPerPage() {
            return perPage;
        }

        public void setPerPage(int perPage) {
            this.perPage = perPage;
        }
    }
}
