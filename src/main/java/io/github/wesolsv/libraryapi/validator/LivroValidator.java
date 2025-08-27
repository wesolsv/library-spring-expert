package io.github.wesolsv.libraryapi.validator;

import io.github.wesolsv.libraryapi.exceptions.CampoInvalidoException;
import io.github.wesolsv.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.wesolsv.libraryapi.model.Livro;
import io.github.wesolsv.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Livros publicados a partir de 2020, o campo 'preco' é obrigatório");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroByIsbn = repository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroByIsbn.isPresent();
        }

        return livroByIsbn
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
