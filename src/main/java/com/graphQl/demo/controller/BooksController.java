package com.graphQl.demo.controller;


import com.graphQl.demo.models.Author;
import com.graphQl.demo.models.Book;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BooksController {


    @QueryMapping
    public List<Book> books() {
        return Book.books;
    }

    @QueryMapping
    public Book bookById(@Argument String id) {
        return Book.getById(id);
    }

    @SchemaMapping // this is used to map the author object to your book object
    public Author author(Book book) {
        return Author.getById(book.authorId());
    }

}
