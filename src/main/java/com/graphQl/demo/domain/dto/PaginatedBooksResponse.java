package com.graphQl.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class PaginatedBooksResponse {

    private List<BookDto> content;
    private int totalElements;
    private int totalPages;
    private int pageSize;
    private int currentPage;

}
