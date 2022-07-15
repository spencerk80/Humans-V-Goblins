package com.github.spencerk.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class welcomeController {
    @FXML
    private Button continueBtn;

    public void handleClick(ActionEvent event) {
        HvGWindow   window = new HvGWindow();

        window.changeScene("/fxml/mapScene.fxml");
    }
}
