package com.graphQl.demo.repository;


import com.graphQl.demo.models.AuthorEntity;
import com.graphQl.demo.models.BookEntity;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Sql("/data.sql")
    @Test @DisplayName("Geting all books data including author data")
    void findAllShouldReturnThreeBooks(){
        Random rand = new Random();
        List<BookEntity> books  = bookRepository.findAll();
        int number = rand.nextInt(2);
        assertAll("Asserting that the book is not null with 3 books and no null values within",
                ()->assertEquals(3, books.size(),"Should return Non Null value"),
                ()->assertNotNull(books.get(number).getName(),"The Name of a random book should not be null"),
                ()->assertNotNull(books.get(number).getAuthor(),"The author part should not be null")); // if we cant to make our test case more edgy a book can sometimes have no author
        /*Super Critical point here */
        /*  N + 1 Problem a huge critical one that'll make your db and back app suffer eventually    */

    }


    @Sql("/data.sql")
    @Test @DisplayName("Saving a new Book in the table")
    void creatingNewBookReturnBook(){

        AuthorEntity author = authorRepository.findById(1).orElseGet(() -> {
            AuthorEntity newAuthor = new AuthorEntity();
            newAuthor.setBornDate(LocalDate.of(2001, 4, 25));
            newAuthor.setName("Jk Watkins");
            return authorRepository.save(newAuthor);
        });

        bookRepository.save(new BookEntity(null,"Harry Potter And the Phoenix",450f,"Trololololololo",author));

        List<BookEntity> books  = bookRepository.findAll();

        assertAll("Asserting that the book is not null with id 1 and identical names",
                ()->assertEquals(4, books.size(),"Should return Non Null value"),
                ()->assertEquals(4, books.get(3).getId(),"Should have id 4 since it's the forth item"),
                ()->assertEquals("Harry Potter And the Phoenix", books.get(3).getName(),"Should have identical Name"),
                ()->assertEquals("JK Watkins", books.get(3).getAuthor().getName()));
        /*Super Critical point here */
        /*  N + 1 Problem a huge critical one that'll make your db and back app suffer eventually    */

//        for(int i= 0; i< books.size();i++ ){
//            System.out.println("Book :" + i + " Author Name "+books.get(i).getAuthor().getName());
//        }
    }


    @Sql("/data.sql")
    @Test @DisplayName("Finding Books By name Harry Potter")
    void findBookByNameReturnNameAndNotNull(){

        BookEntity book = bookRepository.findBookEnityByName("Harry Potter").orElse(null);

        assertAll("Asserting that the book is not null with id 1 and identical names",
                ()->assertNotNull(book,"Should return Non Null value"),
                ()->assertEquals(1, book.getId(),"Should have id 1"),
                ()->assertEquals("Harry Potter", book.getName(),"Should have identical Name"));
    }

    @Test @DisplayName("Finding Books By an Unkown name")
    void findBookByNameReturnNullForUnkownNames(){

        BookEntity book = bookRepository.findBookEnityByName("XXX").orElse(null);
        assertNull(book,"Should Return Null Values");
    }

    /*  @Sql("/<file-sql>")
     * for this to work you must make sure that your spring app is not consuming the file on each run
     * since we are using an embeded db your database will be created on each run
     * and spring on each run tries to insert data so when he dosen't find a db he'll crash before even runing your test
     * I am not using an AfterAll simply H2 db it'll empty it self
     */
    @Sql("/data.sql")
    @Test @DisplayName("Test Insertion SQL via script")
    void testSQLInjectin(){
        assertEquals(3, authorRepository.findAll().size());
    }




}
