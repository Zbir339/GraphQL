package com.graphQl.demo.dto;


import com.graphQl.demo.models.BookEntity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
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
