package ro.library.librarymanagerspringangular.exceptions;


public class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}
