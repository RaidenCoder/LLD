import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import repository.URLRepository;
import strategy.KeyGenerationStrategy;
import observer.Observer;
import enums.EventType;
import model.ShortenedURL;

class URLShortenerService {
    private static final URLShortenerService INSTANCE = new URLShortenerService();
    private URLRepository urlRepository;
    private KeyGenerationStrategy keyGenerationStrategy;
    private String domain;
    private static final int MAX_RETRIES = 10;
    private final List<Observer> observers = new ArrayList<>();

    // Private constructor for Singleton
    private URLShortenerService() {
    }

    public static URLShortenerService getInstance() {
        return INSTANCE;
    }

    // Configure the service with dependencies(Dependency Injection)
    public void configure(String domain, URLRepository repository, KeyGenerationStrategy strategy) {
        this.domain = domain;
        this.urlRepository = repository;
        this.keyGenerationStrategy = strategy;
    }

    public String shorten(String longURL) {
        // Check if we have already shortened the url
        Optional<String> existingURL = urlRepository.findKeyByLongURL(longURL);
        if (existingURL.isPresent()) {
            return domain + existingURL.get();
        }

        // Generate a new key, handling possible collisions
        ShortenedURL shortUrl = claimUniqueKey(longURL);

        notifyObservers(EventType.URL_CREATED, shortUrl);

        return domain + shortUrl.getShortKey();
    }

    // New overload on URLShortenerService, existing shorten() untouched
    public String shortenWithAlias(String longURL, String alias) {
        ShortenedURL url = new ShortenedURL.Builder(longURL, alias).build();
        if (!urlRepository.saveIfAbsent(url)) {
            throw new RuntimeException("Alias already taken: " + alias);
        }
        notifyObservers(EventType.URL_CREATED, url);
        return domain + alias;
    }

    // private String generateUniqueKey() {
    // for(int i = 0; i < MAX_RETRIES; i++) {
    // //The ID is passed but may be ignored by some strategies (like random)
    // String potentialKey =
    // keyGenerationStrategy.generateKey(urlRepository.getNextId());
    // if(!urlRepository.existsByKey(potentialKey)) {
    // return potentialKey; //Found a unique key
    // }
    // }

    // //If we reach here, we failed to generate a unique key after several
    // attempts.
    // throw new RuntimeException("failed to generate a unqiue key after " +
    // MAX_RETRIES + "attempts");
    // }

    private ShortenedURL claimUniqueKey(String longURL) {
        for (int i = 0; i < MAX_RETRIES; i++) {
            String potentialKey = keyGenerationStrategy.generateKey(urlRepository.getNextId());
            ShortenedURL candidate = new ShortenedURL.Builder(longURL, potentialKey).build();
            if (urlRepository.saveIfAbsent(candidate)) {
                return candidate;
            }
        }

        throw new RuntimeException("Failed to claim a unique short key");
    }

    public Optional<String> resolve(String shortURL) {
        if (!shortURL.startsWith(domain)) {
            return Optional.empty();
        }
        String shortKey = shortURL.replace(domain, "");
        Optional<ShortenedURL> found = urlRepository.findByKey(shortKey);
        if (found.isEmpty() || found.get().isExpired()) {
            return Optional.empty(); // expired links behave like missing links
        }
        notifyObservers(EventType.URL_ACCESSED, found.get());
        return Optional.of(found.get().getLongURL());
    }       


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(EventType type, ShortenedURL url) {
        for (Observer observer : observers) {
            observer.update(type, url);
        }
    }
}