package io.github.lucasmlg.libraryapi.controller.dto;

import io.github.lucasmlg.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;
//not blank é utilizado para Strings e not null é utilizado para outros tipos.

public record AutorDTO(UUID id, @NotBlank(message = "campo obrigatório") @Size(min = 2, max = 100, message = "campo fora do tamanho padrao") String nome, @NotNull(message = "campo obrigatório") @Past  LocalDate dataNascimento, @NotBlank(message = "campo obrigatório") @Size(min = 2, max = 100, message = "campo fora do tamanho padrao")String nacionalidade){

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);

        return autor;
    }

}
