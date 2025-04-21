package mvc;

import mvc.model.Book;
import mvc.model.BookManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Observable;
import java.util.Observer;
import static org.junit.jupiter.api.Assertions.*;


public class MVCTest {

    private BookManager bookManager;
    private boolean observerNotified;

    @BeforeEach
    void setUp() {
        bookManager = new BookManager();
        observerNotified = false; // Tracks if the observer is notified
    }

    @Test
    void testObserverNotifiedOnAdd() {
        bookManager.addObserver((Observable o, Object arg) -> observerNotified = true);

        // Add a book and verify the observer is notified
        Book book = new Book("Title1", "Author1", 2023, "1234");
        bookManager.addBook(book);

        assertTrue(observerNotified, "Observer should be notified when a book is added.");
    }

    @Test
    void testObserverNotifiedOnRemove() {
        bookManager.addObserver((Observable o, Object arg) -> observerNotified = true);

        // Add and then remove a book, checking observer notification
        Book book = new Book("Title1", "Author1", 2023, "1234");
        bookManager.addBook(book);
        observerNotified = false; // Reset flag
        bookManager.removeBook(book);

        assertTrue(observerNotified, "Observer should be notified when a book is removed.");
    }

    @Test
    void testObserverNotifiedOnEdit() {
        bookManager.addObserver((Observable o, Object arg) -> observerNotified = true);

        // Add a book and edit it
        Book oldBook = new Book("Title1", "Author1", 2023, "1234");
        Book newBook = new Book("Updated Title", "Updated Author", 2024, "1234");

        bookManager.addBook(oldBook);
        observerNotified = false; // Reset flag
        bookManager.editBook(oldBook, newBook);

        assertTrue(observerNotified, "Observer should be notified when a book is edited.");
    }
}
