package ro.library.librarymanagerspringangular.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.library.librarymanagerspringangular.exceptions.BookNotFoundException;
import ro.library.librarymanagerspringangular.model.Book;
import ro.library.librarymanagerspringangular.model.HttpResponse;
import ro.library.librarymanagerspringangular.repository.BookRepository;

import java.util.Optional;

import static java.util.Collections.singleton;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
@Slf4j
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public HttpResponse<Book> getBooks(){
        log.info("Fetching all the books from database");
        return   HttpResponse.<Book>builder()
                .books(this.bookRepository.findAll())
                .message(bookRepository.count()>0?bookRepository.count()+" books retrieved ":"No books")
                .status(OK)
                .statusCode(OK.value())
                .build();
    }

    public  HttpResponse<Book> saveBook(Book book){
         log.info("Saving new book  to the database");
         return HttpResponse.<Book>builder()
                 .books(singleton(this.bookRepository.save(book)))
                 .message("Book created ")
                 .statusCode(CREATED.value())
                 .build();
    }

    public HttpResponse<Book> getBookById(Long id) throws BookNotFoundException {
        Optional<Book> optionalNote = ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book was not found on the server")));
        return HttpResponse.<Book>builder()
                .books(singleton(optionalNote.get()))
                .message("Retrived book successdully")
                .status(OK)
                .statusCode(OK.value())
                .build();
    }

    public HttpResponse<Book> updateNote(Book book) throws BookNotFoundException {
        log.info("Updating book to the database");
        Optional<Book> optionalBook = ofNullable(bookRepository.findById(book.getId())
                .orElseThrow(() -> new BookNotFoundException("The note was not found on the server")));
        Book updateBook = optionalBook.get();
        updateBook.setId(book.getId());
        updateBook.setTitle(book.getTitle());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setGenre(book.getGenre());
        updateBook.setYear(book.getYear());
        bookRepository.save(updateBook);

        return HttpResponse.<Book>builder()
                .books(singleton(updateBook))
                .message("Book updated successfully")
                .status(OK)
                .statusCode(OK.value())
                .build();
    }





}
