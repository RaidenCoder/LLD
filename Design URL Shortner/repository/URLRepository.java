package repository;

import java.util.Optional;
import model.ShortenedURL;

public interface URLRepository {
    boolean saveIfAbsent(ShortenedURL url);
    Optional<ShortenedURL> findByKey(String key);
    Optional<String> findKeyByLongURL(String longURL);
    long getNextId();
    boolean existsByKey(String key);
}