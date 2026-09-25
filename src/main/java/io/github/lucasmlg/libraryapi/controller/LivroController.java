package io.github.lucasmlg.libraryapi.controller;

import io.github.lucasmlg.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.lucasmlg.libraryapi.controller.dto.ErroResposta;
import io.github.lucasmlg.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasmlg.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @PostMapping("")
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO){
        try{
            //mapear dto para entidade
            //enviar a entidade para o service validar
             // salvar na base
            //criar url para acesso dos dados do livro
            //retornar codigo created com header location


            return ResponseEntity.ok(cadastroLivroDTO);
        }catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
