package observer;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;

import enums.EventType;
import model.ShortenedURL;

public class AnalyticsService implements Observer {
    private final Map<String, AtomicLong> clickCounts = new ConcurrentHashMap<>();

    @Override
    public void update(EventType type, ShortenedURL url) {
        switch(type) {
            case URL_CREATED: 
                clickCounts.put(url.getShortKey(), new AtomicLong(0));
                System.out.printf("[Analytics] URL Created: Key=%s, Original=%s%n",
                        url.getShortKey(), url.getLongURL());
                break;
            
            case URL_ACCESSED: 
                AtomicLong counter = clickCounts.computeIfAbsent(url.getShortKey(), k -> new AtomicLong(0));    //Defensive programming
                counter.getAndIncrement();
                System.out.printf("[Analytics] URL Accessed: Key=%s, Clicks=%d%n",
                        url.getShortKey(), counter.get());
                break;
        }
    }
}