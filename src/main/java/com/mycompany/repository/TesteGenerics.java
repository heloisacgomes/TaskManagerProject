package com.mycompany.repository;

import com.mycompany.model.Task;
import java.util.ArrayList;
import java.util.List;

public class TesteGenerics {

    public static void main(String[] args) {

        ListaUtil<Task> util = new ListaUtil<>();

        List<Task> tarefas = new ArrayList<>();

        Task task = new Task();

        task.setTitulo("Teste Generics");

        util.adicionar(tarefas, task);

        util.imprimir(tarefas);
    }
}
