package com.github.spencerk.gui;

import com.github.spencerk.models.Goblin;
import com.github.spencerk.models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class CombatController {
    @FXML
    protected ProgressBar playerHpBar;
    @FXML
    protected ProgressBar enemyHpBar;
    @FXML
    protected Text playerHpText;
    @FXML
    protected Text enemyHpText;

    Player  player = Player.getInstance();
    Goblin  enemy;

    public void initialize() {
        enemy = new Goblin(player);

        updateBars();
    }

    private void updateBars() {
        playerHpBar.setProgress(player.getHealth() / player.getMaxHealth());
        playerHpText.setText(String.format("%d/%d", player.getHealth(), player.getMaxHealth()));

        enemyHpBar.setProgress(enemy.getHealth() / enemy.getMaxHealth());
        enemyHpText.setText(String.format("%d/%d", enemy.getHealth(), enemy.getMaxHealth()));
    }
}
