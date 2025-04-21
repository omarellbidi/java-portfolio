package observer;

import java.time.LocalDateTime;
import java.util.*;

/**
 * MainSpreader is the central broadcaster for spreading news.
 * Implements the Subject interface and uses helpers for source management and content filtering.
 */

public class MainSpreader implements Subject, NewsSpreader {
    private TrustedSourceManager sourceManager = new TrustedSourceManager();
    private ContentFilter contentFilter = new ContentFilter();
    private Map<String, List<NewsObserver>> topicObservers = new HashMap<>();

    public MainSpreader() {
        // Default constructor
    }

    // Registers a trusted news source with a hashed password
    @Override
    public boolean registerTrustedSource(String source, String pwd) {
        if (source == null || pwd == null || source.isEmpty() || pwd.isEmpty()) {
            return false;
        }
        return sourceManager.addSource(source, hashPassword(pwd));
    }

    // Adds a word to the block list with redaction options
    @Override
    public boolean blockWord(String word, boolean redact) {
        return contentFilter.blockWord(word, redact);
    }

    // Removes a word from the block list
    @Override
    public boolean unblockWord(String word) {
        return contentFilter.unblockWord(word);
    }

    // Processes and broadcasts news from a trusted source
    @Override
    public String spreadNews(String news, String source, String pwd) throws NewsSpreaderException {
        if (news == null || source == null || pwd == null) {
            throw new IllegalArgumentException("News, source, or password cannot be null.");
        }
        if (!sourceManager.isSourceRegistered(source)) {
            throw new UntrustedSourceException("Source not registered: " + source);
        }
        if (!sourceManager.authenticateSource(source, hashPassword(pwd))) {
            throw new AuthenticationException("Failed authentication for source: " + source);
        }

        String processedNews = contentFilter.processNews(news); // Filter the news content
        LocalDateTime timestamp = LocalDateTime.now(); // Get the current timestamp
        notifyObservers(processedNews, source, timestamp, extractTopic(news)); // Notify observers
        return processedNews;
    }

    // Registers an observer for all topics by default
    @Override
    public void registerObserver(NewsObserver observer) {
        registerObserver(observer, "all");
    }

    // Registers an observer for a specific topic
    @Override
    public void registerObserver(NewsObserver observer, String topic) {
        if (observer == null || topic == null || topic.isEmpty()) return;
        topicObservers.computeIfAbsent(topic, k -> new ArrayList<>()).add(observer);
    }

    // Unregisters an observer from all topics
    @Override
    public void unregisterObserver(NewsObserver observer) {
        for (List<NewsObserver> observers : topicObservers.values()) {
            observers.remove(observer);
        }
    }

    // Notifies all observers of news (default topic)
    @Override
    public void notifyObservers(String news, String source, LocalDateTime timestamp) {
        notifyObservers(news, source, timestamp, "all");
    }

    // Notifies observers subscribed to a specific topic
    public void notifyObservers(String news, String source, LocalDateTime timestamp, String topic) {
        List<NewsObserver> observers = topicObservers.getOrDefault(topic, new ArrayList<>());
        for (NewsObserver observer : observers) {
            observer.update(news, source, timestamp);
        }
    }

    // Extracts a topic from news if it contains a hashtag
    private String extractTopic(String news) {
        if (news.contains("#")) {
            return Arrays.stream(news.split("\\s+"))
                    .filter(word -> word.startsWith("#"))
                    .findFirst()
                    .orElse("all");
        }
        return "all";
    }

    // Hashes a password using SHA-256
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Main method for demonstration
    public static void main(String[] args) {
        MainSpreader broadcaster = new MainSpreader();

        // Register a trusted news source
        broadcaster.registerTrustedSource("source1", "password1");

        // Register an observer that prints news to the console
        broadcaster.registerObserver((news, source, timestamp) ->
                System.out.println("Console Observer: " + news + " from " + source + " at " + timestamp)
        );

        // Register an observer that logs news to a file
        broadcaster.registerObserver((news, source, timestamp) -> {
            try (java.io.FileWriter writer = new java.io.FileWriter("news_log.txt", true)) {
                writer.write("File Observer: " + news + " from " + source + " at " + timestamp + "\n");
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        });

        // Simulate news input from the command line
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("Enter news (type 'exit' to quit): ");
        while (true) {
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) break;
            try {
                broadcaster.spreadNews(input, "source1", "password1");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}

