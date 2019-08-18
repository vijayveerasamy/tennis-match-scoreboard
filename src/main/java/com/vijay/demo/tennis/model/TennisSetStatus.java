package com.vijay.demo.tennis.model;

public enum TennisSetStatus {
    InProgress(0),
    Tied(1),
    Lost(2),
    Won(3);

    private Integer value;

    public Integer value() { return value; }

    TennisSetStatus(Integer value) { this.value = value; }
}