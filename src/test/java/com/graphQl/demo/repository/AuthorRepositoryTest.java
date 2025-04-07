package com.graphQl.demo.repository;

import com.graphQl.demo.models.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;




    @Test @Sql("/data.sql")
    void getAllAuthorsReturnListOfAuthors(){

        List<AuthorEntity> authors = authorRepository.findAll();
        assertAll("Finding All authors in my db and check weither they are null or not",
                ()->assertNotNull(authors),
                ()->assertEquals(3,authors.size()),
                ()->assertEquals("JK Watkins",authors.get(0).getName())
        );
    }

    @Test @Sql("/data.sql")
    void getAuthorByIdShouldReturnAuthor(){

        AuthorEntity author = authorRepository.findById(1).get();

        assertAll("Check the existing of the author id 1",
                ()->assertNotNull(author,"Should Return not null"),
                ()->assertEquals(1,author.getId()),
                ()->assertEquals("JK Watkins",author.getName())
        );

    }


    @Test @Sql("/data.sql")
    void createAuthorShouldBeIncrementedInTheAuthorList(){

        AuthorEntity author = AuthorEntity.builder()
                .name("Mohamed")
                .bornDate(LocalDate.of(1654,4,25))
                .build();

        authorRepository.save(author);

        List<AuthorEntity> authors = authorRepository.findAll();

        assertAll("Check the insertion of new Author",
                ()->assertNotNull(authors,"Should Return not null"),
                ()->assertEquals(4,authors.size()),
                ()->assertNotNull(authors.get(3),"Should Not be of type Null"),
                ()->assertEquals("Mohamed",authors.get(3).getName())
        );

    }

}