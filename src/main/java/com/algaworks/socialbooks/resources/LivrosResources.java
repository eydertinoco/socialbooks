package com.algaworks.socialbooks.resources;

import com.algaworks.socialbooks.domain.Comentario;
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
    public ResponseEntity<Livro> buscar(@PathVariable("id") Long id) {
        //O PathVariable permite usar a variavel indicado no value e colocar a informação na função buscar.

        return ResponseEntity.ok(livrosService.buscar(id));

//        Optional<Livro> livro = livrosService.buscar(id);;

//        return ResponseEntity.status(HttpStatus.OK).body(livrosService.buscar(id));
        // status(HttpStatus.OK) = Vai informar que é uma reposta de sucesso. 200 OK.
        // O Body vai adicionar o livro na resposta.
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id){
        livrosService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    // Metódo PUT atualiza o recurso.
    public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id){
        livro.setId(id);
        livrosService.atualizar(livro);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
    public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long livroId, @RequestBody Comentario comentario){
        livrosService.salvarComentario(livroId, comentario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
    public ResponseEntity<List<Comentario>> listarComentartios
            (@PathVariable("id") Long livroId) {

        List<Comentario> comentarios = livrosService.listarComentarios(livroId);

        return ResponseEntity.status(HttpStatus.OK).body(comentarios);
    }

}
