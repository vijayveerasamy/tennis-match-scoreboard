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
    public String playerAWonPoint(TennisSet tennisSetA) {
        return TennisScoreUtil.translateScoreValue(tennisSetA.getCurentGame().getCurrentScore());
    }

    @Override
    public String playerBWonPoint(TennisSet tennisSetB) {
        return TennisScoreUtil.translateScoreValue(tennisSetB.getCurentGame().getCurrentScore());
    }

    @Override
    public void currentScore(List<TennisSet> tennisSetsA, List<TennisSet> tennisSetsB) {

        Long playerASetsWon = tennisSetsA.stream().filter(s -> s.getSetStatus().equals(TennisSetStatus.Won)).count();
        Long playerBSetsWon = tennisSetsB.stream().filter(s -> s.getSetStatus().equals(TennisSetStatus.Won)).count();

        Long gamesWonCurrentSetA = tennisSetsA.get(tennisSetsA.size()-1).getGames().stream().filter(s -> s.getCurrentScore()==50).count();
        Long gamesWonCurrentSetB = tennisSetsB.get(tennisSetsA.size()-1).getGames().stream().filter(s -> s.getCurrentScore()==50).count();

        String playerAWonPoints = playerAWonPoint(tennisSetsA.get(tennisSetsA.size()-1));
        String playerBWonPoints = playerBWonPoint(tennisSetsB.get(tennisSetsB.size()-1));

        playerBWonPoint(tennisSetsA.get(tennisSetsB.size()-1));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current Score: ");
        stringBuilder.append(playerASetsWon);
        stringBuilder.append("/");
        stringBuilder.append(playerBSetsWon);
        stringBuilder.append(" ");
        stringBuilder.append(gamesWonCurrentSetA);
        stringBuilder.append("/");
        stringBuilder.append(gamesWonCurrentSetB);
        stringBuilder.append(" ");
        stringBuilder.append(playerAWonPoints);
        stringBuilder.append("/");
        stringBuilder.append(playerBWonPoints);

        System.out.println(stringBuilder.toString());
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

    private void playerScored(TennisGame tennisGameA, TennisGame tennisGameB, Integer random) {
        if (random%2!=0) {
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

    public Boolean checkPlayerWonSetAndUpdateStatus(final TennisSet playerASet, final TennisSet playerBSet) {

        Boolean playerAWonSet = TennisMatchUtil.playerAWonSet(playerASet.getGames(), playerBSet.getGames());

        Boolean playerBWonSet = TennisMatchUtil.playerBWonSet(playerASet.getGames(), playerBSet.getGames());

        if( playerAWonSet || playerBWonSet ) {

            if (playerAWonSet) {
                playerASet.setSetStatus(TennisSetStatus.Won);
                playerBSet.setSetStatus(TennisSetStatus.Lost);
            }
            else if (playerBWonSet) {
                playerASet.setSetStatus(TennisSetStatus.Lost);
                playerBSet.setSetStatus(TennisSetStatus.Won);
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

                if ( checkPlayerWonSetAndUpdateStatus(tennisSetA, tennisSetB) ) {
                    break;
                }
            }
            currentScore(tennisMatch.getPlayerTennisSets(tennisMatch.getPlayerA()), tennisMatch.getPlayerTennisSets(tennisMatch.getPlayerB()) );
            System.out.println("------------------------------------------");

        } while (!isItFifthSet(tennisMatch.getPlayerTennisSets(tennisMatch.getPlayerA()),
        tennisMatch.getPlayerTennisSets(tennisMatch.getPlayerB())));

    }
}
