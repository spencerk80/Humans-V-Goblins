package com.github.spencerk.map;

import com.github.spencerk.Prompt.AcquireTreasurePrompt;
import com.github.spencerk.Prompt.Prompt;
import com.github.spencerk.enums.Direction;
import com.github.spencerk.enums.MapPoint;
import com.github.spencerk.exceptions.PathBlockedException;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapTest {
    private MapPoint[][]            mapLayout;
    private final Map                     map         = Map.getInstance();
    private final Direction               toLand      = Direction.NORTH;
    private final Direction toChest     = Direction.SOUTH;
    private final Direction toEnemy     = Direction.WEST;
    private final Direction toTree      = Direction.EAST;

    @BeforeEach
    public void resetMap() {
        Field   mapPointsField,
                playerPosField;

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
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.ENEMY,
                        MapPoint.PLAYER, MapPoint.TREE, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND},
                {MapPoint.LAND, MapPoint.LAND,  MapPoint.LAND,   MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
                        MapPoint.CHEST, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND, MapPoint.LAND,
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

    private MapPoint[][] getMapPoints() {
        Field   mapPoints;
        Map     map = Map.getInstance();

        try {
            mapPoints = map.getClass().getDeclaredField("map");
            mapPoints.setAccessible(true);
            return (MapPoint[][]) mapPoints.get(map);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            System.err.println("Error: Could not extract map of points in testing!");
        }

        return null;
    }

    private boolean mapsAreEqual(MapPoint[][] map1, MapPoint[][] map2) {
        for(byte row = 0; row < map1.length; row++)
            for(byte col = 0; col < map1[0].length; col++)
                if(map1[row][col] != map2[row][col])
                    return false;

        return true;
    }

    @Test
    public void movePlayerToLand() {
        String mapLine;

        try{
            map.movePlayer(toLand);
        } catch(PathBlockedException e) {
            //Should not happen. Player is moving to land
            fail();
        }
        mapLine = map.toString().split("\n")[5];

        assertEquals(Map.PLAYER_SYMBOL.charAt(0), mapLine.charAt(13));
    }

    @Test
    public void movePlayerToTree() {
        try {
            map.movePlayer(toTree);
            //Should not reach this
            fail();
        } catch(PathBlockedException e) {
            //Expecting this
            assertTrue(true);
        }
    }

    @Test
    public void movePlayerToChest() {
        Prompt returnedPrompt = null;
        String mapLine;

        try {
            returnedPrompt = map.movePlayer(toChest);
        } catch(PathBlockedException e) {
            //Should not get this
            fail();
        }
        assertTrue(returnedPrompt instanceof AcquireTreasurePrompt);

        mapLine = map.toString().split("\n")[7];

        assertEquals(Map.PLAYER_SYMBOL.charAt(0), mapLine.charAt(13));

        try {
            map.movePlayer(toLand);
        } catch(PathBlockedException e) {
            //Should not get this
            fail();
        }

        mapLine = map.toString().split("\n")[7];

        //Expecting chest to be gone because it would've been opened
        assertEquals(Map.LAND_SYMBOL.charAt(0), mapLine.charAt(13));
    }

    @Test
    public void movePlayerToEnemy() {
        String mapLine;

        try {
            map.movePlayer(toEnemy);
        } catch(PathBlockedException e) {
            //Should not get this
            fail();
        }

        mapLine = map.toString().split("\n")[6];

        assertEquals(Map.PLAYER_SYMBOL.charAt(0), mapLine.charAt(12));

        try {
            map.movePlayer(toLand);
        } catch(PathBlockedException e) {
            //Should not get this
            fail();
        }

        mapLine = map.toString().split("\n")[6];

        //Enemy should be consumed and gone
        assertEquals(Map.LAND_SYMBOL.charAt(0), mapLine.charAt(12));
    }

    @Test
    public void testMapOverflow() {
        String mapLine;

        //By stepping over the top map frame, the player is put on a new map at the south end
        try {
            for(byte i = 0; i < 6; i++) map.movePlayer(toLand);
        } catch(PathBlockedException e) {
            //Should not get this
            fail();
        }

        //Test whether a new map has been made
        assertFalse(mapsAreEqual(mapLayout, getMapPoints()));

        //Test whether player is at the south end now
        mapLine = map.toString().split("\n")[11];

        assertEquals(Map.PLAYER_SYMBOL.charAt(0), mapLine.charAt(13));
    }
}
