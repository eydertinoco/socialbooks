package com.algaworks.socialbooks.resources;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.LivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Void> salvar(@RequestBody Livro livro){
        // ResponseEntity para conseguir manipular a resposta, porém seu corpo é vazio. Sem retorno.
        // O RequestBody vai permitir que a informação seja salva no Banco de Dados
        livro = livrosRepository.save(livro);
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

        Optional<Livro> livro =livrosRepository.findById(id);

        if (livro.equals(Optional.empty())){
            return ResponseEntity.notFound().build();
            //notFound indica que o id deseja não está sendo encontrando, retornando um erro 404.
        }
        return ResponseEntity.status(HttpStatus.OK).body(livro);
        // status(HttpStatus.OK) = Vai informar que é uma reposta de sucesso. 200 OK.
        // O Body vai adicionar o livro na resposta.
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    // Metódo DELETE remove o recurso.
    public void deletar(@PathVariable("id") Long id){
        livrosRepository.deleteById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    // Metódo PUT atualiza o recurso.
    public void atualizar(@RequestBody Livro livro, @PathVariable("id") Long id){
        livro.setId(id);
        livrosRepository.save(livro);
        // O save faz um MERGE entre as informações, atualizando a nova informação sobre a velha informação.
    }


}
