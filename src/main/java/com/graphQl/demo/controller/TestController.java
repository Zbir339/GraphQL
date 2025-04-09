package com.graphQl.demo.controller;


import com.graphQl.demo.domain.entities.testRecords.Author;
import com.graphQl.demo.domain.entities.testRecords.Book;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TestController {


    @QueryMapping
    public List<Author> testScalarsAuthors(){
        return Author.authors;
    }
}
