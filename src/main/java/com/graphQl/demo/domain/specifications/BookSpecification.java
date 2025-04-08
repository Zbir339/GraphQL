package com.graphQl.demo.domain.specifications;

import com.graphQl.demo.domain.entities.BookEntity;
import org.springframework.data.jpa.domain.Specification;

/* This is a specification Class I'll use it for filtering paginating etc */
public class BookSpecification {

    public static Specification<BookEntity> hasAuthorName(String name){
        return ((root, query, cb) ->
                /* cb.like( field, string to match ) */
                cb.like(cb.lower(root.get("author").get("name")),"%"+name.toLowerCase() +"%")
                );
    }

    public static Specification<BookEntity> priceLowerThan(Float max){
        return (((root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("price"),max)
                ));
    }

    public static Specification<BookEntity> priceGreaterThan(Float min){
        return (((root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("price"),min)
        ));
    }

    public static Specification<BookEntity> titleContains(String name){
        return (((root, query, cb) ->
                cb.like(cb.lower(root.get("name")),"%"+name.toLowerCase()+"%")
                ));
    }

}
