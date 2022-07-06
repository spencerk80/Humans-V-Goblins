package com.github.spencerk.Prompt;

import com.github.spencerk.inventory.Inventory;
import com.github.spencerk.models.Player;
import com.github.spencerk.util.Console;

public class PlayAgainPrompt implements Prompt {
    @Override
    public Prompt run() {
        char input;

        Console.clearScreen();

        do {
            System.out.println("Would you like to play again?");
            input = PromptFactory.getScanner().nextLine().trim().toLowerCase().charAt(0);
        } while(input != 'y' && input != 'n');

        if(input == 'y') {
            Inventory.getInstance().reset();
            Player.getInstance().reset();

            return PromptFactory.getWelcomePrompt();
        }
        return null; //End game
    }
}
