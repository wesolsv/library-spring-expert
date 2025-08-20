package io.github.wesolsv.libraryapi.controller.dto;

import io.github.wesolsv.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutorDTO(
        @NotBlank(message = "campo obrigatório")
        @Size(min= 2, max = 100, message = "Campo fora do tamanho padrao")
        String nome,

        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser uma data futura")
        LocalDate dataNascimento,

        @NotBlank(message = "campo obrigatório")
        @Size(min= 2, max = 50, message = "Campo fora do tamanho padrao")
        String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }
}
