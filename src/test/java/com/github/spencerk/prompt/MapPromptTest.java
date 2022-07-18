package com.github.spencerk.prompt;

import com.github.spencerk.Prompt.InventoryPrompt;
import com.github.spencerk.Prompt.PromptFactory;
import com.github.spencerk.enums.MapPoint;
import com.github.spencerk.map.Map;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapPromptTest {
    final byte  VERT = 0,
                HRZT = 1;
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
        resetMap();
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

    private byte[] getPlayerPos() {
        Field   playerPosField;

        try {
            playerPosField = Map.class.getDeclaredField("playerPos");
            playerPosField.setAccessible(true);
            return (byte[]) playerPosField.get(Map.getInstance());
        } catch(IllegalAccessException | NoSuchFieldException e) {
            System.err.println("Error: Could not retrieve player position from map for testing!");
        }

        //Hopefully won't hit this line. Only on Reflection fail
        return null;
    }

    //Sets map to an empty map
    private void resetMap() {
        Field           mapPointsField,
                        playerPosField;
        MapPoint[][]    mapLayout;
        Map             map                 = Map.getInstance();

        //Create known test map
        mapLayout = new MapPoint[][]{
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.PLAYER, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND}
        };

        //Set map fields to know values rather than random
        try {
            mapPointsField      = map.getClass().getDeclaredField("map");
            playerPosField      = map.getClass().getDeclaredField("playerPos");

            mapPointsField.setAccessible(true);
            playerPosField.setAccessible(true);

            mapPointsField.set(map, mapLayout);
            playerPosField.set(map, new byte[]{5, 12});
        } catch(NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error: Could not set map data for testing!");
            System.err.println(e.getMessage());
        }

    }

    @Test
    public void enterNothing() {
        setInput("\nn\n");
        PromptFactory.getMenuPrompt().run();

        //Prompt will just ask again
        assertEquals(
                "Enter a command. N - go north. S - go south. E - go east. W - go west. I - open inventory",
                TEST_OUT.toString().split("\n")[1]
        );
    }

    @Test
    public void enterInvalidInput() {
        setInput("P\nn\n"); //Invalid input
        PromptFactory.getMenuPrompt().run();

        //Prompt will just ask again
        assertEquals(
                "Enter a command. N - go north. S - go south. E - go east. W - go west. I - open inventory",
                TEST_OUT.toString().split("\n")[1]
        );
    }

    @Test
    public void enterN() {
        byte[]  posBefore = getPlayerPos(),
                posAfter;

        setInput("N\nn\n"); //Capital letter because the test in the method should be cap insensitive
        PromptFactory.getMenuPrompt().run();

        posAfter = getPlayerPos();

        assert posBefore != null;
        assert posAfter != null;
        assertEquals(posBefore[VERT] - 1, posAfter[VERT]); //Yes, north descending. It's just how it translates
        assertEquals(posBefore[HRZT], posAfter[HRZT]);
    }

    @Test
    public void enterE() {
        byte[]  posBefore = getPlayerPos(),
                posAfter;

        setInput("E\nn\n"); //Capital letter because the test in the method should be cap insensitive
        PromptFactory.getMenuPrompt().run();

        posAfter = getPlayerPos();

        assert posBefore != null;
        assert posAfter != null;
        assertEquals(posBefore[VERT], posAfter[VERT]);
        assertEquals(posBefore[HRZT] + 1, posAfter[HRZT]);
    }

    @Test
    public void enterW() {
        byte[]  posBefore = getPlayerPos(),
                posAfter;

        setInput("W\nn\n"); //Capital letter because the test in the method should be cap insensitive
        PromptFactory.getMenuPrompt().run();

        posAfter = getPlayerPos();

        assert posBefore != null;
        assert posAfter != null;
        assertEquals(posBefore[VERT], posAfter[VERT]);
        assertEquals(posBefore[HRZT] - 1, posAfter[HRZT]);
    }

    @Test
    public void enterS() {
        byte[]  posBefore = getPlayerPos(),
                posAfter;

        setInput("S\nn\n"); //Capital letter because the test in the method should be cap insensitive
        PromptFactory.getMenuPrompt().run();

        posAfter = getPlayerPos();

        assert posBefore != null;
        assert posAfter != null;
        assertEquals(posBefore[VERT] + 1, posAfter[VERT]); //Yes, south ascending. It's just how it translates
        assertEquals(posBefore[HRZT], posAfter[HRZT]);
    }

    @Test
    public void enterI() {
        setInput("I\nn\n");

        //Run method should return inventory prompt
        assertEquals(InventoryPrompt.class, PromptFactory.getMenuPrompt().run().getClass());
    }
}
