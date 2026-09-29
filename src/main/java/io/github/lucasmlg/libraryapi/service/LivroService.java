package io.github.lucasmlg.libraryapi.service;


import io.github.lucasmlg.libraryapi.model.GeneroLivro;
import io.github.lucasmlg.libraryapi.model.Livro;
import io.github.lucasmlg.libraryapi.repository.LivroRepository;
import io.github.lucasmlg.libraryapi.repository.specs.LivroSpecs;
import io.github.lucasmlg.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.lucasmlg.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator validator;

    public Livro salvar(Livro livro) {

        validator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findByIdComAutor(id);
    }
    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer dataPublicacao,
                                Integer pagina,
                                Integer tamanhoPagina){

        Specification<Livro> specs = Specification.where((root, query, cb) ->  cb.conjunction() );

        if(isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if(genero != null){
            specs = specs.and(generoEqual(genero));
        }
        if (dataPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(dataPublicacao));
        }

        Pageable pagebleRequest = PageRequest.of(pagina, tamanhoPagina);
        return livroRepository.findAll(specs, pagebleRequest);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar um livro, é necessário que ele já exista!");
        }
        validator.validar(livro);
        livroRepository.save(livro);
    }
}
