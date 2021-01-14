package com.algaworks.socialbooks.resources;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.LivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
// Essa anotação é responsável que essa classe vai ser um Resources (Controller).
public class LivrosResources {

    @Autowired
    private LivrosRepository livrosRepository;

    @RequestMapping(value = "/livros", method = RequestMethod.GET)
    // Permite mapear uma URI para determinado método.
    //
    public List<Livro> listar(){
        // Vamos fazer esse método listar alguns livros.

        return livrosRepository.findAll();
    }

}
