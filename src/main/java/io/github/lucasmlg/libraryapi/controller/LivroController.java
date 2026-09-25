package io.github.lucasmlg.libraryapi.controller;

import io.github.lucasmlg.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.lucasmlg.libraryapi.controller.dto.ErroResposta;
import io.github.lucasmlg.libraryapi.controller.mappers.LivroMapper;
import io.github.lucasmlg.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasmlg.libraryapi.model.Livro;
import io.github.lucasmlg.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping()
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try{
            Livro livro = mapper.toEntity(dto);
            service.salvar(livro);
            var url = gerarHeaderLocation(livro.getId());
            //enviar a entidade para o service validar
             // salvar na base
            //criar url para acesso dos dados do livro
            //retornar codigo created com header location
            return ResponseEntity.created(url).build();
        }catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
