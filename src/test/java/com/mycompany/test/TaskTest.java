package com.mycompany.test;

import com.mycompany.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {

        task = new Task();
    }

    @AfterEach
    void tearDown() {

        task = null;
    }

    @Test
    void deveCriarTarefa() {

        task.setTitulo("Atividade IHC");

        assertEquals(
                "Atividade IHC",
                task.getTitulo()
        );
    }

    @Test
    void deveConcluirTarefa() {

        task.setConcluida(true);

        assertTrue(
                task.isConcluida()
        );
    }

    @Test
    void naoDeveAceitarTituloNulo() {

        task.setTitulo(null);

        assertNull(
                task.getTitulo()
        );
    }
}