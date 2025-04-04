package com.graphQl.demo.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "authors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder @ToString
public class AuthorEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private LocalDate bornDate;

    @OneToMany(mappedBy = "author")
    private List<BookEntity> books;

}
