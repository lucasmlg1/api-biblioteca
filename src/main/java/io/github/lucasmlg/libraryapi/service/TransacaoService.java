package io.github.lucasmlg.libraryapi.service;

import io.github.lucasmlg.libraryapi.model.Autor;
import io.github.lucasmlg.libraryapi.model.GeneroLivro;
import io.github.lucasmlg.libraryapi.model.Livro;
import io.github.lucasmlg.libraryapi.repository.AutorRepository;
import io.github.lucasmlg.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void atualziacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("2fc004aa-0354-4e34-9edc-cbe4b70ab9c7")).orElse(null);
        livro.setDataPublicacao(LocalDate.of(2024,6,1));
    }

    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("Pedro");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2006,4,27));

        Livro livro = new Livro();
        livro.setDataPublicacao(LocalDate.of(2000,12,31));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setPreco(BigDecimal.valueOf(100.20));
        livro.setIsbn("portugues");
        livro.setTitulo("Biografia de Pedro");

        livro.setAutor(autor);
        autorRepository.save(autor);
        livroRepository.save(livro);
    }
}
