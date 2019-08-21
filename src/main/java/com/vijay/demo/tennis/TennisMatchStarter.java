package com.vijay.demo.tennis;

import com.vijay.demo.tennis.model.TennisMatch;
import com.vijay.demo.tennis.service.TennisMatchService;
import com.vijay.demo.tennis.service.impl.TennisMatchServiceImpl;

public class TennisMatchStarter {
    public static void main(String[] args) {
        TennisMatch tennisMatch = new TennisMatch("Raphael Nadal", "Roger Federer");

        TennisMatchService tennisMatchService = new TennisMatchServiceImpl(tennisMatch);

        tennisMatchService.startMatch();
    }
}
