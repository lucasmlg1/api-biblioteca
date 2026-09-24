package io.github.lucasmlg.libraryapi.repository;

import io.github.lucasmlg.libraryapi.model.Autor;
import io.github.lucasmlg.libraryapi.model.GeneroLivro;
import io.github.lucasmlg.libraryapi.model.Livro;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByTitulo(String titulo);
    // select * from livro where titulo = titulo;

    //select * from livro where id_autor = id;
    List<Livro> findByAutor(Autor autor);

    //select * from livro where l.isbn = isbn
    List<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);


    List<Livro> findByTituloOrPreco(String titulo, BigDecimal preco);


    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosPorTituloAndPreco();


    @Query(" select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    @Query(" select distinct l.titulo from Livro l ")
    List<String> listarTituloDosLivrosQuery();
    @Query("""
        select distinct l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Brasileira'
                order by l.genero             
        """)
    List<String> listarGeneroAutoresBrasileiros();

    //named parameters
    @Query(" select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade);


    @Query("select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> findByGeneroPositionalParameters(
            GeneroLivro generoLivro,
            String nomePropriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query(" update Livro set dataPublicacao = ?1")
    void updateDataAplicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);
}




