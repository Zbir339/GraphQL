package com.graphQl.demo.dto;

import com.graphQl.demo.models.AuthorEntity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class BookDto {

    private Integer id;

    private String name;
    private Float price;
    private String description;
    //@Fetch(FetchMode.JOIN)
    private AuthorEntity author;
}
