package com.graphQl.demo.controller;


import com.graphQl.demo.domain.dto.*;
import com.graphQl.demo.domain.enums.SortDirection;
import com.graphQl.demo.mapper.impl.AuthorMapper;
import com.graphQl.demo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.annotation.Secured;
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

    /*By Mohamed Amine
     * In GraphQL, this is known as a **resolver**.
     *
     * Simply put, a resolver is a method or function designed to "feed" or provide
     * the data for a specific GraphQL field (like the fields in our Book schema).
     *
     * For example, when a client queries the 'books' field, GraphQL calls the corresponding
     * resolver method in the backend to fetch and return the appropriate data.
     *
     * Each field in the schema can have its own resolver, especially for nested relationships.
     *
     * The following example is a Root Query/Mutation resolver
     */
    @Secured("ROLE_USER")
    @QueryMapping
    public List<BookDto> books() {
        return bookService.getAllBooks();
    }

    @Secured("ROLE_ADMIN")
    @QueryMapping
    public BookDto bookByName(@Argument String name) {
        return bookService.getBookByName(name);
    }

    @QueryMapping
    public PaginatedBooksResponse getBooksPages(@Argument int page,
                                             @Argument int size,
                                             @Argument String sortBy,
                                             @Argument SortDirection direction,
                                             @Argument BookFilterInput filter
    ) {

        Page<BookDto> booksPage = bookService.getAllBooksPaginated(page,size,sortBy,direction,filter);

        return new PaginatedBooksResponse(
                booksPage.getContent(), // Liste des livres de la page
                (int) booksPage.getTotalElements(), // Nombre total de livres
                booksPage.getTotalPages(), // Nombre total de pages
                booksPage.getSize(), // Taille de la page
                booksPage.getNumber() // Page actuelle
        );

    }

    /* By Mohamed Amine
    * This on the other hand is a Nested Field resolver
    * Used to map field that graphQl have no idea how to map them
    */

    @SchemaMapping
    public AuthorDto author(BookDto book) {
        AuthorDto authorDto = authorMapper.mapTo(book.getAuthor());
        //System.out.println(authorDto.getBornDate());
        return authorDto;
    }

    /*
    *  Book Creation Mutation
    */
    @MutationMapping
    public BookDto createBook(@Valid @Argument BookInput bookInput){
        return bookService.save(bookInput);
    }


}
