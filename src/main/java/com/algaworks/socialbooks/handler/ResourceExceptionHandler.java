package com.algaworks.socialbooks.handler;

import com.algaworks.socialbooks.domain.DetalhesErro;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.services.exceptions.AutorExistenteException;
import com.algaworks.socialbooks.services.exceptions.AutorNaoEncontradoException;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(AutorExistenteException.class)
    public ResponseEntity<DetalhesErro> handleAutorExistenteException
            (AutorExistenteException e, HttpServletRequest request) {

        DetalhesErro erro = new DetalhesErro();
        erro.setStatus(409l);
        erro.setTitulo("Autor já existente");
        erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/409");
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(AutorNaoEncontradoException.class)
    // Ao iniciar a aplicação, qualquer código que utilizar o LivroNaoEncontradoException será capturado e tratato aqui.
    public ResponseEntity<DetalhesErro> handleAutorNaoEncontradoException
            (AutorNaoEncontradoException e, HttpServletRequest request) {

        DetalhesErro erro = new DetalhesErro();
        erro.setStatus(404l);
        erro.setTitulo("O Autor não pode ser encontrado");
        erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/404");
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    // Ao iniciar a aplicação, qualquer código que utilizar o LivroNaoEncontradoException será capturado e tratato aqui.
    public ResponseEntity<DetalhesErro> handleDataIntegrityViolationException
            (DataIntegrityViolationException e, HttpServletRequest request) {

        DetalhesErro erro = new DetalhesErro();
        erro.setStatus(400l);
        erro.setTitulo("Requisição Invalida.");
        erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/400");
        erro.setTimestamp(System.currentTimeMillis());

//        return ResponseEntity.badRequest(erro);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

}
