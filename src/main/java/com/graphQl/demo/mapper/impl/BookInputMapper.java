package com.graphQl.demo.mapper.impl;

import com.graphQl.demo.domain.dto.BookInput;
import com.graphQl.demo.mapper.Mapper;
import com.graphQl.demo.domain.entities.BookEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookInputMapper implements Mapper<BookInput, BookEntity> {


    private final ModelMapper modelMapper;

    public BookInputMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


//        TypeMap<BookInput, BookEntity> typeMap = this.modelMapper.createTypeMap(BookInput.class, BookEntity.class);
//        /* We are skipping null or undefined parts in our mapper */
//        typeMap.addMappings(mapper -> {
//                    mapper.skip(BookEntity::setId);
//                    mapper.skip(BookEntity::setAuthor);
//                });

    }

    @Override
    public BookEntity mapTo(BookInput bookInput) {

        return modelMapper.map(bookInput,BookEntity.class);

    }

    /* Not needed simply said we only receive an input and we return a dto */
    @Override
    public BookInput mapFrom(BookEntity bookEntity) {
        return null;
    }
}
