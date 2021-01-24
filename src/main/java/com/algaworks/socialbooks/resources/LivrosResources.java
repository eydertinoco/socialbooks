package com.algaworks.socialbooks.resources;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.LivrosRepository;
import com.algaworks.socialbooks.services.LivrosService;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
// Essa anotação é responsável que essa classe vai ser um Resources (Controller).
@RequestMapping("/livros")
// O RequestMapping na Classe faz com que todas funções dentro da classe tenham o mapeamento indicado anteriormente.
public class LivrosResources {

    @Autowired
    private LivrosService livrosService;

    @RequestMapping(method = RequestMethod.GET)
    // Caso não tiver o @RequestMappinh na Classe, adicione value = "/livros" junto com Method.
    // Permite mapear uma URI para determinado método.
    // Method vai informar que deseja utilizar o método GET (Adquirir informação).
    public ResponseEntity<List<Livro>> listar(){
        // Vamos fazer esse método listar alguns livros.
        return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());
    }
    @RequestMapping(method = RequestMethod.POST)
    // Caso não tiver o @RequestMappinh na Classe, adicione value = "/livros" junto com Method.
    // Method vai informar que deseja criar uma nova informação.
    public ResponseEntity<Void> salvar(@RequestBody Livro livro){
        // ResponseEntity para conseguir manipular a resposta, porém seu corpo é vazio. Sem retorno.
        // O RequestBody vai permitir que a informação seja salva no Banco de Dados
        livro = livrosService.salvar(livro);
        // o livro é salvo com todas informações salva na variavel livro.

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getId()).toUri();
        //Ele vai construir e expandir a URI usando a variavel livro.getId como base.

        return ResponseEntity.created(uri).build();
        // Utilizando o ResponseEntityu para criar a resposta correta.
        // O created vai informar status correto (201 Created)
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
        //O PathVariable permite usar a variavel indicado no value e colocar a informação na função buscar.

        Optional<Livro> livro = null;

        try {
            livro = livrosService.buscar(id);
        } catch (LivroNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
            //notFound indica que o id deseja não está sendo encontrando, retornando um erro 404.
        }

        return ResponseEntity.status(HttpStatus.OK).body(livro);
        // status(HttpStatus.OK) = Vai informar que é uma reposta de sucesso. 200 OK.
        // O Body vai adicionar o livro na resposta.
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id){
        try{
            livrosService.deletar(id);
            // Caso não encontre o ID, a exceção será lançada (catch)
        } catch (LivroNaoEncontradoException e) {
            // Caso não exista o id selecionado, vai ter o tratamento informando ao usuário que a informação não foi
            // encontrada informando erro 404.
            return ResponseEntity.notFound().build();
        }
        // Não há conteúdo nenhum para mostrar.
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    // Metódo PUT atualiza o recurso.
    public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id){
        livro.setId(id);
        try {
            livrosService.atualizar(livro);
            // O atualizar faz um MERGE entre as informações, atualizando a nova informação sobre a velha informação.
        } catch (LivroNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


}
