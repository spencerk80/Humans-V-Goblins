package com.github.spencerk.prompt;

import com.github.spencerk.Prompt.PromptFactory;
import com.github.spencerk.inventory.Inventory;
import com.github.spencerk.models.Player;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventoryPromptTest {
    InputStream OG_IN;
    OutputStream OG_OUT;
    ByteArrayOutputStream TEST_OUT    = new ByteArrayOutputStream();

    //Change all input and output streams to test ones for IO testing
    @BeforeAll
    public void setUpTestIO() {
        OG_IN = System.in;
        OG_OUT = System.out;

        System.setOut(new PrintStream(TEST_OUT));
    }

    @BeforeEach
    public void clearStreams() {
        TEST_OUT.reset();
        Inventory.getInstance().reset();
        Player.getInstance().reset();
    }

    //Restore the streams to work on the CLI again
    @AfterAll
    public void restoreIO() {
        System.setIn(OG_IN);
        System.setOut(new PrintStream(OG_OUT));
    }

    private void setInput(String input) {
        Field scannerField;

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            scannerField = PromptFactory.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
            scannerField.set(null, new Scanner(System.in));
        } catch(IllegalAccessException | NoSuchFieldException e) {
            System.err.println("Error: Tests could not set scanner to use test input stream!");
        }
    }

    private void injurePlayer() {
        Field healthField;

        try {
            healthField = Player.class.getDeclaredField("health");
            healthField.setAccessible(true);
            healthField.set(Player.getInstance(), (short) 8);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            System.err.println("Error: Could not injure player in testing!");
        }
    }

    @Test
    public void enterNothing() {
        setInput("\nexit\n");
        PromptFactory.getInventoryPrompt().run();

        //Player will be prompted again
        assertEquals(
                "Type item name to use it. Type exit to leave inventory.",
                TEST_OUT.toString().split("\n")[2]
        );
    }

    @Test
    public void enterInvalidItem() {
        setInput("Master Sword\nexit\n");
        PromptFactory.getInventoryPrompt().run();

        //Player will be prompted again
        assertEquals(
                "Type item name to use it. Type exit to leave inventory.",
                TEST_OUT.toString().split("\n")[2]
        );
    }

    @Test void enterValidItem() {
        setInput("PoTiOn\nexit\n"); //weird casing to test input case indifference
        PromptFactory.getInventoryPrompt().run();

        //Player isn't injured. Potion shouldn't allow usage.
        assertEquals("Tivian is at full health already!", TEST_OUT.toString().split("\n")[2]);

        //Injure player
        injurePlayer();

        //Check that player is hurt
        assertEquals(8, Player.getInstance().getHealth());

        //Reset output
        TEST_OUT.reset();

        setInput("PoTiOn\nexit\n"); //weird casing to test input case indifference
        PromptFactory.getInventoryPrompt().run();

        //Player heals
        assertEquals("Tivian is at 10/10 HP", TEST_OUT.toString().split("\n")[2]);
    }
}
