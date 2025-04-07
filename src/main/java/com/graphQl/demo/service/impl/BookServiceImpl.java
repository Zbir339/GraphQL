package com.graphQl.demo.service.impl;

import com.graphQl.demo.dto.BookDto;
import com.graphQl.demo.dto.BookInput;
import com.graphQl.demo.exception.BookNotFoundException;
import com.graphQl.demo.mapper.impl.BookInputMapper;
import com.graphQl.demo.mapper.impl.BookMapper;
import com.graphQl.demo.models.AuthorEntity;
import com.graphQl.demo.models.BookEntity;
import com.graphQl.demo.repository.BookRepository;
import com.graphQl.demo.service.BookService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private BookInputMapper bookInputMapper;
    /* The entity manager in our case is used to create linked like object that refers to somewhat an existing
    * object inside my database
    *  */
    private EntityManager entityManager;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, BookInputMapper bookInputMapper, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.bookInputMapper = bookInputMapper;
        this.entityManager = entityManager;
    }

    @Override
    public BookDto getBookByName(String name) {

        BookEntity book =bookRepository.findBookEnityByName(name).orElseThrow(BookNotFoundException::new);

        return bookMapper.mapTo(book);

    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::mapTo).toList();
    }

    @Override
    public BookDto save(BookEntity book) {
        return bookMapper.mapTo(bookRepository.save(book));
    }

    @Override
    public BookDto save(BookInput bookInput) {
        BookEntity book = bookInputMapper.mapTo(bookInput);
        book.setId(null);
        book.setAuthor(entityManager.getReference(AuthorEntity.class, bookInput.getAuthorId()));
        return bookMapper.mapTo(bookRepository.save(book));
    }
}
