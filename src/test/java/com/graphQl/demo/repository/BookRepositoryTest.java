package com.graphQl.demo.repository;


import com.graphQl.demo.domain.entities.AuthorEntity;
import com.graphQl.demo.domain.entities.BookEntity;
import com.graphQl.demo.domain.specifications.BookSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
     * since we are using an embedded db your database will be created on each run
     * and spring on each run tries to insert data so when he doesn't find a db he'll crash before even running your test
     * I am not using an After All simply H2 db it'll empty itself
     */
    @Sql("/data.sql")
    @Test @DisplayName("Test Insertion SQL via script")
    void testSQLInjectin(){
        assertEquals(3, authorRepository.findAll().size());
    }

    @Sql("/data.sql")
    @Test @DisplayName("Finding Books By Author name")
    void findBookByFilterAuthorNameReturnPage(){

        Pageable pageable = PageRequest.of(
                0,5,
                Sort.by("name").descending()
        );

        Specification<BookEntity> spec = Specification.where(BookSpecification.hasAuthorName("JK Watkins"));

        Page<BookEntity> books = bookRepository.findAll(spec,pageable);

        assertAll("",
                ()->assertEquals(1,books.getContent().size(),"Should be of 3 elements"));

    }

    @Sql("/data.sql")
    @Test @DisplayName("Finding Books By Price max value")
    void findBookByFilterMaxPriceReturnPage(){

        Pageable pageable = PageRequest.of(
                0,5,
                Sort.by("name").descending()
        );

        Random rand = new Random();

        Specification<BookEntity> spec = Specification.where(BookSpecification.priceLowerThan(500f));

        Page<BookEntity> books = bookRepository.findAll(spec,pageable);


        assertAll("Shoul have 2 elems and order by name desc",
                ()->assertEquals(2,books.getContent().size(),"Should be of 3 elements"),
                ()->assertTrue(books.getContent().get(rand.nextInt(0,1)).getPrice() <= 500f ,"The price should be lower or equal" ),
                ()->assertEquals("The Champ",books.getContent().get(0).getName(),"Should be the champ order by name desc"));

    }


    @Sql("/data.sql")
    @Test @DisplayName("Finding Books By Price max value")
    void findBookByFilterMixPriceReturnPage(){

        Pageable pageable = PageRequest.of(
                0,5,
                Sort.by("name").descending()
        );

        Random rand = new Random();

        Specification<BookEntity> spec = Specification.where(BookSpecification.priceGreaterThan(100f));

        Page<BookEntity> books = bookRepository.findAll(spec,pageable);


        assertAll("Should have 3 elems and order by name desc",
                ()->assertEquals(3,books.getContent().size(),"Should be of 3 elements"),
                ()->assertTrue(books.getContent().get(rand.nextInt(0,2)).getPrice() >= 100f ,"The price should be lower or equal" ),
                ()->assertEquals("The Hobbit",books.getContent().get(0).getName(),"Should be the champ order by name desc"));

    }

    @Sql("/data.sql")
    @Test @DisplayName("Finding Books By Price max value")
    void findBookByFilterTitleReturnPage(){

        Pageable pageable = PageRequest.of(
                0,5,
                Sort.by("name").descending()
        );

        Specification<BookEntity> spec = Specification.where(BookSpecification.titleContains("Harry"));

        Page<BookEntity> books = bookRepository.findAll(spec,pageable);


        assertAll("Should have 1 elems and order by name desc",
                ()->assertEquals(1,books.getContent().size(),"Should be of 3 elements"),
                ()->assertEquals("Harry Potter",books.getContent().get(0).getName(),"Should be the champ order by name desc"));
    }

    @Sql("/data.sql")
    @Test @DisplayName("Finding Books By Price max value")
    void findBookByFilterCombinationReturnPage(){

        Pageable pageable = PageRequest.of(
                0,5,
                Sort.by("name").descending()
        );

        Random rand = new Random();

        Specification<BookEntity> spec = Specification.where(BookSpecification.titleContains("The"))
                .and(BookSpecification.priceGreaterThan(100f))
                .and(BookSpecification.priceLowerThan(1000f))
                .and(BookSpecification.hasAuthorName("a"));


        Page<BookEntity> books = bookRepository.findAll(spec,pageable);


        assertAll("Should have 2 elems and order by name desc",
                ()->assertEquals(2,books.getContent().size(),"Should be of 3 elements"),
                ()->assertNotNull( books.getContent().get(rand.nextInt(0,2)),"Should Not be null "),
                ()->assertEquals("The Hobbit",books.getContent().get(0).getName())
        );
    }


}
