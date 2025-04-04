package com.graphQl.demo.repository;


import com.graphQl.demo.models.AuthorEntity;
import com.graphQl.demo.models.BookEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

//    @Sql("/data.sql")
//    @Test @DisplayName("Saving a new Book in the table")
//    void creatingNewBookReturnBook(){
//
//        bookRepository.save(BookEntity.builder()
//                        .id(null)
//                        .name("Harry Potter And the Phoenix")
//                        .price(800f)
//                        .description("Harry's journey")
//                        .author( AuthorEntity.builder().id(1).build())
//                .build());
//
//        List<BookEntity> books  = bookRepository.findAll();
//
//        /*Super Critical point here */
//
//
//        assertAll("Asserting that the book is not null with id 1 and identical names",
//                ()->assertEquals(books.size(),4,"Should have a size of 4"),
//                ()->assertNotNull(books.get(3),"Should return Non Null value"),
//                ()->assertEquals(books.get(3).getId(),4,"Should have id 4"),
//                ()->assertEquals(books.get(3).getName(),"Harry Potter And the Phoenix","Should have identical Name")
//                //()->assertEquals(books.get(3).getAuthor().getName(),"JK Watkins","Should Return JK Watkins")
//        );
//    }




    @Sql("/data.sql")
    @Test @DisplayName("Finding Books By name Harry Potter")
    void findBookByNameReturnNameAndNotNull(){

        BookEntity book = bookRepository.findBookEnityByName("Harry Potter").orElse(null);

        assertAll("Asserting that the book is not null with id 1 and identical names",
                ()->assertNotNull(book,"Should return Non Null value"),
                ()->assertEquals(book.getId(),1,"Should have id 1"),
                ()->assertEquals(book.getName(),"Harry Potter","Should have identical Name"));
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
        assertEquals(authorRepository.findAll().size(),3);
    }




}
