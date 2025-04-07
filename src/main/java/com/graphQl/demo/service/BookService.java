package com.graphQl.demo.service;

import com.graphQl.demo.dto.BookDto;
import com.graphQl.demo.dto.BookInput;
import com.graphQl.demo.models.BookEntity;

import java.util.List;

public interface BookService {

    BookDto getBookByName(String name);
    List<BookDto> getAllBooks();
    // this is the old data saving
    BookDto save(BookEntity book);

    BookDto save(BookInput bookInput);
}
