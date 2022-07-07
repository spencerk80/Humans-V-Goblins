package com.github.spencerk.Prompt;

import com.github.spencerk.enums.Direction;
import com.github.spencerk.exceptions.PathBlockedException;
import com.github.spencerk.map.Map;
import com.github.spencerk.models.Player;

public class MenuPrompt implements Prompt {
    @Override
    public Prompt run() {
        char input;
        String inputStr;

        //Loops if player runs into a tree, otherwise, a return statement will be hit
        while(true) {
            do {
                System.out.print("Enter a command. N - go north. S - go south. E - go east. W - go west. ");
                System.out.println("I - open inventory");

                inputStr = PromptFactory.getScanner().nextLine().trim().toLowerCase();
                input = "".equals(inputStr) ? 0 : inputStr.charAt(0); //Set char to null terminator if string empty
            } while (input != 'n' && input != 's' && input != 'w' && input != 'e' && input != 'i');

            try {
                switch (input) {
                    case 'n':
                        return Map.getInstance().movePlayer(Direction.NORTH);
                    case 's':
                        return Map.getInstance().movePlayer(Direction.SOUTH);
                    case 'e':
                        return Map.getInstance().movePlayer(Direction.EAST);
                    case 'w':
                        return Map.getInstance().movePlayer(Direction.WEST);
                    case 'i':
                        return PromptFactory.getInventoryPrompt();
                }
            } catch (PathBlockedException pbe) {
                System.out.printf(
                        "%s runs into a tree and stubs their toe. They curse at the tree.\n\n",
                        Player.getInstance().getName()
                );
            }
        }
    }
}
