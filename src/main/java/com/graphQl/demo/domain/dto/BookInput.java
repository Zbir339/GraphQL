package com.graphQl.demo.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class BookInput {

   /* I used this so i can avoid db checks
    *  The catch here would be handling errors
    *  The benefit is no query search before inserting
    */
    @NotBlank @Size(min = 4 , max= 50)
    private String name;
    @PositiveOrZero @NotNull
    private Float price;

    private String description;

    @Positive @NotNull
    private Integer authorId;
}
