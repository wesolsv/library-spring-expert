package io.github.wesolsv.libraryapi.controller.mappers;

import io.github.wesolsv.libraryapi.controller.dto.UsuarioDTO;
import io.github.wesolsv.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
