package ro.library.librarymanagerspringangular.services;

import com.mysql.cj.x.protobuf.Mysqlx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ro.library.librarymanagerspringangular.model.Book;
import ro.library.librarymanagerspringangular.model.HttpResponse;
import ro.library.librarymanagerspringangular.repository.BookRepository;

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
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build();

    }

}
