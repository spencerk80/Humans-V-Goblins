package com.github.spencerk.gui;

import com.github.spencerk.Prompt.AcquireTreasurePrompt;
import com.github.spencerk.Prompt.CombatPrompt;
import com.github.spencerk.Prompt.Prompt;
import com.github.spencerk.enums.Direction;
import com.github.spencerk.enums.MapPoint;
import com.github.spencerk.exceptions.ItemCountExceededException;
import com.github.spencerk.exceptions.PathBlockedException;
import com.github.spencerk.inventory.Inventory;
import com.github.spencerk.items.Item;
import com.github.spencerk.items.Potion;
import com.github.spencerk.map.Map;
import com.github.spencerk.models.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
        Stage invtryWindow = new Stage();
        Parent root;

        try {
            root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/fxml/InventoryScene.fxml"))
            );
        } catch(IOException e) {
            System.err.println("Error: Could not load inventory window!");
            return;
        }

        invtryWindow.setScene(new Scene(root));
        invtryWindow.setTitle("Inventory");
        invtryWindow.initModality(Modality.WINDOW_MODAL);
        invtryWindow.initOwner(((Node) event.getSource()).getScene().getWindow());
        invtryWindow.show();
    }

    public void handleKeyStroke(KeyEvent event) {
        KeyCode pressedKey      = event.getCode();
        Prompt  returnedPrompt  = null;

        try {
            if(pressedKey.equals(KeyCode.UP) || pressedKey.equals(KeyCode.W)) {
                returnedPrompt = Map.getInstance().movePlayer(Direction.NORTH);
                displayMap();
            } else if(pressedKey.equals(KeyCode.RIGHT) || pressedKey.equals(KeyCode.D)) {
                returnedPrompt = Map.getInstance().movePlayer(Direction.EAST);
                displayMap();
            } else if(pressedKey.equals(KeyCode.DOWN) || pressedKey.equals(KeyCode.S)) {
                returnedPrompt = Map.getInstance().movePlayer(Direction.SOUTH);
                displayMap();
            } else if(pressedKey.equals(KeyCode.LEFT) || pressedKey.equals(KeyCode.A)) {
                returnedPrompt = Map.getInstance().movePlayer(Direction.WEST);
                displayMap();
            }
        } catch(PathBlockedException e) {/*For now don't do anything*/}

        if(returnedPrompt != null) loadNextWindow(returnedPrompt);
    }

    private void loadNextWindow(Prompt indicator) {
        if(indicator.getClass() == CombatPrompt.class) {
            HvGWindow window = new HvGWindow();

            window.changeScene("/fxml/BattleScene.fxml");
        } else if(indicator.getClass() == AcquireTreasurePrompt.class) {
            Alert alert;
            Item item = new Potion();

            try {
                Inventory.getInstance().addItem(item);
            } catch(ItemCountExceededException e) {
                alert = new Alert(Alert.AlertType.WARNING);

                //Show if item is left behind
                alert.setTitle("Item Left Behind");
                alert.setHeaderText(item.toString());
                alert.setContentText(String.format("You cannot hold anymore %s", item));
                alert.show();
            }
            //Show if item is taken
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Item Acquired");
            alert.setHeaderText(item.toString());
            alert.setContentText(String.format("You've acquired a %s", item));
            alert.show();
        }
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
