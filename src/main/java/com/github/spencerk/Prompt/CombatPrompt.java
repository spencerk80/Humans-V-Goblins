package com.github.spencerk.Prompt;

import com.github.spencerk.models.Goblin;
import com.github.spencerk.models.Player;
import com.github.spencerk.util.Console;

import java.util.Random;

public class CombatPrompt implements Prompt {

    private final Random random;

    public CombatPrompt() {
        random = new Random();
    }
    @Override
    public Prompt run() {
        Goblin  enemy       = new Goblin(Player.getInstance());
        boolean showDesc    = true;

        do {
            Console.clearScreen();

            if(showDesc) {
                System.out.printf(
                        "%s came across a goblin! %s is carrying a spear and is poised to fight.\n",
                        Player.getInstance().getName(),
                        random.nextInt(2) == 0 ? "He" : "She"
                );

                showDesc = false;
            }

            printStats(enemy);

            switch(getPlayerAction()) {
                case 'a':
                    doCombatTurn(enemy);
                    break;
                case 'i':
                    PromptFactory.getInventoryPrompt().run();
                    break;
                case 128: //Used for unit testing. User cannot possibly enter this
                    return null;
            }
        } while(enemy.getHealth() > 0 && Player.getInstance().getHealth() > 0);

        //Check if it was the player who died
        if(Player.getInstance().getHealth() <= 0) return PromptFactory.getDefeatedPrompt();

        System.out.printf("%s has slain the goblin.\n", Player.getInstance().getName());
        awardExp(enemy);
        System.out.println("Press enter to continue");
        PromptFactory.getScanner().nextLine();

        return PromptFactory.getAcquireDropsPrompt();
    }

    private char getPlayerAction() {
        String  inputStr;
        char    input;

        do {
            System.out.println("A - attack. I - access inventory");
            inputStr = PromptFactory.getScanner().nextLine().trim().toLowerCase();
            input = "".equals(inputStr) ? 0 : inputStr.charAt(0); //Set char to null terminator if string empty

        } while(input != 'a' && input != 'i' && input != 128);

        return input;
    }

    private void printStats(Goblin enemy) {
        System.out.printf(
                "Player: %d/%d HP. Goblin: %d/%d HP.\n",
                Player.getInstance().getHealth(),
                Player.getInstance().getMaxHealth(),
                enemy.getHealth(),
                enemy.getMaxHealth()
        );
    }

    private void doCombatTurn(Goblin enemy) {
        boolean isCritHit = false;
        byte dmg;

        System.out.printf("%s slashes at the goblin.\n", Player.getInstance().getName());

        switch(random.nextInt(20)) {
            //Player's turn
            case 0: //player misses
                System.out.printf("%s misses...\n", Player.getInstance().getName());
                break;
            case 19: //Crit!
                System.out.printf("%s lands a critical hit!\n", Player.getInstance().getName());
                isCritHit = true;
                //fallthrough to default
            default:
                dmg = isCritHit ?
                        (byte) (Player.getInstance().getStrength() * 2
                                + random.nextInt(Player.getInstance().getStrength() / 5 < 2 ?
                                3 :
                                Player.getInstance().getStrength() / 5)) :
                        (byte) (Player.getInstance().getStrength()
                                + random.nextInt(Player.getInstance().getStrength() / 5 < 2 ?
                                3 :
                                Player.getInstance().getStrength() / 5));
                System.out.printf(
                        "%s deals %d damage to the goblin.\n",
                        Player.getInstance().getName(),
                        dmg
                );
                enemy.takeDamage(dmg);
                System.out.println("Press enter to continue\n");
                PromptFactory.getScanner().nextLine();

                //Check if enemy died. Dead goblins cannot attack
                if(enemy.getHealth() <= 0) return;

                //Enemy turn
                isCritHit = false;
                System.out.printf("The goblin takes a swing at %s\n", Player.getInstance().getName());

                switch(random.nextInt(20)) {
                    case 0: //miss
                        System.out.println("The goblin misses...");
                        break;
                    case 19: //Crit!
                        System.out.println("The goblin strikes a critical hit!");
                        isCritHit = true;
                        //fallthrough
                    default:
                        dmg = isCritHit ?
                                (byte) (enemy.getStrength() * 2
                                        + random.nextInt(enemy.getStrength() / 5 < 2 ?
                                        3 :
                                        enemy.getStrength() / 5)) :
                                (byte) (enemy.getStrength()
                                        + random.nextInt(enemy.getStrength() / 5 < 2 ?
                                        3 :
                                        enemy.getStrength() / 5));
                        System.out.printf(
                                "The goblin did %d damage to %s\n",
                                dmg,
                                Player.getInstance().getName()
                        );
                }
                Player.getInstance().takeDamage(dmg);
                System.out.println("Press enter to continue");
                PromptFactory.getScanner().nextLine();
        }
    }

    private void awardExp(Goblin enemy) {
        byte beforeXpLevel = Player.getInstance().getLevel();

        System.out.printf("The goblin awards %d xp to %s\n", enemy.getExpWorth(), Player.getInstance().getName());
        Player.getInstance().addExp(enemy.getExpWorth());

        if(Player.getInstance().getLevel() > beforeXpLevel) {
            System.out.printf("%s leveled up!\n", Player.getInstance().getName());
        }

        System.out.printf(
                "%s is at %d of %d xp to next level.\n",
                Player.getInstance().getName(),
                Player.getInstance().getCurrentExp(),
                Player.getInstance().getExpNeededToLevel()
        );
    }
}
