package io.github.lucasmlg.libraryapi.controller;

import io.github.lucasmlg.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.lucasmlg.libraryapi.controller.dto.ErroResposta;
import io.github.lucasmlg.libraryapi.controller.dto.PesquisaLivroDTO;
import io.github.lucasmlg.libraryapi.controller.mappers.LivroMapper;
import io.github.lucasmlg.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasmlg.libraryapi.model.Livro;
import io.github.lucasmlg.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping()
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto){
            Livro livro = mapper.toEntity(dto);
            service.salvar(livro);
            var url = gerarHeaderLocation(livro.getId());
            return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
