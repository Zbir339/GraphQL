package com.graphQl.demo.service.impl;

import com.graphQl.demo.dto.BookDto;
import com.graphQl.demo.exception.BookNotFoundException;
import com.graphQl.demo.mapper.impl.BookMapper;
import com.graphQl.demo.models.BookEntity;
import com.graphQl.demo.repository.BookRepository;
import com.graphQl.demo.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookEntityServiceImpl implements BookService {

    private BookRepository bookRepository;

    private BookMapper bookMapper;

    public BookEntityServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto getBookByName(String name) {

        Optional<BookEntity> book =bookRepository.findBookEnityByName(name);

        if (book.isEmpty()) throw new BookNotFoundException();

        return bookMapper.mapTo(book.get());

    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::mapTo).toList();
    }

    @Override
    public BookDto save(BookEntity book) {
        return bookMapper.mapTo(bookRepository.save(book));
    }
}
