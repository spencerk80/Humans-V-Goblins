package com.github.spencerk;

import com.github.spencerk.enums.Direction;
import com.github.spencerk.exceptions.PathBlockedException;
import com.github.spencerk.map.Map;

public class Game {

    public static void main(String[] args) {
        Map map = new Map();

        System.out.println(map.toString());
        for(int i = 0; i < 15; i++) try {
            map.movePlayer(Direction.WEST);
            System.out.println(map.toString());
        } catch(PathBlockedException pbe) {
            System.err.println(pbe.getMessage());
        }

    }

}
