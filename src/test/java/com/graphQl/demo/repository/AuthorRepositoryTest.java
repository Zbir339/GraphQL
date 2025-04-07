package com.graphQl.demo.repository;

import com.graphQl.demo.models.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

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
        assertAll("",
                ()->assertNotNull(authors),
                ()->assertEquals(3,authors.size()),
                ()->assertEquals("JK Watkins",authors.get(0).getName())
        );
    }

}