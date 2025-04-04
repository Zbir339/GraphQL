package com.graphQl.demo.service;

import com.graphQl.demo.models.BookEntity;

import java.util.List;

public interface BookService {

    BookEntity getBookByName(String name);
    List<BookEntity> getAllBooks();
    BookEntity save(BookEntity book);

}
