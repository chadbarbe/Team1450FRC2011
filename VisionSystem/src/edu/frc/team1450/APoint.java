/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.frc.team1450;

import java.util.Comparator;

/**
 *
 * @author parallels
 */
public class APoint {

    public static class xComparator implements Comparator<APoint> {

        @Override
        public int compare(APoint point1, APoint point2) {
            Integer x1 = point1.x();
            Integer x2 = point2.x();
            return x1.compareTo(x2);
        }
    }

    public static class yComparator implements Comparator<APoint> {

        @Override
        public int compare(APoint point1, APoint point2) {
            Integer y1 = point1.y();
            Integer y2 = point2.y();
            return y1.compareTo(y2);
        }
    }
    private int x;
    private int y;

    public APoint(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
