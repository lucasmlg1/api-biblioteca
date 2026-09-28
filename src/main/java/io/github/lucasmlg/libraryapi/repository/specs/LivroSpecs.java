package io.github.lucasmlg.libraryapi.repository.specs;

import io.github.lucasmlg.libraryapi.model.GeneroLivro;
import io.github.lucasmlg.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {
    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro generoLivro) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("genero"), generoLivro);
    }

    public static Specification<Livro> nomeAutorEqual(String nomeAutor) {
        return (root, query, cb)
                -> {
            Join<Object, Object> autor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(autor.get("nome")), "%" + nomeAutor.toUpperCase() + "%");
        };
        // return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%");
    }


     public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
            return (root, query, cb)
                    //select to_char(data_publicacao, 'YYYY') from livro;
                    -> cb.equal(cb.function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
        }





}
