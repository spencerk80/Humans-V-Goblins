package com.github.spencerk.Prompt;

public class WelcomePrompt implements Prompt {
    @Override
    public Prompt run() {
        System.out.println("Welcome to Humans V Goblins\n");
        System.out.print("Your goal is to run around the map and slaying the goblins. Just enter the direction you ");
        System.out.print("wish to go to move about. Running into chests gives you an item. Running into an enemy ");
        System.out.print("starts combat. If you try to run into a tree, well you cannot occupy the same spot as a ");
        System.out.print("tree, so you'll remain where you are. You have an inventory of items you can use, for ");
        System.out.println("example, a potion to heal.\n");
        System.out.print("If you move off the edge of the map, you'll move to the next square. The lands are ");
        System.out.print("confusing to navigate, so if you try to immediately move back, you'll find yourself ");
        System.out.println("somewhere else.\n");
        System.out.println("Press enter to start the game");

        //Wait for user to press enter
        PromptFactory.getScanner().nextLine();

        return PromptFactory.getGetPlayerNamePrompt();
    }
}
