package repository;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import model.ShortenedURL;

import java.util.Map;
import java.util.Optional;



public class InMemoryURLRepository implements URLRepository {
    private final Map<String, ShortenedURL> keyToUrlMap = new ConcurrentHashMap<>();
    private final Map<String, String> longUrlToKeyMap = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    // @Override
    // public void save(ShortenedURL url) {
    //     keyToUrlMap.put(url.getShortKey(), url);
    //     longUrlToKeyMap.put(url.getLongURL(), url.getShortKey());
    // }
    /* 
        This save is not thread safe as in URLShortnerService:
        - The check and the write are separate steps, so two threads can both pass the 
          check for the same key before either saves.
        - Thread B's save() overwrites Thread A's entry. The key aB3xY9 now resolves to 
          urlB only. Thread A already returned short.ly/aB3xY9 to its caller, but that 
          short link now redirects to the wrong destination. One user's link silently points
          at another user's URL.
        - The fix is an atomic claim instead of check-then-write. Add a saveIfAbsent() to the 
          repository that uses an atomic putIfAbsent, and have key generation retry when the 
          claim fails. Thread A claims aB3xY9 and keeps it. Thread B's saveIfAbsent() returns 
          false, so it generates a new candidate and claims that. Each key ends up owned by 
          one URL.
    */

    @Override
    public boolean saveIfAbsent(ShortenedURL url) {
        if(keyToUrlMap.putIfAbsent(url.getShortKey(), url) == null) {
            longUrlToKeyMap.put(url.getLongURL(), url.getShortKey());
            return true;
        }

        return false;
    }

    @Override
    public Optional<ShortenedURL> findByKey(String key) {
        ShortenedURL found = keyToUrlMap.get(key);
        return Optional.ofNullable(found);
    }

    @Override
    public Optional<String> findKeyByLongURL(String longURL) {
        String found = longUrlToKeyMap.get(longURL);
        return Optional.ofNullable(found);
    }

    @Override
    public long getNextId() {
        return idCounter.getAndIncrement();
    }

    @Override
    public boolean existsByKey(String key) {
        return keyToUrlMap.containsKey(key);
    }
}