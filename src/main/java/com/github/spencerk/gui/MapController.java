package com.github.spencerk.gui;

import com.github.spencerk.enums.Direction;
import com.github.spencerk.enums.MapPoint;
import com.github.spencerk.exceptions.PathBlockedException;
import com.github.spencerk.map.Map;
import com.github.spencerk.models.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;

import java.util.List;
import java.util.stream.Collectors;

public class MapController {

    @FXML
    protected FlowPane    imageContainer;
    @FXML
    protected Label         playerNameLbl;

    @FXML
    public void initialize() {
        String playerName   = Player.getInstance().getName();

        //Automatic formatting
        playerName = playerName.charAt(playerName.length() - 1) == ':' ? playerName : playerName + ':';

        playerNameLbl.setText(playerName);
        displayMap();
    }

    public void openInventory(ActionEvent event) {

    }

    public void handleKeyStroke(KeyEvent event) {

        KeyCode pressedKey = event.getCode();

        try {
            if(pressedKey.equals(KeyCode.UP) || pressedKey.equals(KeyCode.W)) {
                Map.getInstance().movePlayer(Direction.NORTH);
                displayMap();
            } else if(pressedKey.equals(KeyCode.RIGHT) || pressedKey.equals(KeyCode.D)) {
                Map.getInstance().movePlayer(Direction.EAST);
                displayMap();
            } else if(pressedKey.equals(KeyCode.DOWN) || pressedKey.equals(KeyCode.S)) {
                Map.getInstance().movePlayer(Direction.SOUTH);
                displayMap();
            } else if(pressedKey.equals(KeyCode.LEFT) || pressedKey.equals(KeyCode.A)) {
                Map.getInstance().movePlayer(Direction.WEST);
                displayMap();
            }
        } catch(PathBlockedException e) {/*For now don't do anything*/}

    }

    private void displayMap() {

        MapPoint[][] map = Map.getInstance().getMap();
        List<ImageView> imageViews = imageContainer.getChildren()
                                                   .stream()
                                                   .map(node -> (ImageView) node)
                                                   .collect(Collectors.toList());

        for(byte row = 0; row < Map.MAP_DIM_VERTICAL; row++)
            for(byte col = 0; col < Map.MAP_DIM_HORIZONTAL; col++) {
                Image   img     = null;

                switch(map[row][col]) {
                    case LAND:
                        img = new Image("/img/land.png");
                        break;
                    case ENEMY:
                        img = new Image("/img/enemy.png");
                        break;
                    case PLAYER:
                        img = new Image("/img/player.png");
                        break;
                    case TREE:
                        img = new Image("/img/tree.png");
                        break;
                    case CHEST:
                        img = new Image("/img/chest.png");
                        break;
                }

                imageViews.get(Map.MAP_DIM_HORIZONTAL * row + col).setImage(img);
            }
    }

}
