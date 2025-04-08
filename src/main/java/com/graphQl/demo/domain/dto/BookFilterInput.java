package com.graphQl.demo.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class BookFilterInput {

    private String authorName;
    private Float minPrice;
    private Float maxPrice;
    private String titleContains;

}
