package ro.library.librarymanagerspringangular.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.library.librarymanagerspringangular.model.Book;
import ro.library.librarymanagerspringangular.model.HttpResponse;
import ro.library.librarymanagerspringangular.services.BookService;

@RestController
@RequestMapping("api/v1/books")
@CrossOrigin
public class BookController {


    private  final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<HttpResponse<Book>> getBooks(){

        return  ResponseEntity.ok().body(bookService.getBooks());
    }


}
