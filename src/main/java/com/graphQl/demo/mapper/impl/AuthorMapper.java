package com.graphQl.demo.mapper.impl;

import com.graphQl.demo.dto.AuthorDto;
import com.graphQl.demo.mapper.Mapper;
import com.graphQl.demo.models.AuthorEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // TypeMap configuration personnalisée et utiliser pour un model specific
        // Sinon on peut utiliser Converter pour cree un regle global sur toutes les dates
        /* Personaliser les map types pour mon model Date */
        TypeMap<AuthorEntity, AuthorDto> typeMap = this.modelMapper.createTypeMap(AuthorEntity.class, AuthorDto.class);

        typeMap.addMapping(
                AuthorEntity::getBornDate,
                (dest, v) -> dest.setBornDate(v != null ? v.toString() : null)
        );
    }

    @Override
    public AuthorDto mapTo(AuthorEntity author) {
        return modelMapper.map(author,AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
