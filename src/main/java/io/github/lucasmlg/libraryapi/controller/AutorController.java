package io.github.lucasmlg.libraryapi.controller;


import io.github.lucasmlg.libraryapi.controller.dto.AutorDTO;
import io.github.lucasmlg.libraryapi.controller.dto.ErroResposta;
import io.github.lucasmlg.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.lucasmlg.libraryapi.exceptions.RegistroDuplicado;
import io.github.lucasmlg.libraryapi.model.Autor;
import io.github.lucasmlg.libraryapi.repository.AutorRepository;
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
public class AutorController {

    private final AutorService autorService;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autorDTO){
        try {
            Autor autor = autorDTO.mapearParaAutor();
            autorService.saveAutor(autor);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autor.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch(RegistroDuplicado e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    //http://localhost:8080/autores/{id}
    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorID(idAutor);
        if(autorOptional.isPresent()){
            Autor entidade = autorOptional.get();
            AutorDTO dto = new AutorDTO(idAutor, entidade.getNome(), entidade.getDataNascimento(), entidade.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterPorID(idAutor);
            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            autorService.deletarAutor(autorOptional.get());
            return ResponseEntity.noContent().build();
        } catch(OperacaoNaoPermitidaException e){
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultadoPesquisa = autorService.pesquisa(nome, nacionalidade);
        List<AutorDTO> resultadoDTO = resultadoPesquisa.stream().
                map(autor -> new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade())).collect(Collectors.toList());

        return ResponseEntity.ok(resultadoDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto){
        try{
            var autorId = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterPorID(autorId);
            if(autorOptional.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            autorService.saveAutor(autor);
            return ResponseEntity.noContent().build();
        }catch(RegistroDuplicado e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
 }
