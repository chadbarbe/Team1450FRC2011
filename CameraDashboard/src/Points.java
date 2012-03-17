/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpijavacv.WPIPoint;
import edu.wpi.first.wpijavacv.WPIPolygon;

import java.awt.*;

import static com.googlecode.javacv.cpp.opencv_core.*;

/**
 *
 * @author pkb
 */
public class Points {

    private final int[] data;
    private int size;

    public Points(int capacity) {
        data = new int[capacity * 2];
        size = 0;
    }

    public Points() {
        this(100);
    }
// (301, 112), (316, 127), (290, 140), (299, 157), (283, 158), (311, 176), (318, 114)
    public static Points fromCvPoints(CvSeq cvPoly) {
        int n = cvPoly.total();
        CvPoint points = new CvPoint(n);
        Points pts = new Points(n);
        cvCvtSeqToArray(cvPoly, points.position(0), CV_WHOLE_SEQ);

        for (int i = 0; i < n; i++) {
            CvPoint pt = points.position(i);
            int x = pt.x();
            int y = pt.y();
            pts.add(x, y);
        }

        return pts;
    }

    static Points fromWPIPolygon(WPIPolygon poly) {
        WPIPoint[] wpts = poly.getPoints();
        int n = wpts.length;
        Points pts = new Points(n);
        for (int i = 0; i < n; i++) {
            WPIPoint wpt = wpts[i];
            pts.add(wpt.getX(), wpt.getY());
        }
        return pts;
    }

    public int capacity() {
        return data.length / 2;
    }

    public int size() {
        return size;
    }

    public boolean add(int x, int y) {
        if (size == capacity()) {
            return false;
        }
        addNoCheck(x, y);
        return true;
    }

    public int getX(int idx) {
        return data[idx * 2];
    }

    public int getY(int idx) {
        return data[(idx * 2) + 1];
    }

    /**
     * Get a string representation of a list of points.
     *
     * @return String containing formatted list of points like: "(3, 29), (202, 103), ..."
     */
    @Override
    public String toString() {
        int n = size();
        StringBuilder out = new StringBuilder((n * 12) + 80);
        out.append(n);
        out.append(" vertices: ");
        for (int i = 0; i < n; i++) {
            out.append((i == 0) ? "(" : ", (");
            out.append(getX(i));
            out.append(", ");
            out.append(getY(i));
            out.append(")");
        }
        return out.toString();
    }

    private void addNoCheck(int x, int y) {
        int idx = size * 2;
        data[idx++] = x;
        data[idx] = y;
        size++;
    }

    /**
     * Draws outline of the polygon assuming points represent vertices.
     *
     * @param g Graphics context to draw the polygon on.
     */
    public void drawPolygonOutline(Graphics g) {
        int n = size();
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            g.setColor(Color.RED);
            g.drawLine(getX(i), getY(i), getX(j), getY(j));
        }
    }
    
    public void drawFilledPolygon(Graphics g) {
        int n = size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];
        for (int i = 0; i < n; i++) {
            xPoints[i] = getX(i);
            yPoints[i] = getY(i);
        }
        g.setColor(Color.RED);
        g.fillPolygon(xPoints,yPoints,n);
    }

    public int getWidth() {
        int minX = 640;
        int maxX = 0;
       for (int i = 0; i < size(); i++) {
           int x = getX(i);
           if (x < minX) minX = x;
           if (x > maxX) maxX = x;
       }
        return maxX - minX;
    }

    public int getMinX() {
        int minX = 640;
        for (int i = 0; i < size(); i++) {
            if (getX(i) < minX) minX = getX(i);
        }
        return minX;
    }

    public int getMaxX() {
        int maxX = 0;
        for (int i = 0; i < size(); i++) {
            if (getX(i) > maxX) maxX = getX(i);
        }
        return maxX;
    }

    public int getMinY() {
        int minY = 640;
        for (int i = 0; i < size(); i++) {
            if (getY(i) < minY) minY = getY(i);
        }
        return minY;
    }

    public int getMaxY() {
        int maxY = 0;
        for (int i = 0; i < size(); i++) {
            if (getY(i) > maxY) maxY = getY(i);
        }
        return maxY;
    }

    public boolean inside(Points other) {
        boolean minXTest = getMinX() > other.getMinX();
        boolean maxXTest = getMaxX() < other.getMaxX();
        boolean minYTest = getMinY() > other.getMinY();
        boolean maxYTest = getMaxY() < other.getMaxY();
        return minXTest &&
                maxXTest &&
                minYTest &&
                maxYTest;

    }

    public Polygon toPolygon() {
        Polygon polygon = new Polygon();
        for (int i = 0; i < size ; i++) {
            polygon.addPoint(getX(i),getY(i));
        }
        return polygon;
    }
}
