package com.vijay.demo.tennis.service.impl;

import com.vijay.demo.tennis.model.TennisGame;
import com.vijay.demo.tennis.model.TennisMatch;
import com.vijay.demo.tennis.model.TennisSet;
import com.vijay.demo.tennis.model.TennisSetStatus;
import com.vijay.demo.tennis.service.TennisMatchService;
import com.vijay.demo.tennis.util.TennisMatchUtil;
import com.vijay.demo.tennis.util.TennisScoreUtil;

import java.util.List;

public class TennisMatchServiceImpl implements TennisMatchService {

    private TennisMatch tennisMatch;

    public TennisMatchServiceImpl(TennisMatch tennisMatch) {
        this.tennisMatch = tennisMatch;
    }

    @Override
    public void playerAWonPoint() {

    }

    @Override
    public void playerBWonPoint() {

    }

    @Override
    public void currentScore() {

    }

    private void playerScored(TennisGame tennisGameA, TennisGame tennisGameB, Boolean playerA) {
        if (playerA) {
            if( TennisMatchUtil.playerBAtAdvantage(tennisGameA.getCurrentScore(), tennisGameB.getCurrentScore()) ) {
                tennisGameB.setCurrentScore(
                        TennisScoreUtil.getScorePositionToValue(
                                TennisScoreUtil.getScoreValueToPosition(tennisGameB.getCurrentScore())-1)
                );
            }
            tennisGameA.setCurrentScore(
                    TennisScoreUtil.getScorePositionToValue(
                            TennisScoreUtil.getScoreValueToPosition(tennisGameA.getCurrentScore())+1)
            );
        } else {
            if( TennisMatchUtil.playerAAtAdvantage(tennisGameA.getCurrentScore(), tennisGameB.getCurrentScore()) ) {
                tennisGameA.setCurrentScore(
                        TennisScoreUtil.getScorePositionToValue(
                                TennisScoreUtil.getScoreValueToPosition(tennisGameA.getCurrentScore())-1)
                );
            }
            tennisGameB.setCurrentScore(
                    TennisScoreUtil.getScorePositionToValue(
                            TennisScoreUtil.getScoreValueToPosition(tennisGameB.getCurrentScore())+1)
            );
        }
    }

    public Boolean checkPlayerWonSetAndUpdateStatus(final List<TennisGame> playerAGames, final List<TennisGame> playerBGames) {

        Boolean playerAWonSet = TennisMatchUtil.playerAWonSet(playerAGames, playerBGames);

        Boolean playerBWonSet = TennisMatchUtil.playerAWonSet(playerAGames, playerBGames);

        if( playerAWonSet || playerAWonSet ) {

            if (playerAWonSet) {
                tennisMatch.getCurrentSet(tennisMatch.getPlayerA()).setSetStatus(TennisSetStatus.Won);
                tennisMatch.getCurrentSet(tennisMatch.getPlayerB()).setSetStatus(TennisSetStatus.Lost);
            }
            else if (playerAWonSet) {
                tennisMatch.getCurrentSet(tennisMatch.getPlayerA()).setSetStatus(TennisSetStatus.Lost);
                tennisMatch.getCurrentSet(tennisMatch.getPlayerB()).setSetStatus(TennisSetStatus.Won);
            }

            return true;
        }

        return false;
    }


    @Override
    public void startMatch() {

        while (tennisMatch.getTennisSets().size()<=5) {

            TennisSet tennisSetA = new TennisSet();
            TennisSet tennisSetB = new TennisSet();

            tennisMatch.addCurrentSet(tennisMatch.getPlayerA(), tennisSetA);
            tennisMatch.addCurrentSet(tennisMatch.getPlayerB(), tennisSetB);

            while ( tennisMatch.getCurrentSet(tennisMatch.getPlayerA()).getSetStatus().equals(TennisSetStatus.InProgress) &&
                    tennisMatch.getCurrentSet(tennisMatch.getPlayerB()).getSetStatus().equals(TennisSetStatus.InProgress))  {

                TennisGame tennisGameA = new TennisGame();
                TennisGame tennisGameB = new TennisGame();

                tennisMatch.getCurrentSet(tennisMatch.getPlayerA()).addCurentGame(tennisGameA);
                tennisMatch.getCurrentSet(tennisMatch.getPlayerB()).addCurentGame(tennisGameB);

                while ( !TennisMatchUtil.playerAWonGame(tennisGameA.getCurrentScore(), tennisGameB.getCurrentScore()) &&
                        !TennisMatchUtil.playerBWonGame(tennisGameA.getCurrentScore(), tennisGameB.getCurrentScore()) ) {

                    playerScored(tennisGameA, tennisGameB, TennisMatchUtil.getRandomBoolean());
                }

                //TBD
                //check tied match
                //check tie-brak match
                //5th set no-tie -break check

                if ( checkPlayerWonSetAndUpdateStatus(tennisMatch.getCurrentSet(tennisMatch.getPlayerA()).getGames(),
                        tennisMatch.getCurrentSet(tennisMatch.getPlayerB()).getGames()) ) {
                    break;
                }
                if (TennisMatchUtil.setWasTied(tennisMatch.getCurrentSet(tennisMatch.getPlayerA()).getGames(),
                        tennisMatch.getCurrentSet(tennisMatch.getPlayerB()).getGames()) ) {

                }

            }

        }

    }


    public static void main(String[] args) {
        TennisMatch tennisMatch = new TennisMatch("Raphael Nadal", "Roger Federer");

        TennisMatchService tennisMatchService = new TennisMatchServiceImpl(tennisMatch);

        tennisMatchService.startMatch();
    }
}
