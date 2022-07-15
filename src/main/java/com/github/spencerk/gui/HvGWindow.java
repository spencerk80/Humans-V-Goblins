package com.github.spencerk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static java.lang.System.exit;

public class HvGWindow extends Application {
    private static Stage       window;


    public void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryWindow) throws Exception {
        window = primaryWindow;
        window.setTitle("Humans Vs Goblins");
        window.setResizable(false);
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/WelcomeScene.fxml"))));
        window.setWidth(600); //Required to not break the layout
        window.setHeight(480); //Required to not break the layout
        window.show();
    }

    public void changeScene(String fxml) {
        try {
            window.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml))));
        } catch(IOException e) {
            System.err.println(e.getMessage());
            exit(1);
        }
    }

}
