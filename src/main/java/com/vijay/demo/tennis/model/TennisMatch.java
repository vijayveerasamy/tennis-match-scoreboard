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

    public List<TennisSet> getPlayerTennisSets(String player) {
        return tennisSets.get(player);
    }

    public TennisSet getCurrentSet(final String player) {
        return tennisSets.get(player).get((int)tennisSets.get(player).stream().count() -1);
    }

    public void addCurrentSet(final String player, final TennisSet tennisSet) {

        if(tennisSets.containsKey(player)) {
            tennisSets.get(player).add(tennisSet);
        }
        else {
            tennisSets.put(player, new ArrayList() {{
                add(tennisSet);
            }});
        };
    }

    public String getPlayerA() {
        return playerA;
    }

    public String getPlayerB() {
        return playerB;
    }
}
