package io.github.wesolsv.libraryapi.service;

import io.github.wesolsv.libraryapi.model.GeneroLivro;
import io.github.wesolsv.libraryapi.model.Livro;
import io.github.wesolsv.libraryapi.repository.LivroRepository;
import io.github.wesolsv.libraryapi.repository.specs.LivroSpecs;
import io.github.wesolsv.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.wesolsv.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    private final LivroValidator validator;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Livro livro){
        repository.delete(livro);
    }

    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer anoPublicacao,
                                Integer pagina,
                                Integer tamanhoPagina){

//            Specification<Livro> specs = Specification
//                    .where(LivroSpecs.isbnEqual(isbn))
//                    .and(LivroSpecs.tituloLike(titulo))
//                    .and(LivroSpecs.nomeAutorEqual(nomeAutor))
//                    .and(LivroSpecs.generoEqual(genero));

        Specification<Livro> specs = Specification
                .where(((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()));

        if(isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        if(genero != null){
            specs = specs.and(generoEqual(genero));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return repository.findAll(specs, pageRequest);
    }

    public void atualizar( Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("Entidade sem id presente");
        }

        validator.validar(livro);
        repository.save(livro);
    }
}
