package com.graphQl.demo.domain.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

@Entity(name = "books")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Float price;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    //@Fetch(FetchMode.JOIN)
    private AuthorEntity author;

    public BookEntity(Integer id,String name,Float price,String description){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

}
