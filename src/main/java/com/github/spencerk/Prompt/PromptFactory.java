package com.github.spencerk.Prompt;

import java.util.Scanner;

public class PromptFactory {
    private static Scanner                  scanner;

    private static AcquireTreasurePrompt   acquireTreasurePrompt;
    private static CombatPrompt            combatPrompt;
    private static MapPrompt                mapPrompt;
    private static AcquireDropsPrompt       acquireDropsPrompt;
    private static InventoryPrompt          inventoryPrompt;
    private static MenuPrompt               menuPrompt;
    private static WelcomePrompt            welcomePrompt;
    private static DefeatedPrompt           defeatedPrompt;
    private static PlayAgainPrompt          playAgainPrompt;
    private static GetPlayerNamePrompt      getPlayerNamePrompt;

    public static Scanner getScanner() {
        if(scanner == null) scanner = new Scanner(System.in);
        return scanner;
    }

    public static AcquireTreasurePrompt getAcquireTreasurePrompt() {
        if(acquireTreasurePrompt == null) acquireTreasurePrompt = new AcquireTreasurePrompt();
        return acquireTreasurePrompt;
    }

    public static CombatPrompt getCombatPrompt() {
        if(combatPrompt == null) combatPrompt = new CombatPrompt();
        return combatPrompt;
    }

    public static MapPrompt getMapPrompt() {
        if(mapPrompt == null) mapPrompt = new MapPrompt();
        return mapPrompt;
    }

    public static AcquireDropsPrompt getAcquireDropsPrompt() {
        if(acquireDropsPrompt == null) acquireDropsPrompt = new AcquireDropsPrompt();
        return acquireDropsPrompt;
    }

    public static InventoryPrompt getInventoryPrompt() {
        if(inventoryPrompt == null) inventoryPrompt = new InventoryPrompt();
        return inventoryPrompt;
    }

    public static MenuPrompt getMenuPrompt() {
        if(menuPrompt == null) menuPrompt = new MenuPrompt();
        return menuPrompt;
    }

    public static WelcomePrompt getWelcomePrompt() {
        if(welcomePrompt == null) welcomePrompt = new WelcomePrompt();
        return welcomePrompt;
    }

    public static PlayAgainPrompt getPlayAgainPrompt() {
        if(playAgainPrompt == null) playAgainPrompt = new PlayAgainPrompt();
        return playAgainPrompt;
    }

    public static DefeatedPrompt getDefeatedPrompt() {
        if(defeatedPrompt == null) defeatedPrompt = new DefeatedPrompt();
        return defeatedPrompt;
    }

    public static GetPlayerNamePrompt getGetPlayerNamePrompt() {
        if(getPlayerNamePrompt == null) getPlayerNamePrompt = new GetPlayerNamePrompt();
        return getPlayerNamePrompt;
    }
}
