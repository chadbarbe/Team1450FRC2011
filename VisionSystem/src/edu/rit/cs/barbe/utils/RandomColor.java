package edu.rit.cs.barbe.utils;

import java.awt.Color;
import java.util.Random;

public class RandomColor
{
    
    private Random rand;

    /**
     * Constructor for objects of class RandomColor initializes the
     * random number generator
     */
    public RandomColor()
    {
        rand = new Random();
    }

    /**
     * randomColor returns a pseudorandom Color
     * 
     * @return a pseudorandom Color
     */
    public Color randomColor()
    {
        int choice = rand.nextInt(4);
        
        if (choice == 0)
            return Color.blue;
        if (choice == 1)
            return Color.red;
        if (choice == 2)
            return Color.green;
        else
            return Color.yellow;
        
    }
    
    /**
     * randomGray returns a pseudorandom gray Color
     * 
     * @return a pseudorandom Color
     */
    public Color randomGray()
    {
        int intensity = rand.nextInt(256);
        return(new Color(intensity,intensity,intensity));
    }
}