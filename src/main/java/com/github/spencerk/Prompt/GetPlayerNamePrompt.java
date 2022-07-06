package com.github.spencerk.Prompt;

import com.github.spencerk.models.Player;

public class GetPlayerNamePrompt implements Prompt {
    @Override
    public Prompt run() {
        char input;
        String name;

        do {
            System.out.print("Enter your character's name:");
            name = PromptFactory.getScanner().nextLine().trim();

            do {
                System.out.printf("%s. Are you sure that's your name?(y/n)", name);
                input = PromptFactory.getScanner().nextLine().trim().toLowerCase().charAt(0);
            } while(input != 'y' && input != 'n');
        } while(input != 'y');

        Player.getInstance().setName(name);
        return PromptFactory.getMapPrompt();
    }
}
