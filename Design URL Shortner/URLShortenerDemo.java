import java.time.LocalDateTime;
import java.util.Optional;

import model.ShortenedURL;
import repository.InMemoryURLRepository;
import strategy.Base62Strategy;
import observer.AnalyticsService;

public class URLShortenerDemo {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=========================================");
        System.out.println("     URL SHORTENER SYSTEM DEMO");
        System.out.println("=========================================\n");

        // -------------------- Setup --------------------
        URLShortenerService shortener = URLShortenerService.getInstance();

        shortener.configure(
                "http://short.ly/",
                new InMemoryURLRepository(),
                new Base62Strategy());

        shortener.addObserver(new AnalyticsService());

        System.out.println("Service initialized.");
        System.out.println("Strategy  : Base62");
        System.out.println("Repository: In Memory");
        System.out.println();

        // -------------------- Shorten URLs --------------------

        String google = "https://www.google.com/search?q=builder+pattern";

        String github = "https://github.com/OpenAI";

        System.out.println("--------------- SHORTENING URLS ---------------");

        String googleShort = shortener.shorten(google);
        String githubShort = shortener.shorten(github);

        System.out.println("Google : " + googleShort);
        System.out.println("GitHub : " + githubShort);

        System.out.println();

        // -------------------- Duplicate URL --------------------

        System.out.println("--------- DUPLICATE URL TEST ---------");

        String duplicate = shortener.shorten(google);

        if (duplicate.equals(googleShort)) {
            System.out.println("PASS: Duplicate URL returned existing key.");
        } else {
            System.out.println("FAIL");
        }

        System.out.println();

        // -------------------- Resolution --------------------

        System.out.println("--------- URL RESOLUTION TEST ---------");

        resolveAndPrint(shortener, googleShort);
        resolveAndPrint(shortener, googleShort);
        resolveAndPrint(shortener, googleShort);

        resolveAndPrint(shortener, githubShort);

        System.out.println();

        // -------------------- Invalid URL --------------------

        System.out.println("--------- INVALID URL TEST ---------");

        resolveAndPrint(shortener,
                "http://short.ly/doesNotExist");

        System.out.println();

        // -------------------- Builder Pattern --------------------

        System.out.println("--------- BUILDER TEST ---------");

        ShortenedURL custom = new ShortenedURL.Builder(
                "https://custom.com",
                "custom1")
                .creationDate(LocalDateTime.now().minusDays(1))
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        System.out.println("Long URL     : " + custom.getLongURL());
        System.out.println("Short Key    : " + custom.getShortKey());
        System.out.println("Created At   : " + custom.getCreationDate());
        System.out.println("Expires At   : " + custom.getExpiryDate());
        System.out.println("Expired?     : " + custom.isExpired());

        System.out.println();

        // -------------------- Expired Builder Object --------------------

        System.out.println("--------- EXPIRY TEST ---------");

        ShortenedURL expired = new ShortenedURL.Builder(
                "https://expired.com",
                "dead01")
                .expiryDate(LocalDateTime.now().minusSeconds(1))
                .build();

        System.out.println("Expired? " + expired.isExpired());

        System.out.println();

        // -------------------- Singleton --------------------

        System.out.println("--------- SINGLETON TEST ---------");

        URLShortenerService another = URLShortenerService.getInstance();

        if (another == shortener) {
            System.out.println("PASS: Only one service instance exists.");
        } else {
            System.out.println("FAIL");
        }

        System.out.println();

        System.out.println("=========================================");
        System.out.println("           DEMO COMPLETE");
        System.out.println("=========================================");
    }

    private static void resolveAndPrint(URLShortenerService shortener, String shortUrl) {
        Optional<String> resolvedUrl = shortener.resolve(shortUrl);

        if (resolvedUrl.isPresent()) {
            System.out.printf("Short URL %s resolved to -> %s%n", shortUrl, resolvedUrl.get());
        } else {
            System.out.println("No original URL found for: " + shortUrl);
        }
    }
}
