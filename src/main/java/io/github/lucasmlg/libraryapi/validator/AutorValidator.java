package io.github.lucasmlg.libraryapi.validator;

import io.github.lucasmlg.libraryapi.exceptions.RegistroDuplicado;
import io.github.lucasmlg.libraryapi.model.Autor;
import io.github.lucasmlg.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }


    public void validarAutor(Autor autor){
        if (existeAutorCadastrado(autor)){
            throw new RegistroDuplicado("Autor já está cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(),autor.getDataNascimento(), autor.getNacionalidade());
        if (autor.getId()==null){
            return autorEncontrado.isPresent();
        }
        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();

    }
}
