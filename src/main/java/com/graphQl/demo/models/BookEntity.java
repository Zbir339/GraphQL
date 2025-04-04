package com.graphQl.demo.models;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "books")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Float price;
    private String description;

    @ManyToOne
    private AuthorEntity author;

}
