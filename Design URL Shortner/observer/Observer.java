package observer;

import enums.EventType;
import model.ShortenedURL;

public interface Observer {
    void update(EventType type, ShortenedURL url);
}