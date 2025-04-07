package com.graphQl.demo.controller;


import com.graphQl.demo.dto.AuthorDto;
import com.graphQl.demo.dto.BookDto;
import com.graphQl.demo.mapper.impl.AuthorMapper;
import com.graphQl.demo.models.Author;
import com.graphQl.demo.models.AuthorEntity;
import com.graphQl.demo.models.Book;
import com.graphQl.demo.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BooksController {


    private AuthorMapper authorMapper;

    public BooksController(AuthorMapper authorMapper, BookService bookService) {
        this.authorMapper = authorMapper;
        this.bookService = bookService;
    }

    private final BookService bookService;

    @QueryMapping
    public List<BookDto> books() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book bookById(@Argument String id) {
        return Book.getById(id);
    }

    @SchemaMapping // this is used to map the author object to your book object
    public AuthorDto author(BookDto book) {
        AuthorDto authorDto = authorMapper.mapTo(book.getAuthor());
        System.out.println(authorDto.getBornDate());
        return authorDto;
    }

}
