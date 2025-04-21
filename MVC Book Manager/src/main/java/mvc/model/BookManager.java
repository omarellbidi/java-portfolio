package mvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class BookManager extends Observable {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        for (Book b : books) {
            if (b.getIsbn().equals(book.getIsbn())) {
                throw new IllegalArgumentException("ISBN already exists");
            }
        }
        books.add(book);
        setChanged();
        notifyObservers(books);
    }

    public void removeBook(Book book) {
        books.remove(book);
        setChanged();
        notifyObservers(books);
    }

    public void editBook(Book oldBook, Book newBook) {
        removeBook(oldBook);
        addBook(newBook);
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }
}
