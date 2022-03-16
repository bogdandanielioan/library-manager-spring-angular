package ro.library.librarymanagerspringangular;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.library.librarymanagerspringangular.model.Book;
import ro.library.librarymanagerspringangular.repository.BookRepository;

@SpringBootApplication
public class LibraryManagerSpringAngularApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagerSpringAngularApplication.class, args);
    }
//        @Bean
//        CommandLineRunner commandLineRunner(
//           BookRepository bookRepository) {
//        return args -> {
//            Faker faker = new Faker();
//
//
//            for(int i=1;i<=100;i++){
//                Book b=new Book(faker.book().title(),faker.book().author(),faker.book().genre(),(int)(Math.floor(Math.random()*2000+1000)));
//
//                 bookRepository.save(b);
//            }
//
//        };
//    }

}
