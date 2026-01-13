package io.github.wesolsv.libraryapi.controller.dto;

import io.github.wesolsv.libraryapi.model.Autor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório")
        @Size(min= 2, max = 100, message = "Campo fora do tamanho padrao")
        @Schema(name = "nome")
        String nome,
        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser uma data futura")
        @Schema(name = "dataNascimento")
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatório")
        @Size(min= 2, max = 50, message = "Campo fora do tamanho padrao")
        @Schema(name = "nacionalidade")
        String nacionalidade
) {
}
