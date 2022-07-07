package com.github.spencerk.prompt;

import com.github.spencerk.Prompt.PromptFactory;
import com.github.spencerk.models.Player;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CombatPromptTest {

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

    @Test
    public void enterNothing() {
        setInput(String.format("\n%c\n", (char) 128));
        PromptFactory.getCombatPrompt().run();

        //Prompt should just ask for the input again on invalid input
        assertEquals("A - attack. I - access inventory", TEST_OUT.toString().split("\n")[3]);
    }

    @Test
    public void enterInvalidChar() {
        setInput(String.format("G\n%c\n", (char) 128)); //Not a valid input
        PromptFactory.getCombatPrompt().run();

        assertEquals("A - attack. I - access inventory", TEST_OUT.toString().split("\n")[3]);
    }

    @Test
    public void enterCapitolLetter() {
        setInput(String.format("I\nexit\n%c\n", (char) 128)); //inventory. Should accept either cap or lower I
        PromptFactory.getCombatPrompt().run();

        //Inventory has been printed
        assertEquals("Tivian\'s inventory has: 3 potion. ", TEST_OUT.toString().split("\n")[3]);
    }

    @Test
    public void attack() {
        String output;

        setInput(String.format("a\n\n\n%c\n", (char) 128));
        PromptFactory.getCombatPrompt().run();

        output = TEST_OUT.toString().split("\n")[4];

        if(output.contains("misses"))
            assertEquals("Tivian misses...", output);
        else if(output.contains("critical"))
            assertEquals("Tivian lands a critical hit!", output);
        else
            assertTrue(output.matches("Tivian deals (\\d) damage to the goblin."));
    }

    @Test
    public void attackAllPossibleOutcomes() {
        for(byte i = 0; i < 100; i++) attack();
    }
}