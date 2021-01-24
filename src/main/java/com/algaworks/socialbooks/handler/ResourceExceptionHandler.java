package com.algaworks.socialbooks.handler;

import com.algaworks.socialbooks.domain.DetalhesErro;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice //Permite que interceptemos qualquer tipo de exceção que possa vir a cometer no código
public class ResourceExceptionHandler {

    // Ao capturar a exceção vamos retornar uma resposta adqueada.
    @ExceptionHandler(LivroNaoEncontradoException.class)
    // Ao iniciar a aplicação, qualquer código que utilizar o LivroNaoEncontradoException será capturado e tratato aqui.
    public ResponseEntity<DetalhesErro> handleLivroNaoEncontradoException
                    (LivroNaoEncontradoException e, HttpServletRequest request) {

        DetalhesErro erro = new DetalhesErro();
        erro.setStatus(404l);
        erro.setTitulo("O livro não pode ser encontrado");
        erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/404");
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

}
