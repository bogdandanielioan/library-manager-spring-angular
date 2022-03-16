package ro.library.librarymanagerspringangular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.library.librarymanagerspringangular.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {


}
