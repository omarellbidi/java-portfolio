package mvc.presentation;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mvc.model.Book;
import mvc.model.BookManager;
import java.util.Optional;



public class MainFX extends Application {
    private final BookManager bookManager = new BookManager();
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        // Dynamic label for the title
        Label dynamicLabel = new Label("You have not added any books yet!");
        dynamicLabel.setFont(new Font("Arial", 24));

        // Observer to update the dynamic label
        bookManager.addObserver((o, arg) -> {
            bookList.setAll(bookManager.getBooks());
            if (bookList.isEmpty()) {
                dynamicLabel.setText("You have not added any books yet");
            } else {
                dynamicLabel.setText("Your Books");
            }
        });

        ListView<Book> listView = new ListView<>(bookList);

        // Title box
        VBox labelBox = new VBox(dynamicLabel);
        labelBox.setStyle("-fx-alignment: center; -fx-padding: 10;");

        // Buttons
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button editButton = new Button("Edit");

        removeButton.setDisable(true);
        editButton.setDisable(true);

        // Enable/disable buttons based on selection
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isSelected = newSelection != null;
            removeButton.setDisable(!isSelected);
            editButton.setDisable(!isSelected);
        });

        // Add button handler
        addButton.setOnAction(e -> {
            while (true) {
                Optional<Book> book = showBookDialog(null);
                if (book.isPresent()) {
                    try {
                        bookManager.addBook(book.get());
                        break; // Exit the loop on success
                    } catch (IllegalArgumentException ex) {
                        showErrorDialog("Duplicate ISBN", ex.getMessage());
                    }
                } else {
                    break; // Exit loop if user cancels
                }
            }
        });

        // Remove button handler
        removeButton.setOnAction(e -> {
            Book selectedBook = listView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                bookManager.removeBook(selectedBook);
            }
        });

        // Edit button handler
        editButton.setOnAction(e -> {
            Book selectedBook = listView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                while (true) {
                    Optional<Book> updatedBook = showBookDialog(selectedBook);
                    if (updatedBook.isPresent()) {
                        try {
                            bookManager.editBook(selectedBook, updatedBook.get());
                            break; // Exit the loop on success
                        } catch (IllegalArgumentException ex) {
                            showErrorDialog("Duplicate ISBN", ex.getMessage());
                        }
                    } else {
                        break; // Exit loop if user cancels
                    }
                }
            }
        });

        // ToolBar
        ToolBar toolBar = new ToolBar(addButton, removeButton, editButton);

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(labelBox);
        root.setCenter(listView);
        root.setBottom(toolBar);

        // Scene and stage setup
        Scene scene = new Scene(root, 900, 800);
        stage.setTitle("Book Manager");
        stage.setScene(scene);
        stage.show();
    }

    private Optional<Book> showBookDialog(Book book) {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle(book == null ? "Add Book" : "Edit Book");

        // Input fields
        TextField titleField = new TextField(book == null ? "" : book.getTitle());
        TextField authorField = new TextField(book == null ? "" : book.getAuthor());
        TextField yearField = new TextField(book == null ? "" : String.valueOf(book.getYear()));
        TextField isbnField = new TextField(book == null ? "" : book.getIsbn());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Title:"), titleField);
        grid.addRow(1, new Label("Author:"), authorField);
        grid.addRow(2, new Label("Year:"), yearField);
        grid.addRow(3, new Label("ISBN:"), isbnField);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Validate inputs
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
       okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
    try {
        // Validate inputs
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String yearInput = yearField.getText().trim();
        String isbn = isbnField.getText().trim();

        // Check if any field is empty
        if (title.isEmpty() || author.isEmpty() || yearInput.isEmpty() || isbn.isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        // Parse year (only after ensuring it's not empty)
        try {
            int year = Integer.parseInt(yearInput);

            // Additional validation for year
            if (year < 0) {
                throw new IllegalArgumentException("Year must be positive");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Year must be a valid number");
        }

    } catch (IllegalArgumentException e) {
        // Show error message and consume event to keep the dialog open
        showErrorDialog("Invalid Input", e.getMessage());
        event.consume(); // Prevent dialog from closing
    }
});


        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return new Book(
                        titleField.getText().trim(),
                        authorField.getText().trim(),
                        Integer.parseInt(yearField.getText().trim()),
                        isbnField.getText().trim()
                );
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}