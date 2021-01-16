package com.algaworks.socialbooks.resources;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.LivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RestController
// Essa anotação é responsável que essa classe vai ser um Resources (Controller).
@RequestMapping("/livros")
// O RequestMapping na Classe faz com que todas funções dentro da classe tenham o mapeamento indicado anteriormente.
public class LivrosResources {

    @Autowired
    private LivrosRepository livrosRepository;

    @RequestMapping(method = RequestMethod.GET)
    // Caso não tiver o @RequestMappinh na Classe, adicione value = "/livros" junto com Method.
    // Permite mapear uma URI para determinado método.
    // Method vai informar que deseja utilizar o método GET (Adquirir informação).
    public List<Livro> listar(){
        // Vamos fazer esse método listar alguns livros.
        return livrosRepository.findAll();
    }
    @RequestMapping(method = RequestMethod.POST)
    // Caso não tiver o @RequestMappinh na Classe, adicione value = "/livros" junto com Method.
    // Method vai informar que deseja criar uma nova informação.
    public void salvar(@RequestBody Livro livro){
        // O RequestBody vai permitir que a informação seja salva no Banco de Dados
        livrosRepository.save(livro);
    }

    // A função buscar vai ter o efeito de buscar um determinado livro em base no ID.
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    // o Value com {id} é um parametro que deseja a variavel indicada.
    public Optional<Livro> buscar(@PathVariable("id") Long id) {
        //O PathVariable permite usar a variavel indicado no value e colocar a informação na função buscar.
        return livrosRepository.findById(id);
        //public Livro buscar(@PathVariable("id") Long id){
        //return livrosRepository.findOne(id);
        //}
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    // Metódo DELETE remove o recurso.
    public void deletar(@PathVariable("id") Long id){
        livrosRepository.deleteById(id);
    }


}
