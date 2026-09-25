package io.github.lucasmlg.libraryapi.controller.common;

import io.github.lucasmlg.libraryapi.controller.dto.ErroCampo;
import io.github.lucasmlg.libraryapi.controller.dto.ErroResposta;
import io.github.lucasmlg.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.lucasmlg.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // faz capturar o erro e jogar o erro que foi capturado
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e ){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> collectErroCampo = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", collectErroCampo);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return  ErroResposta.conflito(e.getMessage());
    }
    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPesrmitidaException(OperacaoNaoPermitidaException e){
        return  ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e){
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado, vê com os cara lá pra resolver",
                List.of());


    }
}
