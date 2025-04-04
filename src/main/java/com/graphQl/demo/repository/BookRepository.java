package com.graphQl.demo.repository;

import com.graphQl.demo.models.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity,Integer> {
    Optional<BookEntity> findBookEnityByName(String name);
}
