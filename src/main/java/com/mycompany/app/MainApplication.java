/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/TelaListagem.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("To Do - O que faremos hoje?");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
    launch();
}
}