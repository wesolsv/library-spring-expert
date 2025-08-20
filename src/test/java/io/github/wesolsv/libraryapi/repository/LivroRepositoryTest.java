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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("106-133");
        livro.setPreco(BigDecimal.valueOf(697));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("DINOSSAUROS");
        livro.setDataPublicacao(LocalDate.of(2000, 1, 29));

        Autor autor = autorRepository.findById(UUID.fromString("bad433a7-57ea-4dcb-9615-ef5490ddb4ac"))
                .orElse(null);
        livro.setAutor(autor);
        repository.save(livro);
    }


    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("905-333");
        livro.setPreco(BigDecimal.valueOf(99));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("DRAGONS LIVE2");
        livro.setDataPublicacao(LocalDate.of(2000, 3, 1));

        Autor autor = new Autor("Jordan",  LocalDate.of(2000,4, 2), "Austriaco");
        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    void salvarAutorLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("432-112");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1960, 12, 25));

        Autor autor = new Autor("AmericanAutor",  LocalDate.of(1950,1, 1), "Americano");
        autorRepository.save(autor);
        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
        Optional<Autor> novoAutor = autorRepository.findById(UUID.fromString("fbb53e62-6f24-444f-b86e-fb6caa8ec3cd"));
        Optional<Livro> livro = repository.findById(UUID.fromString("42d07997-9e2c-4f05-9f97-d8e21b12a1af"));
        livro.get().setAutor(novoAutor.get());
        repository.save(livro.get());

    }

    @Test
    void deletar(){
        var id = UUID.fromString("42d07997-9e2c-4f05-9f97-d8e21b12a1af");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("78c84b55-b9c2-413a-96dc-5d0b26cdad16");
        Livro livro = repository.findById(id).orElse(null);

        System.out.println(livro.getTitulo() + " " + livro.getPreco());
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> livros = repository.findByTitulo("DRAGONS BAY2");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        List<Livro> livros = repository.findByIsbn("908-350");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest(){
        List<Livro> livros = repository.findByTituloAndPreco("DRAGONS BAY2", BigDecimal.valueOf(567));
        livros.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJpql(){
        var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresLivrosComQueryJpql(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarNomesDeLivrosDistintos(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBr(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarLivrosPorGeneroQueryParamTest(){
        var resultado = repository.findByGenero(GeneroLivro.FANTASIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarLivrosPorGeneroPositionalParamTest(){
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.FANTASIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGenero(){
        repository.deleteByGenero(GeneroLivro.FANTASIA);
    }

    @Test
    void updateDataPublicacao(){
        repository.updateDataPublicacao(LocalDate.of(2000, 1, 1));
    }

}