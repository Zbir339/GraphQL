package com.graphQl.demo.service.impl;

import com.graphQl.demo.domain.dto.BookDto;
import com.graphQl.demo.domain.dto.BookFilterInput;
import com.graphQl.demo.domain.dto.BookInput;
import com.graphQl.demo.domain.enums.SortDirection;
import com.graphQl.demo.domain.specifications.BookSpecification;
import com.graphQl.demo.exception.entities.BookNotFoundException;
import com.graphQl.demo.mapper.impl.BookInputMapper;
import com.graphQl.demo.mapper.impl.BookMapper;
import com.graphQl.demo.domain.entities.AuthorEntity;
import com.graphQl.demo.domain.entities.BookEntity;
import com.graphQl.demo.repository.BookRepository;
import com.graphQl.demo.service.BookService;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookInputMapper bookInputMapper;
    /* The entity manager in our case is used to create linked like object that refers to somewhat an existing
    * object inside my database
    *  */
    private final EntityManager entityManager;

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

    @Override
    public Page<BookDto> getAllBooksPaginated(int page, int size, String sortedBy, SortDirection direction, BookFilterInput bookFilters){

        /*
        * First Layer is to create our pageable
        * default values are 0 page , 5 elems
        * */
        Pageable pageable = PageRequest.of(
                page,size,
                direction == SortDirection.ASC? Sort.by(sortedBy).ascending() : Sort.by(sortedBy).descending()
        );

        /*
        * We then initialize our spec's to null if the filters are nowhere to be found
        * */
        Specification<BookEntity> spec = Specification.where(null);

        if(bookFilters.getAuthorName() != null){
            spec = spec.and(BookSpecification.hasAuthorName(bookFilters.getAuthorName()));
        }

        if(bookFilters.getMaxPrice() != null){
            spec = spec.and(BookSpecification.priceLowerThan(bookFilters.getMaxPrice()));
        }

        if(bookFilters.getMinPrice() != null){
            spec = spec.and(BookSpecification.priceGreaterThan(bookFilters.getMinPrice()));
        }

        if(bookFilters.getTitleContains() != null){
            spec = spec.and(BookSpecification.titleContains(bookFilters.getTitleContains()));
        }

        return bookRepository.findAll(spec,pageable).map(bookMapper::mapTo);
    }



}
