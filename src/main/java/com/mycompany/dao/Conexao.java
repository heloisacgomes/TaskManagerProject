package com.mycompany.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    public static Connection conectar() throws SQLException {

        try {

            Properties props = new Properties();

            props.load(
                    Conexao.class.getResourceAsStream(
                            "/config.properties"
                    )
            );

            String url =
                    props.getProperty("db.url");

            String usuario =
                    props.getProperty("db.user");

            String senha =
                    props.getProperty("db.password");

            return DriverManager.getConnection(
                    url,
                    usuario,
                    senha
            );

        } catch (Exception e) {

            throw new SQLException(
                    "Erro ao carregar configuração do banco",
                    e
            );
        }
    }
}