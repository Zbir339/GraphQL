package com.graphQl.demo.controller;

import com.graphQl.demo.dto.AuthorDto;
import com.graphQl.demo.dto.BookDto;
import com.graphQl.demo.dto.BookInput;
import com.graphQl.demo.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public List<AuthorDto> authors() {
        return authorService.getAllAuthors();
    }

    @QueryMapping
    public AuthorDto authorById(@Argument Integer id) {
        return authorService.getAuthor(id);
    }

    @MutationMapping
    public AuthorDto createAuthor(@Argument AuthorDto author){
        return authorService.save(author);
    }


}
