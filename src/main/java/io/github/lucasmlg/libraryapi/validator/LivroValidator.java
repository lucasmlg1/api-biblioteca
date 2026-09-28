package io.github.lucasmlg.libraryapi.validator;

import io.github.lucasmlg.libraryapi.model.Livro;
import io.github.lucasmlg.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository repository;


    public void validar(Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new IllegalArgumentException("ISBN ja cadastrado.");
        }
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());
        if (livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));

    }
}
