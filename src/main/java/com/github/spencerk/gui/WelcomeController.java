package com.github.spencerk.gui;

public class WelcomeController {

    public void handleClick() {
        HvGWindow   window = new HvGWindow();

        window.changeScene("/fxml/SelectNameScene.fxml");
    }
}
