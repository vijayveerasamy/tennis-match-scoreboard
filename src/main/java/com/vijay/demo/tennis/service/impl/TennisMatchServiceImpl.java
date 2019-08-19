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

    public Boolean isItTieBreakSet(TennisSet tennisSetA, TennisSet tennisSetB) {
        return tennisSetA.getTieBreak() && tennisSetB.getTieBreak();
    }

    public Boolean isItFifthSet(List<TennisSet> tennisSetsA, List<TennisSet> tennisSetsB) {

        Long validSetsA = tennisSetsA.stream().filter(s -> s.getSetStatus()!=TennisSetStatus.Tied).count();

        Long validSetsB = tennisSetsB.stream().filter(s -> s.getSetStatus()!=TennisSetStatus.Tied).count();

        if (validSetsA==validSetsB && validSetsA==5 && validSetsB==5) return true;
        else return false;
    }

    private void playerScored(TennisGame tennisGameA, TennisGame tennisGameB, Integer playerA) {
        if (playerA%2==0) {
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

    public boolean checkSetTiedAndUpdate(final List<TennisGame> playerAGames, final List<TennisGame> playerBGames) {

        if (TennisMatchUtil.setWasTied(playerAGames, playerBGames)) {
            tennisMatch.getCurrentSet(tennisMatch.getPlayerA()).setSetStatus(TennisSetStatus.Tied);
            tennisMatch.getCurrentSet(tennisMatch.getPlayerB()).setSetStatus(TennisSetStatus.Tied);
            return true;
        }

        return false;
    }

    public Boolean checkPlayerWonSetAndUpdateStatus(final List<TennisGame> playerAGames, final List<TennisGame> playerBGames) {

        Boolean playerAWonSet = TennisMatchUtil.playerAWonSet(playerAGames, playerBGames);

        Boolean playerBWonSet = TennisMatchUtil.playerBWonSet(playerAGames, playerBGames);

        if( playerAWonSet || playerAWonSet ) {

            if (playerAWonSet) {
                tennisMatch.getCurrentSet(tennisMatch.getPlayerA()).setSetStatus(TennisSetStatus.Won);
                tennisMatch.getCurrentSet(tennisMatch.getPlayerB()).setSetStatus(TennisSetStatus.Lost);
            }
            else if (playerBWonSet) {
                tennisMatch.getCurrentSet(tennisMatch.getPlayerA()).setSetStatus(TennisSetStatus.Lost);
                tennisMatch.getCurrentSet(tennisMatch.getPlayerB()).setSetStatus(TennisSetStatus.Won);
            }

            return true;
        }

        return false;
    }


    @Override
    public void startMatch() {

        Boolean tieBreakRequest = false;

        do {

            TennisSet tennisSetA = new TennisSet();
            TennisSet tennisSetB = new TennisSet();

            tennisSetA.setTieBreak(tieBreakRequest);
            tennisSetB.setTieBreak(tieBreakRequest);
            tieBreakRequest = false;

            tennisMatch.addCurrentSet(tennisMatch.getPlayerA(), tennisSetA);
            tennisMatch.addCurrentSet(tennisMatch.getPlayerB(), tennisSetB);

            while ( tennisSetA.getSetStatus().equals(TennisSetStatus.InProgress) &&
                    tennisSetB.getSetStatus().equals(TennisSetStatus.InProgress))  {

                TennisGame tennisGameA = new TennisGame();
                TennisGame tennisGameB = new TennisGame();

                tennisSetA.addCurentGame(tennisGameA);
                tennisSetB.addCurentGame(tennisGameB);

                while ( !TennisMatchUtil.playerAWonGame(tennisGameA.getCurrentScore(), tennisGameB.getCurrentScore()) &&
                        !TennisMatchUtil.playerBWonGame(tennisGameA.getCurrentScore(), tennisGameB.getCurrentScore()) ) {

                    playerScored(tennisGameA, tennisGameB, TennisMatchUtil.getRandomNumber());
                }

                if ( checkSetTiedAndUpdate(tennisSetA.getGames(),
                        tennisSetB.getGames()) &&
                        !isItTieBreakSet(tennisSetA, tennisSetB) &&
                        !isItFifthSet(tennisMatch.getPlayerTennisSets(tennisMatch.getPlayerA()),
                                tennisMatch.getPlayerTennisSets(tennisMatch.getPlayerB())) ) {

                    tieBreakRequest = true;

                    break;
                }

                if ( checkPlayerWonSetAndUpdateStatus(tennisSetA.getGames(),
                        tennisSetB.getGames()) ) {
                    break;
                }
            }

        } while (!isItFifthSet(tennisMatch.getPlayerTennisSets(tennisMatch.getPlayerA()),
        tennisMatch.getPlayerTennisSets(tennisMatch.getPlayerB())));

    }


    public static void main(String[] args) {
        TennisMatch tennisMatch = new TennisMatch("Raphael Nadal", "Roger Federer");

        TennisMatchService tennisMatchService = new TennisMatchServiceImpl(tennisMatch);

        tennisMatchService.startMatch();
    }
}
