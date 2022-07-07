package com.github.spencerk.Prompt;

import com.github.spencerk.models.Player;

public class GetPlayerNamePrompt implements Prompt {
    @Override
    public Prompt run() {
        char input;
        String name, inputStr;

        do {
            do {
                System.out.print("Enter your character's name: ");
                name = PromptFactory.getScanner().nextLine().trim();
                System.out.println();
            } while("".equals(name));

            do {
                System.out.printf("%s. Are you sure that's your name?(y/n): ", name);
                inputStr = PromptFactory.getScanner().nextLine().trim().toLowerCase();
                input = "".equals(inputStr) ? 0 : inputStr.charAt(0); //Set char to null terminator if string empty
                System.out.println();
            } while(input != 'y' && input != 'n');
        } while(input != 'y');

        Player.getInstance().setName(name);
        return PromptFactory.getMapPrompt();
    }
}
