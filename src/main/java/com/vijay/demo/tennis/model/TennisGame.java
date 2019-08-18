package com.vijay.demo.tennis.model;

import java.util.ArrayList;
import java.util.List;

public class TennisGame {
    private Integer score = 0;

    public Integer getCurrentScore() {
        return score;
    }

    public void setCurrentScore(final Integer score) {
        this.score = score;
    }
}
