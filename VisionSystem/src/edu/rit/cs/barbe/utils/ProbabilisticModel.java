package edu.rit.cs.barbe.utils;

import java.util.Random;

public class ProbabilisticModel implements BooleanModelIF {
    
    private Random myRandom = new Random();
    private int probabilityOutOfOneHundred = 0;
    
    public ProbabilisticModel(float probability)
    {
        setProbability(probability);
    }

    public float getProbability() {
        return probabilityOutOfOneHundred / 100f;
    }

    public void setProbability(float probability) {
        probabilityOutOfOneHundred = Math.round(probability * 100);
    }

    public boolean isTrue() {
        return (myRandom.nextInt(100)<probabilityOutOfOneHundred);
    }

}
