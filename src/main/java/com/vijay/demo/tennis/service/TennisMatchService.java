package com.vijay.demo.tennis.service;

import com.vijay.demo.tennis.model.TennisSet;

import java.util.List;

public interface TennisMatchService {

   public String playerAWonPoint(TennisSet tennisSetA);

   public String playerBWonPoint(TennisSet tennisSetB);

   public void currentScore(List<TennisSet> tennisSetsA, List<TennisSet> tennisSetsB);

   public void startMatch();

}
