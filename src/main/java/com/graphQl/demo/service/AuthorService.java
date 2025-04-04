package com.graphQl.demo.service;

import com.graphQl.demo.models.AuthorEntity;

import java.util.List;

public interface AuthorService {

    List<AuthorEntity> getAllAuthors();
    AuthorEntity save(AuthorEntity author);
    AuthorEntity getAuthor(Integer id);

}
