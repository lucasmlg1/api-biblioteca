package io.github.lucasmlg.libraryapi.controller;


import io.github.lucasmlg.libraryapi.controller.dto.AutorDTO;
import io.github.lucasmlg.libraryapi.controller.dto.ErroResposta;
import io.github.lucasmlg.libraryapi.controller.mappers.AutorMapper;
import io.github.lucasmlg.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.lucasmlg.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasmlg.libraryapi.model.Autor;
import io.github.lucasmlg.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper mapper;


    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        Autor autor = mapper.toEntity(autorDTO);
        autorService.saveAutor(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();

    }

    //http://localhost:8080/autores/{id}
    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        return autorService.obterPorID(idAutor).map(autor -> {
            AutorDTO dto = mapper.toDTO(autor);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorID(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletarAutor(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultadoPesquisa = autorService.pesquisa(nome, nacionalidade);
        List<AutorDTO> resultadoDTO = resultadoPesquisa.stream().
                map(mapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(resultadoDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        var autorId = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorID(autorId);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.saveAutor(autor);
        return ResponseEntity.noContent().build();
    }
}
