package com.github.spencerk.Prompt;

import com.github.spencerk.map.Map;
import com.github.spencerk.models.Player;
import com.github.spencerk.util.Console;

public class MapPrompt implements Prompt {
    @Override
    public Prompt run() {
        Console.clearScreen();

        System.out.println(Map.getInstance().toString());
        System.out.printf(
                "Legend:\nPlayer: %s\nEnemy: %s\nTree: %s\nTreasure Chest: %s\n\n",
                Map.PLAYER_SYMBOL,
                Map.ENEMY_SYMBOL,
                Map.TREE_SYMBOL,
                Map.CHEST_SYMBOL
        );
        System.out.printf(
                "%s current HP is %d/%d\n",
                Player.getInstance().getName(),
                Player.getInstance().getHealth(),
                Player.getInstance().getMaxHealth()
        );

        return PromptFactory.getMenuPrompt();
    }
}