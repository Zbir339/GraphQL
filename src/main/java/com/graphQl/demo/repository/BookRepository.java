package com.graphQl.demo.repository;

import com.graphQl.demo.domain.entities.BookEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity,Integer>, JpaSpecificationExecutor<BookEntity> {
    @EntityGraph(attributePaths = "author")
    Optional<BookEntity> findBookEnityByName(String name);

//    @Query("select b from books b join fetch b.author a")

    /*this is a power jpa way of telling the orm to eagerly get the data
    * without specifying it in your methode making it more care free
    * */
    @EntityGraph(attributePaths = "author")
    List<BookEntity> findAll();

}
