package com.github.spencerk.Prompt;

import com.github.spencerk.exceptions.ItemCountExceededException;
import com.github.spencerk.inventory.Inventory;
import com.github.spencerk.models.Player;
import com.github.spencerk.items.Potion;

import java.util.Random;

public class AcquireDropsPrompt implements Prompt {

    private final Random random;

    public AcquireDropsPrompt() {
        random = new Random();
    }
    @Override
    public Prompt run() {
        //5% chance to obtain a potion from enemy
        if(random.nextInt(20) == 0) {
            try{
                Inventory.getInstance().addItem(new Potion());
                System.out.printf("%s acquired a potion from the goblin!\n", Player.getInstance().getName());
            } catch(ItemCountExceededException icee) {
                System.out.printf(
                        "%s has too many potions and cannot take the one found on the goblin.\n",
                        Player.getInstance().getName()
                );
            }
        }
        System.out.println("Press enter to continue.");
        PromptFactory.getScanner().nextLine();

        return PromptFactory.getMapPrompt();
    }
}
