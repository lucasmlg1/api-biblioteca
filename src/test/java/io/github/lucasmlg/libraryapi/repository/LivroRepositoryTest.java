package io.github.lucasmlg.libraryapi.repository;

import io.github.lucasmlg.libraryapi.model.Autor;
import io.github.lucasmlg.libraryapi.model.GeneroLivro;
import io.github.lucasmlg.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setDataPublicacao(LocalDate.of(2000,12,31));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setPreco(BigDecimal.valueOf(100.20));
        livro.setIsbn("Aniplex");
        livro.setTitulo("Dr. Tenma");

        Autor autor = autorRepository.findById(UUID.fromString("4b31ac55-aac3-4bcf-b602-077c4b3b16f7")).orElse(null);

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
     void salvarAutorLivroTest(){
        Livro livro = new Livro();
        livro.setDataPublicacao(LocalDate.of(2000,12,31));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(100.20));
        livro.setIsbn("Aurora");
        livro.setTitulo("Sangatsu no Lion");

        Autor autor = new Autor();
        autor.setNome("Caio");
        autor.setNacionalidade("Japonês");
        autor.setDataNascimento(LocalDate.of(2006,4,27));

        autorRepository.save(autor);
        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void salvarAutorLivroCascadeTest(){
        Livro livro = new Livro();
        livro.setDataPublicacao(LocalDate.of(2000,12,31));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(100.20));
        livro.setIsbn("Aurora");
        livro.setTitulo("Sangatsu no Lion");

        Autor autor = new Autor();
        autor.setNome("Caio");
        autor.setNacionalidade("Japonês");
        autor.setDataNascimento(LocalDate.of(2006,4,27));
        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void alterarAutorLivroTest(){
        UUID id = (UUID.fromString("364ce9af-d514-47f6-bc97-53497bae60f4"));
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);


        UUID idAutor = UUID.fromString("4098d6d2-cded-44d8-a5e0-33bba52ea6ff");
        var lucas = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(lucas);
        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void excluirLivroTest(){
        UUID idLivroExcluido = UUID.fromString("a02a0c82-086f-4ef5-b08e-2c2ff321d71c");
        livroRepository.deleteById(idLivroExcluido);
    }


    @Test
    void excluirLivroCascadeTest(){
        UUID idLivroExcluido = UUID.fromString("ab73d8d1-14eb-457e-893f-118b7171fa54");
        livroRepository.deleteById(idLivroExcluido);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("364ce9af-d514-47f6-bc97-53497bae60f4");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void buscarLivroPorTitulo(){
        List<Livro> sangatsuNoLion = livroRepository.findByTitulo("Sangatsu no Lion");
        sangatsuNoLion.forEach(System.out::println);
    }

    @Test
    void buscarLivroPorIsbn(){
        List<Livro> byIsbn = livroRepository.findByIsbn("5346-2345");
        byIsbn.forEach(System.out::println);
    }

    @Test
    void buscarLivrosPorTituloAndPreco(){
        List<Livro> livros = livroRepository.listarTodosPorTituloAndPreco();
        livros.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosQuery(){
        List<Autor> autors = livroRepository.listarAutoresDosLivros();
        autors.forEach(System.out::println);
    }

    @Test
    void listarTituloDosLivrosQuery(){
        List<String> titulos = livroRepository.listarTituloDosLivrosQuery();
        titulos.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrasileiros(){
        List<String> strings = livroRepository.listarGeneroAutoresBrasileiros();
        strings.forEach(System.out::println);
    }

    @Test
    void listarLivrosPorGenero(){
        List<Livro> byGenero = livroRepository.findByGenero(GeneroLivro.ROMANCE, "titulo");
        byGenero.forEach(System.out::println);
    }

    @Test
    void listarLivrosPorGeneroPositionalParams(){
        var result = livroRepository.findByGeneroPositionalParameters(GeneroLivro.FANTASIA, "preco");
        result.forEach(System.out::println);
    }
    @Test
    void deletePorGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }
}
