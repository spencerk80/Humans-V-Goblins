package com.github.spencerk.gui;

import com.github.spencerk.models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SelectNameController {

    @FXML
    protected TextField   nameField;

    public void setCharName() {
        HvGWindow   window      = new HvGWindow();
        String      playerName  = nameField.getText().trim();

        //Use default name if none provided
        Player.getInstance().setName(playerName.length() == 0 ? Player.getInstance().getName() : playerName);
        window.changeScene("/fxml/MapScene.fxml");
    }

    public void onEnter(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) setCharName();
    }

}
