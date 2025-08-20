package io.github.wesolsv.libraryapi.controller;

import io.github.wesolsv.libraryapi.controller.dto.AutorDTO;
import io.github.wesolsv.libraryapi.controller.dto.AutorResponseDTO;
import io.github.wesolsv.libraryapi.controller.dto.ErroResposta;
import io.github.wesolsv.libraryapi.exceptions.OperacaonaoPermitidaException;
import io.github.wesolsv.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.wesolsv.libraryapi.model.Autor;
import io.github.wesolsv.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autorDTO){
        try {
            var autorEntidade = autorDTO.mapearParaAutor();
            service.salvar(autorEntidade);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(uri).build();
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorResponseDTO> buscar(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorResponseDTO dto = new AutorResponseDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());

            return ResponseEntity.noContent().build();
        } catch (OperacaonaoPermitidaException e){
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.Status()).body(erroResposta);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){

        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorResponseDTO> lista = resultado
                .stream()
                .map(autor ->
                        new AutorResponseDTO(
                                autor.getId(),
                                autor.getNome(),
                                autor.getDataNascimento(),
                                autor.getNacionalidade()))
                    .toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if(autorOptional.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();

            autor.setNome(autorDTO.nome());
            autor.setNacionalidade(autorDTO.nacionalidade());
            autor.setDataNascimento(autorDTO.dataNascimento());

            service.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
        }
    }

}
