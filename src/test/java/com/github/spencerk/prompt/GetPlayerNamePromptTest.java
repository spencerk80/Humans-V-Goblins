package com.github.spencerk.prompt;

import com.github.spencerk.Prompt.MapPrompt;
import com.github.spencerk.Prompt.Prompt;
import com.github.spencerk.Prompt.PromptFactory;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetPlayerNamePromptTest {
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
    public void enterNoName() {
        setInput("\nKris\ny\n");
        PromptFactory.getGetPlayerNamePrompt().run();

        //Prompt will just ask again
        assertEquals("Enter your character's name: ", TEST_OUT.toString().split("\n")[1]);
    }

    @Test
    public void enterNothingOnConfirmation() {
        setInput("Kris\n\ny\n");
        PromptFactory.getGetPlayerNamePrompt().run();

        //Prompt should just ask again
        assertEquals("Kris. Are you sure that's your name?(y/n): ", TEST_OUT.toString().split("\n")[2]);
    }

    @Test
    public void enterInvalidConfirm() {
        setInput("Kris\nG\ny\n"); //G is invalid
        PromptFactory.getGetPlayerNamePrompt().run();

        //Prompt should just ask again
        assertEquals("Kris. Are you sure that's your name?(y/n): ", TEST_OUT.toString().split("\n")[2]);
    }

    @Test
    public void changeName() {
        Prompt returnedPrompt;

        setInput("Kris\nn\nKristoffer\ny\n");
        returnedPrompt = PromptFactory.getGetPlayerNamePrompt().run();

        //Prompt should just ask again
        assertEquals("Kris. Are you sure that's your name?(y/n): ", TEST_OUT.toString().split("\n")[1]);
        assertEquals("Enter your character's name: ", TEST_OUT.toString().split("\n")[2]);
        assertEquals(
                "Kristoffer. Are you sure that's your name?(y/n): ",
                TEST_OUT.toString().split("\n")[3]
        );
        assertSame(returnedPrompt.getClass(), MapPrompt.class);
    }
}
