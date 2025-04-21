package observer;

/**
 * Subject interface for the Observer pattern.
 * Defines methods for managing observers and notifying them of updates.
 */
public interface Subject {

    // Registers an observer to receive updates
    void registerObserver(NewsObserver observer);

    // Registers an observer for a specific topic
    void registerObserver(NewsObserver observer, String topic);

    // Unregisters an observer
    void unregisterObserver(NewsObserver observer);

    // Notifies all observers of an update
    void notifyObservers(String news, String source, java.time.LocalDateTime timestamp);
}
