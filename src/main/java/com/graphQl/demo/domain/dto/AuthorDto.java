package com.graphQl.demo.domain.dto;


import com.graphQl.demo.domain.entities.BookEntity;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter @Builder
public class AuthorDto {

    private Integer id;
    private String name;
    private String bornDate;

    private List<BookEntity> books;
}
