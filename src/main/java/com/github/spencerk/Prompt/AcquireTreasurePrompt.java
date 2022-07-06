package com.github.spencerk.Prompt;

import com.github.spencerk.exceptions.ItemCountExceededException;
import com.github.spencerk.inventory.Inventory;
import com.github.spencerk.models.Player;
import com.github.spencerk.items.Potion;

public class AcquireTreasurePrompt implements Prompt {
    @Override
    public Prompt run() {
        try{
            Inventory.getInstance().addItem(new Potion());
            System.out.printf("%s received a new potion\n", Player.getInstance().getName());
        } catch(ItemCountExceededException icee) {
            System.out.printf("%s has too many potions to take this one.\n", Player.getInstance().getName());
        }

        System.out.println("Press any key to continue");
        PromptFactory.getScanner().nextLine();

        return PromptFactory.getMapPrompt();
    }
}
