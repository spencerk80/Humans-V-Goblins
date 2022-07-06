package com.github.spencerk.Prompt;

import com.github.spencerk.inventory.Inventory;
import com.github.spencerk.items.Item;
import com.github.spencerk.models.Player;

import java.util.List;

public class InventoryPrompt implements Prompt {
    @Override
    public Prompt run() {
        List<String>    inventoryContents = Inventory.getInstance().getItemList();
        String          playerChoice;
        Item            item;

        if(inventoryContents.isEmpty()) {
            System.out.printf("%s has no items.\n", Player.getInstance().getName());
            System.out.println("Press enter to continue");
            PromptFactory.getScanner().nextLine();
            return PromptFactory.getMapPrompt();
        }

        System.out.printf("%s's inventory has: ", Player.getInstance().getName());
        inventoryContents.forEach(entry -> System.out.printf(
                "%d %s. ",
                Inventory.getInstance().getItemQuantity(entry),
                entry
        ));
        System.out.println();

        playerChoice = getPlayerChoice(inventoryContents);
        if("exit".equals(playerChoice)) return PromptFactory.getMapPrompt();

        item = Inventory.getInstance().getItem(playerChoice);
        item.use();

        return PromptFactory.getMapPrompt();
    }

    private String getPlayerChoice(List<String> inventoryContents) {
        String input;

        do {
            System.out.println("Type item name to use it. Type exit to leave inventory.");
            input = PromptFactory.getScanner().nextLine().trim().toLowerCase();
        } while( ! inventoryContents.contains(input) && ! "exit".equals(input));

        return input;
    }
}
