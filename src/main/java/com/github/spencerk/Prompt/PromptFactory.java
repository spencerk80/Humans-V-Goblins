package com.github.spencerk.Prompt;

public class PromptFactory {
    private static AcquireTreasurePrompt   acquireTreasurePrompt;
    private static CombatPrompt            combatPrompt;
    private static NavigateMapPrompt       navigateMapPrompt;

    public static AcquireTreasurePrompt getAcquireTreasurePrompt() {
        if(acquireTreasurePrompt == null) acquireTreasurePrompt = new AcquireTreasurePrompt();
        return acquireTreasurePrompt;
    }

    public static CombatPrompt getCombatPrompt() {
        if(combatPrompt == null) combatPrompt = new CombatPrompt();
        return combatPrompt;
    }

    public static NavigateMapPrompt getNavigateMapPrompt() {
        if(navigateMapPrompt == null) navigateMapPrompt = new NavigateMapPrompt();
        return navigateMapPrompt;
    }
}
