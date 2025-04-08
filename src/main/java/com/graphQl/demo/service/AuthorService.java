package com.graphQl.demo.service;

import com.graphQl.demo.domain.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();
    AuthorDto save(AuthorDto author);
    AuthorDto getAuthor(Integer id);

}
