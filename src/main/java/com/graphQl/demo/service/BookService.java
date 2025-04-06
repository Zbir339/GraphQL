package com.graphQl.demo.service;

import com.graphQl.demo.dto.BookDto;
import com.graphQl.demo.models.BookEntity;

import java.util.List;

public interface BookService {

    BookDto getBookByName(String name);
    List<BookDto> getAllBooks();
    BookDto save(BookEntity book);

}
