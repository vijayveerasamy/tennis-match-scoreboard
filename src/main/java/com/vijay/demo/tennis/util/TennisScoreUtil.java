package com.vijay.demo.tennis.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TennisScoreUtil {


    /*
    Value to Position
    Love
    15
    30
    40-Deuce
    AD
    Game
 */
    private static final Map<Integer, Integer> scoreValueToPosition = new HashMap<Integer, Integer>(){{
        put(0, 0);
        put(15, 1);
        put(30, 2);
        put(40, 3);
        put(45, 4);
        put(50, 5);
    }};

    public static Integer getScoreValueToPosition(final Integer value) {
        return scoreValueToPosition.get(value);
    }

    /*
        Position to Value
        Love
        15
        30
        40-Deuce
        AD
        Game
     */
    private static final Map<Integer, Integer> scorePositionToValue = new HashMap<Integer, Integer>(){{
        put(0, 0);
        put(1, 15);
        put(2, 30);
        put(3, 40);
        put(4, 45);
        put(5, 50);
    }};

    public static Integer getScorePositionToValue(final Integer position) {
        return scorePositionToValue.get(position);
    }

    private static final Map<Integer, Integer> scoreValueToPoint = new HashMap<Integer, Integer>(){{
        put(0, 0);
        put(15, 1);
        put(30, 2);
        put(40, 3);
        put(50, 4);
    }};

    public static Integer getScoreValueToPoint(final Integer value) {
        return scoreValueToPoint.get(value);
    }

    public static String translateScoreValue(final Integer value) {
        String returnScore = null;
        switch (value) {
            case 0: returnScore="Love";
            break;
            case 45: returnScore="AD";
            break;
            case 50: returnScore="Game";
            break;
            default: returnScore=value.toString();
            break;
        }
        return returnScore;
    }
}
