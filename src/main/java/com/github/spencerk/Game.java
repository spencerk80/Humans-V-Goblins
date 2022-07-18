package com.github.spencerk;

import com.github.spencerk.Prompt.Prompt;
import com.github.spencerk.Prompt.PromptFactory;
import com.github.spencerk.gui.HvGWindow;

public class Game {

    public static void main(String[] args) {

        if(args.length > 0 && args[0].equalsIgnoreCase("--cli"))
            runPrompts();
        else
            runGui(args);
    }

    private static void runPrompts() {
        Prompt prompt = PromptFactory.getWelcomePrompt();

        while(prompt != null) prompt = prompt.run();
    }

    private static void runGui(String[] args) {
        HvGWindow application = new HvGWindow();

        application.run(args);
    }

}
