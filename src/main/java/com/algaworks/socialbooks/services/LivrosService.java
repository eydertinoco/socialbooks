package com.algaworks.socialbooks.services;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.LivrosRepository;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //Indica que é uma camada de Serviço
public class LivrosService {

    @Autowired
    private LivrosRepository livrosRepository;

    public List<Livro> listar(){
        return livrosRepository.findAll();
    }

    public Optional<Livro> buscar(Long id){
        Optional<Livro> livro =livrosRepository.findById(id);

        if (livro.equals(Optional.empty())){
            throw new LivroNaoEncontradoException("O livro não pode ser encontrado.");
        }
        return livro;
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

}
