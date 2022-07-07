package com.github.spencerk.Prompt;

import com.github.spencerk.inventory.Inventory;
import com.github.spencerk.models.Player;
import com.github.spencerk.util.Console;

public class PlayAgainPrompt implements Prompt {
    @Override
    public Prompt run() {
        char    input;
        String  inputStr;

        Console.clearScreen();

        do {
            System.out.println("Would you like to play again?");
            inputStr = PromptFactory.getScanner().nextLine().trim().toLowerCase();
            input = "".equals(inputStr) ? 0 : inputStr.charAt(0); //Set char to null terminator if string empty
        } while(input != 'y' && input != 'n');

        if(input == 'y') {
            Inventory.getInstance().reset();
            Player.getInstance().reset();

            return PromptFactory.getWelcomePrompt();
        }
        return null; //End game
    }
}
