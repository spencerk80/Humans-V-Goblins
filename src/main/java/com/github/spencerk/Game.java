package com.github.spencerk;

import com.github.spencerk.Prompt.Prompt;
import com.github.spencerk.Prompt.PromptFactory;

public class Game {

    public static void main(String[] args) {
        Prompt prompt = PromptFactory.getWelcomePrompt();

        while(prompt != null) prompt = prompt.run();
    }

}
