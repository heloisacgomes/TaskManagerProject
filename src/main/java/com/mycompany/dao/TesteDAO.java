/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author heloisagomes
 */
package com.mycompany.dao;

import com.mycompany.model.Task;

public class TesteDAO {

    public static void main(String[] args) {

        TaskDAO dao = new TaskDAO();

        Task task = new Task();

        task.setTitulo("Estudar JavaFX");
        task.setDescricao("Criar interface gráfica");
        task.setConcluida(false);
        
        dao.salvar(task);
    }
}
