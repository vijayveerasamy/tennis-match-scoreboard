package com.vijay.demo.tennis.util;

import com.vijay.demo.tennis.model.TennisGame;
import com.vijay.demo.tennis.model.TennisSet;

import java.util.List;
import java.util.Random;

public class TennisMatchUtil {
    private static final Random random = new Random();

    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    public static Integer getRandomNumber() {
        return random.nextInt();
    }

    public static boolean playerAAtDeuce(final Integer playerAScore, final Integer playerBScore) {

        if (playerAScore == 40 && TennisScoreUtil.getScoreValueToPosition(playerAScore) == TennisScoreUtil.getScoreValueToPosition(playerBScore) + 1)
            return true;

        return false;
    }

    public static boolean playerBAtDeuce(final Integer playerAScore, final Integer playerBScore) {

        if (playerBScore == 40 && TennisScoreUtil.getScoreValueToPosition(playerBScore) == TennisScoreUtil.getScoreValueToPosition(playerAScore) + 1)
            return true;

        return false;
    }

    public static boolean playerAAtAdvantage(final Integer playerAScore, final Integer playerBScore) {

        if (playerAScore == 45 && TennisScoreUtil.getScoreValueToPosition(playerAScore) == TennisScoreUtil.getScoreValueToPosition(playerBScore) + 1)
            return true;

        return false;
    }

    public static boolean playerBAtAdvantage(final Integer playerAScore, final Integer playerBScore) {

        if (playerBScore == 45 && TennisScoreUtil.getScoreValueToPosition(playerBScore) == TennisScoreUtil.getScoreValueToPosition(playerAScore) + 1)
            return true;

        return false;
    }

    public static boolean playerAWonGame(final Integer playerAScore, final Integer playerBScore) {

        if (playerAScore == 50 && TennisScoreUtil.getScoreValueToPosition(playerAScore) >= TennisScoreUtil.getScoreValueToPosition(playerBScore) + 2)
            return true;

        return false;
    }

    public static boolean playerBWonGame(final Integer playerAScore, final Integer playerBScore) {

        if (playerBScore == 50 && TennisScoreUtil.getScoreValueToPosition(playerBScore) >= TennisScoreUtil.getScoreValueToPosition(playerAScore) + 2)
            return true;

        return false;
    }

    public static boolean setWasTied(final List<TennisGame> playerAGames, final List<TennisGame> playerBGames) {

        Long playAGamesWonCount = playerAGames.stream().filter(g -> g.getCurrentScore()==50).count();
        Long playBGamesWonCount = playerAGames.stream().filter(g -> g.getCurrentScore()==50).count();

        if( playAGamesWonCount==7 && playBGamesWonCount==6 && (playAGamesWonCount-playBGamesWonCount<2) ) {
            return true;
        }
        else if( playAGamesWonCount==6 && playBGamesWonCount==7 && (playBGamesWonCount-playAGamesWonCount<2) ) {
            return true;
        }

        return false;
    }

    public static boolean playerAWonSet(final List<TennisGame> playerAGames, final List<TennisGame> playerBGames) {

        long playAGamesWonCount = playerAGames.stream().filter(g -> g.getCurrentScore()==50).count();
        long playBGamesWonCount = playerBGames.stream().filter(g -> g.getCurrentScore()==50).count();

        if( playAGamesWonCount>=6 && (playAGamesWonCount-playBGamesWonCount>=2) ) {
            return true;
        }

        return false;
    }

    public static boolean playerBWonSet(final List<TennisGame> playerAGames, final List<TennisGame> playerBGames) {

        long playAGamesWonCount = playerAGames.stream().filter(g -> g.getCurrentScore()==50).count();
        long playBGamesWonCount = playerAGames.stream().filter(g -> g.getCurrentScore()==50).count();

        if( playBGamesWonCount>=6 && (playBGamesWonCount-playAGamesWonCount>=2) ) {
            return true;
        }

        return false;
    }

}
