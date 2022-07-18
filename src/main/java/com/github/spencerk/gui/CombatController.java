package com.github.spencerk.gui;

import com.github.spencerk.exceptions.ItemCountExceededException;
import com.github.spencerk.inventory.Inventory;
import com.github.spencerk.items.Item;
import com.github.spencerk.items.Potion;
import com.github.spencerk.models.Goblin;
import com.github.spencerk.models.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class CombatController {
    @FXML
    protected ProgressBar playerHpBar;
    @FXML
    protected ProgressBar enemyHpBar;
    @FXML
    protected Text playerHpText;
    @FXML
    protected Text enemyHpText;
    @FXML
    protected Label battleMsg;
    @FXML
    protected Button attackBtn;
    @FXML
    protected Button inventoryBtn;

    Player  player = Player.getInstance();
    Goblin  enemy;
    Random  random = new Random();

    public void initialize() {
        enemy = new Goblin(player);

        updateBars();
    }

    public void attack() {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> doPlayerTurn()),
                new KeyFrame(Duration.seconds(1.5), e -> doEnemyTurn()),
                new KeyFrame(Duration.seconds(3), e -> endOfCombatCheck()),
                new KeyFrame(Duration.seconds(3.1), e -> enableBtns())
        );

        disableBtns();
        timeline.play();
    }

    public void inventory(ActionEvent event) {
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

    private void updateBars() {
        playerHpBar.setProgress((float) player.getHealth() / player.getMaxHealth());
        playerHpText.setText(String.format("%d/%d", player.getHealth(), player.getMaxHealth()));

        enemyHpBar.setProgress((float) enemy.getHealth() / enemy.getMaxHealth());
        enemyHpText.setText(String.format("%d/%d", enemy.getHealth(), enemy.getMaxHealth()));
    }

    private void disableBtns() {
        attackBtn.setDisable(true);
        inventoryBtn.setDisable(true);
    }

    private void enableBtns() {
        attackBtn.setDisable(false);
        inventoryBtn.setDisable(false);
        battleMsg.setText("Fight!");
    }

    private void doPlayerTurn() {
        boolean isCritHit   = false;
        byte    dmg         = 0;

        battleMsg.setText("Attacking");
        switch(random.nextInt(20)) {
            case 0: //player misses
                battleMsg.setText("Miss");
                break;
            case 19: //Crit!
                isCritHit = true;
                battleMsg.setText("Crit!");
                //fallthrough to default
            default:
                dmg = isCritHit ?
                        (byte) (Player.getInstance().getStrength() * 2
                                + random.nextInt(Player.getInstance().getStrength() / 5 < 2 ?
                                3 :
                                Player.getInstance().getStrength() / 5)) :
                        (byte) (Player.getInstance().getStrength()
                                + random.nextInt(Player.getInstance().getStrength() / 5 < 2 ?
                                3 :
                                Player.getInstance().getStrength() / 5));
        }

        enemy.takeDamage(dmg);
        updateBars();
    }

    private void doEnemyTurn() {
        boolean isCritHit   = false;
        byte    dmg         = 0;

        //Dead goblin can't do anything
        if(enemy.getHealth() == 0) return;

        battleMsg.setText("Defending");
        switch(random.nextInt(20)) {
            case 0: //miss
                battleMsg.setText("Miss");
                break;
            case 19: //Crit!
                isCritHit = true;
                battleMsg.setText("Crit!");
                //fallthrough
            default:
                dmg = isCritHit ?
                        (byte) (enemy.getStrength() * 2
                                + random.nextInt(enemy.getStrength() / 5 < 2 ?
                                3 :
                                enemy.getStrength() / 5)) :
                        (byte) (enemy.getStrength()
                                + random.nextInt(enemy.getStrength() / 5 < 2 ?
                                3 :
                                enemy.getStrength() / 5));
        }

        Player.getInstance().takeDamage(dmg);
        updateBars();
    }

    private void endOfCombatCheck() {
        //If player dead, prompt and return to welcome
        if(player.getHealth() == 0) {
            Alert       alert   = new Alert(Alert.AlertType.WARNING);
            HvGWindow   window  = new HvGWindow();
            alert.setTitle("Uh oh!");
            alert.setHeaderText("X.X");
            alert.setContentText(String.format("%s has died! Better luck next time...", player.getName()));
            alert.show();

            window.changeScene("/fxml/WelcomeScene.fxml");
            return;
        }

        //If enemy is dead, prompt and award possible drop
        if(enemy.getHealth() == 0) {
            HvGWindow window = new HvGWindow();

            if(random.nextInt(20) == 0) {
                Item item = new Potion();
                Alert alert;

                try {
                    Inventory.getInstance().addItem(item);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Drop Acquired");
                    alert.setHeaderText(item.toString());
                    alert.setContentText(String.format("%s found an item!", player.getName()));
                    alert.show();
                } catch(ItemCountExceededException e) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Item Left Behind");
                    alert.setHeaderText(item.toString());
                    alert.setContentText(String.format(
                            "%s is carrying too many %s",
                            player.getName(), item
                    ));
                    alert.show();
                }
            }
            window.changeScene("/fxml/MapScene.fxml");
        }
    }
}
