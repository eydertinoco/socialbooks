package com.algaworks.socialbooks.services;

import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.ComentariosRepository;
import com.algaworks.socialbooks.repository.LivrosRepository;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service //Indica que é uma camada de Serviço
public class LivrosService {

    @Autowired
    private LivrosRepository livrosRepository;

    @Autowired
    private ComentariosRepository comentariosRepository;

    public List<Livro> listar(){
        return livrosRepository.findAll();
    }

    public Livro buscar(Long id){
        return this.livrosRepository
                .findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(""));




//        Optional<Livro> livro =livrosRepository.findById(id);
//
//        if (livro.equals(Optional.empty())){
//            throw new LivroNaoEncontradoException("O livro não pode ser encontrado.");
//        }
//        return livro;
    }

    public Livro salvar(Livro livro) {
        livro.setId(null); //Garante a criação de uma nova instância e não vai atualizar nenhuma.
        return livrosRepository.save(livro);
    }

    public void deletar(Long id) {
        try {
            livrosRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw  new LivroNaoEncontradoException("O livro não pode ser encontrado.");
        }
    }

    public void atualizar(Livro livro) {
        verificarExistencia(livro);
        livrosRepository.save(livro);
    }

    private void verificarExistencia(Livro livro) {
        buscar(livro.getId());
    }

    public Comentario salvarComentario(Long id, Comentario comentario) {
        Livro livro =livrosRepository.getOne(id);

        comentario.setLivro(livro);
        comentario.setData(new Date());

        return comentariosRepository.save(comentario);
    }

    public List<Comentario> listarComentarios(Long livroId) {
        Livro livro = buscar(livroId);



        return livro.getComentarios();
    }

}
