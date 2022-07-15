package com.github.spencerk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class HvGWindow extends Application {
    private static Stage       window;


    public void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;
        window.setTitle("Humans Vs Goblins");
        window.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("/fxml/welcomeScene.fxml")),
                600,
                450)
        );
        window.setMinWidth(600); //Required to not break the layout
        window.setMinHeight(450); //Required to not break the layout
        window.show();
    }

    public void changeScene(String fxml) {
        try {
            window.getScene().setRoot(FXMLLoader.load(getClass().getResource(fxml)));
        } catch(IOException e) {
            System.err.println("Error: Could not find scene FXML!");
            exit(1);
        }
    }

}
