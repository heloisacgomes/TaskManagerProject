package com.mycompany.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                                .getResource(
                                        "/TelaLogin.fxml"
                                )
                );

        Scene scene =
                new Scene(
                        loader.load()
                );

        stage.setTitle(
                "To do - O que faremos hoje?"
        );

        stage.setScene(
                scene
        );

        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}