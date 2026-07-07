package model;

import java.time.LocalDateTime;

public class ShortenedURL {
    private final String longURL;
    private final String shortKey;
    private final LocalDateTime creationDate;
    private final LocalDateTime expiryDate;

    private ShortenedURL(Builder builder) {
        this.longURL = builder.longURL;
        this.shortKey = builder.shortKey;
        this.creationDate = builder.creationDate;
        this.expiryDate = builder.expiryDate;
    }

    //Getters
    public String getLongURL() { return longURL; }
    public String getShortKey() { return shortKey; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public LocalDateTime getExpiryDate() { return expiryDate; }

    public boolean isExpired() {
        return expiryDate != null && LocalDateTime.now().isAfter(expiryDate);
    }

    //--- Builder Class ---
    public static class Builder {
        private final String longURL;
        private final String shortKey;
        private LocalDateTime creationDate;
        private LocalDateTime expiryDate;

        public Builder(String longURL, String shortKey) {
            this.longURL = longURL;
            this.shortKey = shortKey;
            this.creationDate = LocalDateTime.now();
            this.expiryDate = null;
        }

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder expiryDate(LocalDateTime expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public ShortenedURL build() {
            return new ShortenedURL(this);
        }
    }
}