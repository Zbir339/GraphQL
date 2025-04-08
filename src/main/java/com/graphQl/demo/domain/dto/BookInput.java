package com.graphQl.demo.domain.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class BookInput {

   /* I used this so i can avoid db checks
    *  The catch here would be handling errors
    *  The benefit is no query search before inserting
    */
    private String name;
    private Float price;
    private String description;
    private Integer authorId;
}
