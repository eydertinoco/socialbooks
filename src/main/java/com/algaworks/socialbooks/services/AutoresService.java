package com.algaworks.socialbooks.services;

import com.algaworks.socialbooks.domain.Autor;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.AutoresRepository;
import com.algaworks.socialbooks.services.exceptions.AutorExistenteException;
import com.algaworks.socialbooks.services.exceptions.AutorNaoEncontradoException;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutoresService {

    @Autowired
    private AutoresRepository autoresRepository;

    public List<Autor> listar() {
        return  autoresRepository.findAll();
    }

    public Autor salvar(Autor autor){
        if (autor.getId() != null) {
            if (autoresRepository.findById(autor.getId()).isEmpty()) {
                return autoresRepository.save(autor);
            } else if ((autoresRepository.findById(autor.getId()).get()).getId().equals(autor.getId())) {
                throw new AutorExistenteException("O Autor já existe.");
            }
        }
        return autoresRepository.save(autor);
    }

    public Autor buscar (Long id) {
        return this.autoresRepository
                .findById(id)
                .orElseThrow(() -> new AutorNaoEncontradoException("O autor não pode ser encontrado."));
//        Autor autor = autoresRepository.findById(id);
//         if (autor == null){
//             throw new AutorNaoEncontradoException("O autor não pode ser encontrado.");
//         }
//         return autor;
    }

}
