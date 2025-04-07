package com.graphQl.demo.repository;

import com.graphQl.demo.models.AuthorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity,Integer> {

    @EntityGraph(attributePaths = "books")
    List<AuthorEntity> findAll();


}
