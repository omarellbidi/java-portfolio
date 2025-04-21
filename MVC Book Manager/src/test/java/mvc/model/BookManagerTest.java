/**
 * Tests for BookManager: adding, editing, removing books, handling duplicates.
 */

package mvc.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookManagerTest {

    private BookManager bookManager;

    @BeforeEach
    void setUp() {
        bookManager = new BookManager();
    }

    @Test
    void testAddBook() {
        Book book = new Book("Title", "Author", 2023, "1234");
        bookManager.addBook(book);

        List<Book> books = bookManager.getBooks();
        assertEquals(1, books.size(), "BookManager should contain one book after adding.");
        assertEquals(book, books.get(0), "The added book should match the retrieved book.");
    }


    @Test
    void testAddDuplicateISBN() {
        Book book1 = new Book("Title1", "Author1", 2023, "1234");
        Book book2 = new Book("Title2", "Author2", 2024, "1234"); // Same ISBN

        bookManager.addBook(book1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> bookManager.addBook(book2),
                "Adding a book with a duplicate ISBN should throw an exception.");
         assertEquals("ISBN already exists", exception.getMessage());

    }

    @Test
    void testRemoveBook() {
        Book book = new Book("Title", "Author", 2023, "1234");
        bookManager.addBook(book);
        bookManager.removeBook(book);

        List<Book> books = bookManager.getBooks();
        assertTrue(books.isEmpty(), "BookManager should be empty after removing the book.");
    }

    @Test
    void testRemoveNonExistentBook() {
        Book book = new Book("Title", "Author", 2023, "1234");

        assertDoesNotThrow(() -> bookManager.removeBook(book),
                "Removing a non-existent book should not throw an exception.");
    }

    @Test
    void testEditBook() {
        Book oldBook = new Book("Old Title", "Old Author", 2023, "1234");
        Book newBook = new Book("New Title", "New Author", 2024, "1234");

        bookManager.addBook(oldBook);
        bookManager.editBook(oldBook, newBook);

        List<Book> books = bookManager.getBooks();
        assertEquals(1, books.size(), "BookManager should still contain one book after editing.");
        assertEquals(newBook, books.get(0), "The edited book should match the new book details.");
    }

    @Test
    void testGetBooksReturnsCopy() {
        Book book = new Book("Title", "Author", 2023, "1234");
        bookManager.addBook(book);

        List<Book> books = bookManager.getBooks();
        books.clear(); // Attempt to modify the returned list

        assertEquals(1, bookManager.getBooks().size(), "Modifying the returned list should not affect BookManager's data.");
    }
}
