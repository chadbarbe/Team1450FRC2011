/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.frc.team1450;

/**
 *
 * @author parallels
 */
public class ALine {

    APoint second;
    APoint first;

    public ALine(APoint _first, APoint _second) {
        first = _first;
        second = _second;
    }

    public APoint firstPoint() {
        return first;
    }

    public APoint secondPoint() {
        return second;
    }
    
    public String toString() {
        return first + " to " + second;
    }
}
