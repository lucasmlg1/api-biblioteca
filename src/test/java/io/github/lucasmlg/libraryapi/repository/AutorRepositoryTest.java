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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Lucas");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2006,4,27));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Dados do Autor: " + autorSalvo);
    }


    @Test
    public void atualizarTest(){
        var id = UUID.fromString("2531fdff-6d3f-4e68-967a-85f8df4983bd");
        Optional<Autor> autor1 = autorRepository.findById(id);
        if (autor1.isPresent()){
            Autor possivelAutor = autor1.get();
            System.out.println("dados do autor: " + possivelAutor);
            possivelAutor.setDataNascimento(LocalDate.of(2000,2,20));
            autorRepository.save(possivelAutor);
        }
    }


    @Test
    public void listarTest(){
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void contarAutores(){
        System.out.println("Contagem de autores: " + autorRepository.count());
    }


    @Test
    public void deletarAutores(){
        var id = UUID.fromString("2531fdff-6d3f-4e68-967a-85f8df4983bd");
        var autor = autorRepository.getById(id);
        autorRepository.deleteById(id);
        System.out.println("Informações do autor excluido: " + autor);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(1997,9,17));

        Livro livro = new Livro();
        livro.setDataPublicacao(LocalDate.of(2000,12,31));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setPreco(BigDecimal.valueOf(40));
        livro.setIsbn("1234-532");
        livro.setTitulo("Bungo Stray Dogs");
        livro.setAutor(autor);


        Livro livro2 = new Livro();
        livro2.setDataPublicacao(LocalDate.of(2000,12,31));
        livro2.setGenero(GeneroLivro.FANTASIA);
        livro2.setPreco(BigDecimal.valueOf(40));
        livro2.setIsbn("5346-2345");
        livro2.setTitulo("Attack on Titan");
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
    }

    @Test
    void buscarLivrosAutor(){
        var id = UUID.fromString("d4c0391e-6776-4089-9d89-b40ef40755bb");
        var autor = autorRepository.findById(id).get();

        List<Livro> livrosa = livroRepository.findByAutor(autor);
        autor.setLivros(livrosa);
        autor.getLivros().forEach(System.out::println);
    }
}
