package com.vijay.demo.tennis.model;

import java.util.ArrayList;
import java.util.List;

public class TennisSet {
    private List<TennisGame> games = new ArrayList<>();

    private TennisSetStatus setStatus = TennisSetStatus.InProgress;

    private Boolean tieBreak = false;

    public List<TennisGame> getGames() {
        return games;
    }

    public TennisGame getCurentGame() {
        return games.get(games.size()-1);
    }

    public void setCurentGame(final TennisGame tennisGame) {
        games.set(games.size()-1, tennisGame);
    }

    public void addCurentGame(final TennisGame tennisGame) {
        games.add(tennisGame);
    }

    public TennisSetStatus getSetStatus() {
        return setStatus;
    }

    public void setSetStatus(TennisSetStatus setStatus) {
        this.setStatus = setStatus;
    }

    public Boolean getTieBreak() {
        return tieBreak;
    }

    public void setTieBreak(Boolean tieBreak) {
        this.tieBreak = tieBreak;
    }
}
