package io.github.lucasmlg.libraryapi.repository;

import io.github.lucasmlg.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    AutorRepository repo;

    @Autowired
    TransacaoService transacaoService;

    @Test
    void transacaoSimples(){
        transacaoService.executar();

    }
    @Test
    void transacaoSimplesATT(){
        transacaoService.atualziacaoSemAtualizar();

    }

}
