package io.github.lucasmlg.libraryapi.service;


import io.github.lucasmlg.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.lucasmlg.libraryapi.model.Autor;
import io.github.lucasmlg.libraryapi.repository.AutorRepository;
import io.github.lucasmlg.libraryapi.repository.LivroRepository;
import io.github.lucasmlg.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;

    public Autor saveAutor(Autor autor){
        autorValidator.validarAutor(autor);
        return autorRepository.save(autor);
    }

    public void atualizarAutor(Autor autor){
        if(autor.getId()== null){
            throw new IllegalArgumentException("Para atualizar é necessário que o Autor já esteja salvo e tenha um id.");
        }
        autorValidator.validarAutor(autor);

        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorID(UUID id){
        return autorRepository.findById(id);
    }

    public void deletarAutor(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é possível excluir o Autor que possui livros cadastrados.");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if (nome != null){
            return autorRepository.findByNome(nome);
        }
        if(nacionalidade!= null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }
        return autorRepository.findAll();
    }


    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }

}
