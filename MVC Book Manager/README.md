# MVC Book Manager

## Project Overview
This application is a modern book management system built with JavaFX that demonstrates the Model-View-Controller (MVC) architectural pattern and Observer design pattern. It allows users to manage a collection of books with standard CRUD operations through an intuitive graphical interface.

## Architecture
The application strictly follows the MVC pattern:

### Model
- `Book`: Represents a book with title, author, year, and ISBN attributes
- `BookManager`: Manages the collection of books, implements the Observable pattern

### View
- JavaFX-based user interface with a ListView displaying books
- Dynamic title that changes based on collection state
- Toolbar with context-sensitive buttons

### Controller
- Handles user interactions and delegates to the model
- Manages input validation and error handling
- Updates view state based on user selection

## Features
- Add new books with complete metadata
- Remove existing books from the collection
- Edit book details while maintaining ISBN uniqueness
- Real-time UI updates using the Observer pattern
- Input validation with helpful error messages
- Disabled buttons when actions aren't applicable

## Technical Highlights
- **Clean Architecture**: Strict separation between model and UI components
- **Observer Pattern**: Model notifies view of changes without direct coupling
- **MVC Implementation**: Clear separation of concerns across components
- **Exception Handling**: Robust error handling for invalid inputs and duplicates
- **Unit Testing**: Comprehensive test suite for model and observer functionality

## Implementation Details
- The model (`BookManager`) extends Java's Observable class
- The view registers as an observer to receive updates from the model
- The controller logic is integrated with the view for simplicity
- ISBN uniqueness is enforced at the model level
- All model classes are completely independent of UI technology

## Testing
The application includes comprehensive unit tests:
- Model functionality (add, remove, edit)
- Observer notification mechanism
- Duplicate ISBN handling
- Error cases and exception handling

To run the tests:
```
mvn clean test
```

## Running the Application
To launch the application:
```
mvn clean javafx:run
```
