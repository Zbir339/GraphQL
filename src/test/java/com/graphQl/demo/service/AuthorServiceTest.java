package com.graphQl.demo.service;

import com.graphQl.demo.domain.dto.AuthorDto;
import com.graphQl.demo.exception.entities.AuthorNotFoundException;
import com.graphQl.demo.domain.entities.AuthorEntity;
import com.graphQl.demo.repository.AuthorRepository;
import com.graphQl.demo.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AuthorServiceTest {

    @MockitoBean
    AuthorRepository authorRepository;

    @Autowired
    AuthorServiceImpl authorService;

    List<AuthorEntity> authors = new ArrayList<>();


    @BeforeEach
    void init(){

        authors.add(AuthorEntity.builder().id(1).name("JK Watkins").build());
        authors.add(AuthorEntity.builder().id(2).name("Robert Louis Stevenson").build());
        authors.add(AuthorEntity.builder().id(3).name("Herman Melville").build());
    }

    @Test
    void getAllAuthors() {
        // get All Authors
        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorDto> authorDtos = authorService.getAllAuthors();

        assertAll("",
                ()->assertNotNull(authorDtos,"Should Not be null or empty"),
                ()->assertEquals(3,authorDtos.size(),"Should be of size 3"),
                ()->assertEquals("JK Watkins",authorDtos.get(0).getName(),"The name should not be null"));


    }

    @Test
    void save() {



    }

    @Test
    void getAuthorShouldReturnAuthorOne() {

        when(authorRepository.findById(1)).thenReturn(Optional.ofNullable(authors.get(0)));

        AuthorDto author = authorService.getAuthor(1);

        assertAll("",
                ()->assertNotNull(author,"Should not be null"),
                ()->assertEquals(1,author.getId(),"Should be of id 1"),
                ()->assertEquals("JK Watkins",author.getName(),"Should have the exact name"));
    }


    @Test
    void getAuthorShouldThrowNotFoundException() {

        when(authorRepository.findById(9)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class,()->authorService.getAuthor(9));
    }
}