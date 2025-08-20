package io.github.wesolsv.libraryapi.repository;

import io.github.wesolsv.libraryapi.service.TransactionalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransactionalService transactionalService;

    /**
     * Commit -> confirmar atts
     * Rollback -> desfazer atts
     */
    @Test
    void transacaoSimples(){
        transactionalService.executar();
    }

    @Test
    void transacaoEstadoManaged(){
        transactionalService.atualizarSemAtualizar();
    }
}
