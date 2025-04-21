package observer;

/**
 * NewsObserver interface for the Observer pattern.
 * Any class implementing this interface can receive updates from a Subject (e.g., news updates).
 */
public interface NewsObserver {

    /**
     * Called when the Subject has news to share.
     * @param news The content of the news.
     * @param source The source of the news.
     * @param timestamp The time the news was broadcasted.
     */
    void update(String news, String source, java.time.LocalDateTime timestamp);
}
