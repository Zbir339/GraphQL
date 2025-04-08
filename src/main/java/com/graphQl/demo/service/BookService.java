package com.graphQl.demo.service;

import com.graphQl.demo.domain.dto.BookDto;
import com.graphQl.demo.domain.dto.BookFilterInput;
import com.graphQl.demo.domain.dto.BookInput;
import com.graphQl.demo.domain.entities.BookEntity;
import com.graphQl.demo.domain.enums.SortDirection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    BookDto getBookByName(String name);
    List<BookDto> getAllBooks();
    // this is the old data saving
    BookDto save(BookEntity book);
    BookDto save(BookInput bookInput);
    Page<BookDto> getAllBooksPaginated(int page, int size, String sortedBy, SortDirection direction, BookFilterInput bookFilters);
}
