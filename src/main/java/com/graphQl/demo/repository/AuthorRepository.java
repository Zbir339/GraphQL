package com.graphQl.demo.repository;

import com.graphQl.demo.models.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity,Integer> {

}
