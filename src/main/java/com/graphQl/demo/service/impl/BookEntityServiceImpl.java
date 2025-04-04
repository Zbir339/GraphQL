package com.graphQl.demo.service.impl;

import com.graphQl.demo.models.BookEntity;
import com.graphQl.demo.repository.BookRepository;
import com.graphQl.demo.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookEntityServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookEntityServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity getBookByName(String name) {
        return bookRepository.findBookEnityByName(name).orElse(null);
    }

    @Override
    public List<BookEntity> getAllBooks() {
        return List.of();
    }

    @Override
    public BookEntity save(BookEntity book) {
        return null;
    }
}
