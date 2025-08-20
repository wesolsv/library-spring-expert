package io.github.wesolsv.libraryapi.repository;

import io.github.wesolsv.libraryapi.model.Autor;
import io.github.wesolsv.libraryapi.model.GeneroLivro;
import io.github.wesolsv.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTeste(){
        Autor autor = new Autor("Wesley",  LocalDate.of(1994,2, 23), "Brasileiro");
        Autor autorSave = repository.save(autor);
        System.out.println(autorSave.getNome());
    }

    @Test
    public void salvarAutorComLivrosTeste(){
        Autor autor = new Autor("WesleyTeste2",  LocalDate.of(1994,2, 23), "Suiço");
        Livro livro = new Livro();
        livro.setIsbn("908-350");
        livro.setPreco(BigDecimal.valueOf(567));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("DRAGONS BAY");
        livro.setDataPublicacao(LocalDate.of(2015, 3, 1));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("908-351");
        livro2.setPreco(BigDecimal.valueOf(567));
        livro2.setGenero(GeneroLivro.FICCAO);
        livro2.setTitulo("DRAGONS BAY2");
        livro2.setDataPublicacao(LocalDate.of(2015, 3, 1));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
        System.out.println(autor.getNome());
    }

    @Test
    void listarLivrosAutor(){
        Autor autor = repository.findById(UUID.fromString("f408ab59-0ddc-4c00-a117-1e6d6677087f")).orElse(null);
        List<Livro> livros = livroRepository.findByAutor(autor);
        autor.setLivros(livros);
        autor.getLivros().forEach(System.out::println);
    }


    @Test
    public void atualizarTeste(){
        Optional<Autor> registroAntigo = repository.findById(UUID.fromString("1a092295-1446-42f9-a980-1da933040c10"));
        if(registroAntigo.isPresent()){
            System.out.println("Dados Autor");
            System.out.println(registroAntigo.get());

            Autor autorEncontrado = registroAntigo.get();
            autorEncontrado.setDataNascimento(LocalDate.of(1995,2, 23));
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("4ecd77e6-f9a4-4159-8a94-47efae7d8353");
        repository.deleteById(id);
    }

    @Test
    public void deleteByObjeto(){
        Optional<Autor> registroAntigo = repository.findById(UUID.fromString("1a092295-1446-42f9-a980-1da933040c10"));
        repository.delete(registroAntigo.get());
    }
}
