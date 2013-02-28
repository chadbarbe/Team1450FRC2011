/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.frc.team1450;

import static com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import com.googlecode.javacv.cpp.opencv_imgproc.CvMoments;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author parallels
 */

public class ASquare {
    
    public static class centerComparator implements Comparator<ASquare> {

        @Override
        public int compare(ASquare square1, ASquare square2) {
            Integer centerY1 = square1.GetCenter().y();
            Integer centerY2 = square2.GetCenter().y();
            return centerY1.compareTo(centerY2);
        }
    }
    
    private CvSeq approx;
    private ArrayList<APoint> points;
    private ALine top;
    private ALine bottom;
    private ALine left;
    private ALine right;
    private APoint center;
    
    public ASquare(CvSeq _approx) {
        approx = _approx;
        points = new ArrayList<APoint>();
        int total = approx.total();
        CvPoint pts = new CvPoint(4);
        cvCvtSeqToArray(approx, pts, CV_WHOLE_SEQ);
        for (int i = 0; i < pts.capacity(); i++) {
            CvPoint myPoint = new CvPoint(pts.position(i));
            points.add(new APoint((new CvPoint(pts.position(i)).x()), (new CvPoint(pts.position(i)).y())));
        }
        
        System.out.println("points.size = " + points.size());
        
        CvMoments moments = new CvMoments();
        cvMoments(approx, moments, 1);
        double moment10 = moments.m10();
        double moment01 = moments.m01();
        double moment00 = moments.m00();

        int x = (int) Math.floor(moment10 / moment00);
        int y = (int) Math.floor(moment01 / moment00);
        
        center = new APoint(x,y);
        
        findSides();
    }

    public CvPoint GetCenter() {
        return new CvPoint(center.x(), center.y());
    }
    
    public ALine getTop() {
        return top;
    }
    
    public ALine getBottom() {
        return bottom;
    }
    
    public ALine getLeft() {
        return left;
    }
    
    public ALine getRight() {
        return right;
    }
    
    private ArrayList<APoint> getXSortedPoints() {
        ArrayList<APoint> myPoints = new ArrayList(points);       
        Collections.sort(myPoints, new APoint.xComparator());
        return myPoints;
    }
    
    private ArrayList<APoint> getYSortedPoints() {
        ArrayList<APoint> myPoints = new ArrayList(points);
        Collections.sort(myPoints, new APoint.yComparator());
        return myPoints;
    }
    
    private void findSides() {
        ArrayList<APoint> ySorted = getYSortedPoints();
        ArrayList<APoint> xSorted = getXSortedPoints();
        
        top = new ALine(ySorted.get(0), ySorted.get(1));
        bottom = new ALine(ySorted.get(2), ySorted.get(3));
        left = new ALine(xSorted.get(0), xSorted.get(1));
        right = new ALine(xSorted.get(2), xSorted.get(3));
    }
}
