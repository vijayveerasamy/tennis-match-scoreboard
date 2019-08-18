package com.vijay.demo.tennis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TennisMatch {

    private Map<String, List<TennisSet>> tennisSets;

    private String playerA;
    private String playerB;

    public TennisMatch(final String playerA, final String playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        tennisSets = new HashMap<>();
    }

    public Map<String, List<TennisSet>> getTennisSets() {
        return tennisSets;
    }

    public TennisSet getCurrentSet(final String player) {
        return tennisSets.get(player).get(tennisSets.get(player).size()-1);
    }

    public void setCurrentSet(final String player, final TennisSet tennisSet) {
        tennisSets.get(player).set(tennisSets.get(player).size() - 1, tennisSet);
    }

    public void addCurrentSet(final String player, final TennisSet tennisSet) {
        tennisSets.putIfAbsent(player, new ArrayList() {{
            add(tennisSet);
        }});
    }

    public String getPlayerA() {
        return playerA;
    }

    public String getPlayerB() {
        return playerB;
    }
}
