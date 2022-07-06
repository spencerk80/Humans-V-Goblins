package com.github.spencerk.map;

import com.github.spencerk.Prompt.Prompt;
import com.github.spencerk.Prompt.PromptFactory;
import com.github.spencerk.enums.Direction;
import com.github.spencerk.enums.MapPoint;
import com.github.spencerk.exceptions.PathBlockedException;

import java.util.Random;

public class Map {
    public final static String  LAND_SYMBOL             = " ",
                                PLAYER_SYMBOL           = "Θ",
                                ENEMY_SYMBOL            = "Ѫ",
                                TREE_SYMBOL             = "Ψ",
                                CHEST_SYMBOL            = "Ŵ";
    private final static char   MAP_FRAME_TOP_RIGHT     = (char) 0x2554,
                                MAP_FRAME_TOP_LEFT      = (char) 0x2557,
                                MAP_FRAME_HORIZONTAL    = (char) 0x2550,
                                MAP_FRAME_VERTICAL      = (char) 0x2551,
                                MAP_FRAME_BOTTOM_RIGHT  = (char) 0x255A,
                                MAP_FRAME_BOTTOM_LEFT   = (char) 0x255D;
    private final static byte   MAP_DIM_VERTICAL        = 11,
                                MAP_DIM_HORIZONTAL      = 25,
                                PLAYER_START_VERT_POS   = MAP_DIM_VERTICAL / 2,
                                PLAYER_START_HRZNTL_POS = MAP_DIM_HORIZONTAL / 2,
                                VERT                    = 0,
                                HRZTL                   = 1;
    private final static short  TREE_RATIO              = MAP_DIM_HORIZONTAL,
                                ENEMY_RATIO             = MAP_DIM_HORIZONTAL * 2,
                                CHEST_RATIO             = MAP_DIM_HORIZONTAL * 6;
    private static MapPoint[][] map                     = null;
    private static final Random RANDOM                  = new Random();
    private byte[]              playerPos;
    private static Map          instance                = null;

    /*----------------------------------------------------------------------------------------------------------------*
     * Constructor
     *----------------------------------------------------------------------------------------------------------------*/
    public Map() {
            playerPos = new byte[]{PLAYER_START_VERT_POS, PLAYER_START_HRZNTL_POS};
            generateMap();
    }

