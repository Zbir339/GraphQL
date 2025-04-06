package com.graphQl.demo.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @ManyToOne
    //@Fetch(FetchMode.JOIN)
    private AuthorEntity author;

    public BookEntity(Integer id,String name,Float price,String description){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

}
