package com.github.spencerk.Prompt;

import com.github.spencerk.models.Player;

public class DefeatedPrompt implements Prompt {
    @Override
    public Prompt run() {
        System.out.printf("%s has been defeated in combat! Game over...", Player.getInstance().getName());
        System.out.println("Press enter to continue");
        PromptFactory.getScanner().nextLine();
        return PromptFactory.getPlayAgainPrompt();
    }
}