    /*----------------------------------------------------------------------------------------------------------------*
     * Public methods
     *----------------------------------------------------------------------------------------------------------------*/
    public static Map getInstance() {
        if(instance == null) instance = new Map();
        return instance;
    }
    public Prompt movePlayer(Direction direction) throws PathBlockedException {
        byte[]      spaceWanted     = new byte[]{playerPos[VERT], playerPos[HRZTL]};
        boolean     regenerateMap   = false;
        MapPoint    pointWanted;

        //Get the next location. Wrap around the map and set regenerateMap true if needed
        switch(direction) {
            case NORTH:
                if(spaceWanted[VERT] - 1 == -1) {
                    spaceWanted[VERT] = MAP_DIM_VERTICAL - 1;
                    regenerateMap = true;
                } else spaceWanted[VERT] -= 1;
                break;
            case EAST:
                if(spaceWanted[HRZTL] + 1 == MAP_DIM_HORIZONTAL) {
                    spaceWanted[HRZTL] = 0;
                    regenerateMap = true;
                } else spaceWanted[HRZTL] += 1;
                break;
            case SOUTH:
                if(spaceWanted[VERT] + 1 == MAP_DIM_VERTICAL) {
                    spaceWanted[VERT] = 0;
                    regenerateMap = true;
                } else spaceWanted[VERT] += 1;
                break;
            case WEST:
                if(spaceWanted[HRZTL] - 1 == -1) {
                    spaceWanted[HRZTL] = MAP_DIM_HORIZONTAL - 1;
                    regenerateMap = true;
                } else spaceWanted[HRZTL] -= 1;
        }

        //Grab the MapPoint at the new spot
        pointWanted = map[spaceWanted[VERT]]
                         [spaceWanted[HRZTL]];

        //Check if it's of various types and do accordingly
        if( ! regenerateMap && pointWanted == MapPoint.TREE) {
            throw new PathBlockedException("Player ran into a tree");
        } else if( ! regenerateMap && pointWanted == MapPoint.ENEMY) {
            map[spaceWanted[VERT]][spaceWanted[HRZTL]] = MapPoint.LAND;
            updatePlayerPos(spaceWanted, false);

            return PromptFactory.getCombatPrompt();
        } else if( ! regenerateMap && pointWanted == MapPoint.CHEST) {
            map[spaceWanted[VERT]][spaceWanted[HRZTL]] = MapPoint.LAND;
            updatePlayerPos(spaceWanted, false);

            return PromptFactory.getAcquireTreasurePrompt();
        } else {
            updatePlayerPos(spaceWanted, regenerateMap);

            return PromptFactory.getMapPrompt();
        }

    }
    /*----------------------------------------------------------------------------------------------------------------*
     * Private methods
     *----------------------------------------------------------------------------------------------------------------*/
    private void generateMap() {
        map = new MapPoint[MAP_DIM_VERTICAL][MAP_DIM_HORIZONTAL];

        for(byte row = 0; row < MAP_DIM_VERTICAL; row++)
            for(byte column = 0; column < MAP_DIM_HORIZONTAL; column++) {
                int treeChance = RANDOM.nextInt(TREE_RATIO);
                int chestChance = RANDOM.nextInt(CHEST_RATIO);
                int enemyChance = RANDOM.nextInt(ENEMY_RATIO);

                if(treeChance == 0)
                    map[row][column] = MapPoint.TREE;
                else if(chestChance == 0)
                    map[row][column] = MapPoint.CHEST;
                else if(enemyChance == 0) {
                    map[row][column] = MapPoint.ENEMY;
                } else
                    map[row][column] = MapPoint.LAND;
            }

        map[playerPos[VERT]][playerPos[HRZTL]] = MapPoint.PLAYER;
    }

    private void updatePlayerPos(byte[] spaceWanted, boolean regenerateMap) {
        map[playerPos[VERT]][playerPos[HRZTL]] = MapPoint.LAND;
        playerPos = spaceWanted;
        map[playerPos[VERT]][playerPos[HRZTL]] = MapPoint.PLAYER;

        if(regenerateMap) generateMap();
    }

    /*----------------------------------------------------------------------------------------------------------------*
     * Overridden methods
     *----------------------------------------------------------------------------------------------------------------*/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        /*----------------------*
         * Top frame of the map
         *----------------------*/
        sb.append(MAP_FRAME_TOP_RIGHT);
        for(byte column = 0; column < MAP_DIM_HORIZONTAL; column++) sb.append(MAP_FRAME_HORIZONTAL);
        sb.append(MAP_FRAME_TOP_LEFT);
        sb.append("\n");

        /*-----------------------------------*
         * Side frames + map content per row
         *-----------------------------------*/
        for(byte row = 0; row < MAP_DIM_VERTICAL; row++) {
            //Frame on the left side
            sb.append(MAP_FRAME_VERTICAL);

            //Map content for row i
            for(byte column = 0; column < MAP_DIM_HORIZONTAL; column++) {
                switch(map[row][column]) {
                    case LAND:
                        sb.append(LAND_SYMBOL);
                        break;
                    case TREE:
                        sb.append(TREE_SYMBOL);
                        break;
                    case CHEST:
                        sb.append(CHEST_SYMBOL);
                        break;
                    case ENEMY:
                        sb.append(ENEMY_SYMBOL);
                        break;
                    case PLAYER:
                        sb.append(PLAYER_SYMBOL);
                }
            }

            //Frame on the right side
            sb.append(MAP_FRAME_VERTICAL);
            sb.append("\n");
        }

        /*-------------------------*
         * Bottom frame of the map
         *-------------------------*/
        sb.append(MAP_FRAME_BOTTOM_RIGHT);
        for(byte i = 0; i < MAP_DIM_HORIZONTAL; i++) sb.append(MAP_FRAME_HORIZONTAL);
        sb.append(MAP_FRAME_BOTTOM_LEFT);
        sb.append("\n");

        return sb.toString();
    }

}
