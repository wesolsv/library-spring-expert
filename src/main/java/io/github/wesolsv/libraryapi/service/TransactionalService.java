package io.github.wesolsv.libraryapi.service;

import io.github.wesolsv.libraryapi.model.Autor;
import io.github.wesolsv.libraryapi.model.GeneroLivro;
import io.github.wesolsv.libraryapi.model.Livro;
import io.github.wesolsv.libraryapi.repository.AutorRepository;
import io.github.wesolsv.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionalService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;


    @Transactional
    public void atualizarSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("caea66ad-c6c2-4e4f-8b4e-01b26f90e043"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));
    }

    @Transactional
    public void executar(){

        Autor autor = new Autor("AmericanAutor",  LocalDate.of(1950,1, 1), "Americano");
        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("433-113");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("American Book");
        livro.setDataPublicacao(LocalDate.of(1960, 12, 25));

        livro.setAutor(autor);
        livroRepository.save(livro);

        if(autor.getNome().equals("AmericanAutor")){
            throw new RuntimeException("Rollback");
        }
    }
}
