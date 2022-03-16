package ro.library.librarymanagerspringangular.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Book")
@Table(name="book")
public class Book{
    @Id
    @SequenceGenerator(
            name="book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    Long id;

    @Column(
            name="title",
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    @NotBlank(message = "Title is required")
    String title;
    @Column(
            name ="author",
            nullable =false,
            columnDefinition = "TEXT"
    )
    @NotBlank(message = "Author is required")
    String author;

    @NotBlank(message = "Genre is required")
    @Column(
            name="genre",
            nullable = false,
            columnDefinition = "TEXT"
    )

    String genre;

    @Column(
            name="year",
            columnDefinition = "INTEGER",
            nullable = false
    )
    Integer year;


    public Book(String title, String author, String genre, int year) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
    }




}