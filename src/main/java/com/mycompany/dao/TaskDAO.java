/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public void salvar(Task task) {

    String sql =
            "INSERT INTO tarefas (titulo, descricao, concluida) VALUES (?, ?, ?)";

    Connection conn = null;

    try {

        conn = Conexao.conectar();

        conn.setAutoCommit(false);

        PreparedStatement stmt =
                conn.prepareStatement(sql);

        stmt.setString(1, task.getTitulo());
        stmt.setString(2, task.getDescricao());
        stmt.setBoolean(3, task.isConcluida());

        stmt.executeUpdate();

        conn.commit();

    } catch (Exception e) {

        try {

            if (conn != null) {
                conn.rollback();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        e.printStackTrace();

    } finally {

        try {

            if (conn != null) {
                conn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    public List<Task> listar() {

        List<Task> lista = new ArrayList<>();

        String sql = "SELECT * FROM tarefas";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

           while (rs.next()) {
               Task task = new Task();
               task.setId(rs.getInt("id"));
               task.setTitulo(rs.getString("titulo"));
               task.setDescricao(rs.getString("descricao"));
               task.setConcluida(rs.getBoolean("concluida"));
               
               lista.add(task);
}
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void excluir(int id) {

        String sql = "DELETE FROM tarefas WHERE id = ?";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void concluir(int id) {

        String sql =
                "UPDATE tarefas SET concluida = true WHERE id = ?";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
public void atualizar(Task task) {

    String sql =
        "UPDATE tarefas SET titulo=?, descricao=?, concluida=? WHERE id=?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, task.getTitulo());
        stmt.setString(2, task.getDescricao());
        stmt.setBoolean(3, task.isConcluida());
        stmt.setInt(4, task.getId());

        stmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
        


