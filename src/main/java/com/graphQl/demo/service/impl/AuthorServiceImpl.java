package com.graphQl.demo.service.impl;

import com.graphQl.demo.domain.dto.AuthorDto;
import com.graphQl.demo.exception.AuthorNotFoundException;
import com.graphQl.demo.mapper.impl.AuthorMapper;
import com.graphQl.demo.domain.entities.AuthorEntity;
import com.graphQl.demo.repository.AuthorRepository;
import com.graphQl.demo.service.AuthorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(authorMapper::mapTo).toList();
    }

    @Override
    public AuthorDto save(AuthorDto author) {
        author.setId(null);

        /* Bull shit code */
        AuthorEntity author1 = authorMapper.mapFrom(author);
        author1.setBornDate(LocalDate.parse(author.getBornDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        AuthorEntity saved = authorRepository.save(author1);

        return authorMapper.mapTo(saved);
    }

    @Override
    public AuthorDto getAuthor(Integer id) {

        AuthorEntity author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);

        return authorMapper.mapTo(author);
    }
}
