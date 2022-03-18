package ro.library.librarymanagerspringangular.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.library.librarymanagerspringangular.exceptions.BookNotFoundException;
import ro.library.librarymanagerspringangular.model.Book;
import ro.library.librarymanagerspringangular.model.HttpResponse;
import ro.library.librarymanagerspringangular.services.BookService;

import javax.validation.Valid;
import java.net.URI;

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
    @PostMapping("/add")
    public  ResponseEntity<HttpResponse<Book>> addBook(@RequestBody @Valid Book book){
        return ResponseEntity.created(
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/book/add").toUriString())
        ).body(bookService.saveBook(book));
    }
    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<Book>> getBook(@PathVariable(value = "id") Long id) throws BookNotFoundException {
        return ResponseEntity.ok().body(bookService.getBookById(id));
    }


}
