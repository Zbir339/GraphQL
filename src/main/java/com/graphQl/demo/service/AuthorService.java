package com.graphQl.demo.service;

import com.graphQl.demo.dto.AuthorDto;
import com.graphQl.demo.models.AuthorEntity;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();
    AuthorDto save(AuthorDto author);
    AuthorDto getAuthor(Integer id);

}
