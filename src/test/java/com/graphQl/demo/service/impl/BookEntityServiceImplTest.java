package com.graphQl.demo.service.impl;

import com.graphQl.demo.dto.BookDto;
import com.graphQl.demo.exception.BookNotFoundException;
import com.graphQl.demo.models.AuthorEntity;
import com.graphQl.demo.models.BookEntity;
import com.graphQl.demo.repository.BookRepository;
import com.graphQl.demo.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookEntityServiceImplTest {

    // mock , mockBean , injectMock
    //ApplicationContext context; // to get beans or something

    @MockitoBean /* we can use mock or mockbean if we don't want to inject */
    BookRepository bookRepository;

    @Autowired
    BookEntityServiceImpl bookEntityService;

    BookEntity book1;
    BookEntity book2;
    BookEntity book3;
    BookEntity book4;

    AuthorEntity author1;
    AuthorEntity author2;

    @BeforeEach
    void init(){
        author1 = AuthorEntity.builder()
                .id(1)
                .name("JK Watkins")
                .bornDate(LocalDate.of(1985,4,25))
                .build();
        author2 = AuthorEntity.builder()
                .id(2)
                .name("John Jack")
                .bornDate(LocalDate.of(1965,9,25))
                .build();

        book1 = BookEntity.builder()
                .id(1)
                .name("Harry Potter And the Phoenix")
                .author(author1)
                .price(450f)
                .description("Harry Potter's adventure")
                .build();

        book2 = BookEntity.builder()
                .id(2)
                .name("Treasure Island")
                .author(author1)
                .price(850f)
                .description("Jim's adventure with Silver the great pirate")
                .build();

        book3 = BookEntity.builder()
                .id(3)
                .name("Atomic Habits")
                .author(author2)
                .price(650f)
                .description("Describes the ....")
                .build();

        book4 = BookEntity.builder()
                .id(null)
                .name("Atomic Habits")
                .author(author2)
                .price(650f)
                .description("Describes the ....")
                .build();
    }

    @Test @DisplayName("Finding book by name must return book")
    void getBookByNameShouldReturnBook() {

        when(bookRepository.findBookEnityByName("Harry Potter And the Phoenix")).thenReturn(Optional.of(book1));

        BookDto book = bookEntityService.getBookByName("Harry Potter And the Phoenix");

        assertAll("",
                ()->assertNotNull(book,"Should return not null"),
                ()->assertEquals(book.getName(),"Harry Potter And the Phoenix"),
                ()->assertNotNull(book.getAuthor(),"Should as well return not null"));
    }


    @Test @DisplayName("Finding book by inexsting name must throw exception")
    void getBookByNameShouldThrowBookNotFoundException() {

        when(bookRepository.findBookEnityByName("XXX")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookEntityService.getBookByName("XXX");
        });
    }

    @Test
    void getAllBooksShouldReturnBookS() {

        when(bookRepository.findAll()).thenReturn(List.of(book1,book2,book3));

        List<BookDto> books = bookEntityService.getAllBooks();

        Random rand = new Random();

        int index = rand.nextInt(2);

        assertAll("Should ",
                ()->assertEquals(books.size(),3,"Shoudl return all the elements"),
                ()->assertNotNull(books.get(index),"Should not be null"),
                ()->assertNotNull(books.get(index).getAuthor(),"The author should not be null"));

    }

    @Test @DisplayName("Saving new book")
    void save() {

        when(bookRepository.save(book4)).thenAnswer(invoc -> {
            book4.setId(4);
            return book4;
        });

        BookDto bookDto = bookEntityService.save(book4);

        assertAll("",
                ()->assertNotNull(bookDto,"Should not be of null value"),
                ()->assertEquals(bookDto.getId(),4,"The id must be incremented"),
                ()->assertNotNull(bookDto.getAuthor(),"The author should not be null")
                );



    }
}