package com.mycompany.controller;

import com.mycompany.api.AuthApiService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaLoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Label lblMensagem;

    private final AuthApiService authApiService =
            new AuthApiService();

    @FXML
    public void entrar() {

        String email =
                txtEmail.getText();

        String senha =
                txtSenha.getText();

        if (email == null
                || email.isBlank()
                || senha == null
                || senha.isBlank()) {

            lblMensagem.setText(
                    "Informe o e-mail e a senha."
            );

            return;
        }

        try {

            authApiService.login(
                    email.trim(),
                    senha
            );

            abrirTelaListagem();

        } catch (Exception e) {

            String mensagem =
                    e.getMessage();

            if (mensagem == null
                    || mensagem.isBlank()) {

                mensagem =
                        "Não foi possível realizar o login.";
            }

            lblMensagem.setText(
                    mensagem
            );
        }
    }

    private void abrirTelaListagem()
            throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                                .getResource(
                                        "/TelaListagem.fxml"
                                )
                );

        Stage stage =
                (Stage) txtEmail
                        .getScene()
                        .getWindow();

        stage.setScene(
                new Scene(loader.load())
        );

        stage.setTitle(
                "TO DO - O que faremos hoje?"
        );

        stage.show();
    }
}