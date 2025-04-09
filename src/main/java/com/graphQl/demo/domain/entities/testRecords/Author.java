package com.graphQl.demo.domain.entities.testRecords;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public record Author(String id, String name, LocalDate bornDate) {

    public static List<Author> authors = Arrays.asList(
            new Author("author-1", "Joshua", LocalDate.of(2024,4,25)),
            new Author("author-2", "Douglas", LocalDate.of(2000,3,2)),
            new Author("author-3", "Bill", LocalDate.of(2000,1,7))
    );

    public static Author getById(String id) {
        return authors.stream()
                .filter(author -> author.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}