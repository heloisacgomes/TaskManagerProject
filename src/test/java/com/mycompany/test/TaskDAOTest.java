package com.mycompany.test;

import com.mycompany.dao.TaskDAO;
import com.mycompany.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskDAOTest {

    @Test
    void deveCriarObjetoTask() {

        Task task = new Task();

        task.setTitulo("Teste DAO");
        task.setDescricao("Descrição");

        assertEquals(
                "Teste DAO",
                task.getTitulo()
        );

        assertEquals(
                "Descrição",
                task.getDescricao()
        );
    }

    @Test
    void deveMarcarComoConcluida() {

        Task task = new Task();

        task.setConcluida(true);

        assertTrue(
                task.isConcluida()
        );
    }
}
