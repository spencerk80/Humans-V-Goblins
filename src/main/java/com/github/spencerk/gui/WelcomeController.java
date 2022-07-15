package com.github.spencerk.gui;

import javafx.event.ActionEvent;

public class WelcomeController {

    public void handleClick() {
        HvGWindow   window = new HvGWindow();

        window.changeScene("/fxml/SelectNameScene.fxml");
    }
}
