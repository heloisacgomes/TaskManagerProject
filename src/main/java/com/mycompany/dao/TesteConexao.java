/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {

        try {

            Connection conexao = Conexao.conectar();

            System.out.println("Conectado com sucesso!");

            conexao.close();

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());

        }
    }
}
