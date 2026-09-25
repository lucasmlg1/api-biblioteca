package io.github.lucasmlg.libraryapi.controller.mappers;

import io.github.lucasmlg.libraryapi.controller.dto.AutorDTO;
import io.github.lucasmlg.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);

}
