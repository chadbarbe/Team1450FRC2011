/*
 * Helper class to look for Polygons in images without leaking memory.
 */

import com.googlecode.javacpp.Loader;
import edu.wpi.first.wpijavacv.WPIBinaryImage;
import edu.wpi.first.wpijavacv.WPIContour;
import edu.wpi.first.wpijavacv.WPIPolygon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

/**
 * Class which is used to find polygons meeting certain criteria within a image
 * without leaking memory.
 *
 * <p>Unfortunately, it appears that the 2012 SmartDashboard release is a bit
 * unstable as far as image processing. Particularly when trying to extract
 * polygon information from bit map images. This class is an attempt to
 * bypass much of the WPI classes and go directly to the javacv (OpenCV) 
 * image processing classes for the purpose of reducing the use of memory.</p>
 *
 * <p>Unfortunately, the OpenCV library is C/C++ based which the javacv project
 * has provided a wrapper for. This is a extremely difficult API to work with as
 * examples of extracting polygons using this library are hard to come by. So,
 * this class is more or less a trial and error attempt (don't expect
 * miracles).</p>
 *
 * <p>Basic usage:</p>
 *
 * <ul>
 * <li>Construct a instance.</li>
 *
 * <li>Use the "set" methods to filter the polygons you are interested in.</li>
 *
 * <li>Invoke the clear() method to clear out any previous results and pass
 * a image into the find() method to locate polygons within the image.</li>
 *
 * <li>Use the size() and getPolygon(), getBoundingBox() methods to look
 * at the polygons that were found.</li>
 * </ul>
 *
 * @author pkb
 */
public class PolygonFinder {

    private final static double EXPECTED_ASPECT_RATIO = 24.0 / 18.0;
    public static final double RECTANGLE_SCORE_THRESHOLD = .75;
    public static final double ASPECT_SCORE_THRESHOLD = .8;

    private int minPoints;
    private int maxPoints;
    private double percentAccuracy;
    private ArrayList<Points> ppolys;
    private ArrayList<Dimension> boundingBoxes;
    private int maxFind;
    private Dimension minDimension;
    private Dimension maxDimension;
    private double minAspectRatio;
    private double maxAspectRatio;

    /**
     * Construct a new instance with some default settings.
     *
     * <p>You can adjust these default settings using the
     * various "set" methods.</p>
     *
     * <ul>
     * <li>Minimum of 4 points required for each polygon.</li>
     * <li>Maximum of 4 points required for each polygon.</li>
     * <li>Polygon bounding boxes must be larger than 10x10.</li>
     * <li>Polygon bounding boxes must be smaller than 1000000x1000000 (no limit).</li>
     * <li>Aspect ratio (width/height) must be in the range of [3/4, 6/4].</li>
     * <li>Percent accuracy paramater to cvApproxPoly() is set to 10.0.</li>
     * </ul>
     */
    public PolygonFinder() {
        minPoints = 4;
        maxPoints = 4;
        percentAccuracy = 10.0;
        ppolys = new ArrayList<Points>();
        boundingBoxes = new ArrayList<Dimension>();
        maxFind = 32;
        minDimension = new Dimension(10, 10);
        maxDimension = new Dimension(1000000, 1000000);
        minAspectRatio = (3.0 / 4.0);
        maxAspectRatio = (6.0 / 4.0);
    }

    /**
     * Clears list of polygons that have been found (you will probably want
     * to invoke this before you call find()).
     */
    public void clear() {
        ppolys.clear();
    }

    /**
     * The number of polygons we have found that have met your criteria.
     *
     * @return Count of available polygons.
     */
    public int size() {
        return ppolys.size();
    }

    /**
     * Get the points defining one of the found polygons.
     *
     * @param i Index of the polygon in the range of [0, size() - 1].
     * @return Array of points making up the polygon.
     */
    public Points getPolygon(int i) {
        return ppolys.get(i);
    }

    /**
     * Get the dimensions of the bounding box for a particular polygon.
     *
     * @param i Index of the polygon in the range of [0, size() - 1].
     * @return Dimensions of polygon's bounding box.
     */
    public Dimension getBoundingBox(int i) {
        return boundingBoxes.get(i);
    }

    /**
     * Get a iterator to go through each of the found polygons.
     *
     * @return A iterator whose element data is a array of points making up
     * the polygon.
     */
    public Iterator<Points> iterator() {
        return ppolys.iterator();
    }

    /**
     * Set the minimum number of sides (polygons found having more than this will
     * be ignored).
     *
     * @param limit The minimum number of sides each polygon should have.
     */
    public void setMinSides(int limit) {
        minPoints = limit;
    }

    /**
     * Set the maximum number of sides (polygons found having more than this will
     * be ignored).
     *
     * @param limit The maximum number of sides each polygon should have.
     */
    public void setMaxSides(int limit) {
        maxPoints = limit;
    }

    /**
     * Set the minimum and maximum sizes which each polygons bounding box must fall
     * within.
     *
     * @param minBoundingBox Any polygon whose bounding box is smaller than this
     * will be rejected.
     *
     * @param maxBoundingBox Any polygon whose bounding box is larger than this
     * will be rejected.
     */
    public void setDimensionRange(Dimension minBoundingBox, Dimension maxBoundingBox) {
        minDimension = minBoundingBox;
        maxDimension = maxBoundingBox;
    }

    /**
     * Set the aspect ratio (width/height) required for each polygons bounding box.
     * 
     * @param minAspect Any polygon whose bounding box aspect ratio is smaller than this
     * will be rejected.
     * 
     * @param maxAspect Any polygon whose bounding box aspect ratio is larger than this
     * will be rejected.
     */
    public void setAspectRatioRange(double minAspect, double maxAspect) {
        minAspectRatio = minAspect;
        maxAspectRatio = maxAspect;
    }

    /**
     * Set the maximum number of polygons we try to find each time (when to stop looking).
     *
     * @param limit We will stop looking for polygons when this limit is hit.
     */
    public void setMaxFind(int limit) {
        maxFind = limit;
    }

    /**
     * @param accuracy the percentage the perimeter of the polygon can be off
     * the perimeter of the contour. The higher the value, the fewer points the polygon
     * will have. A value of 4-5 is recommended.
     */
    public void setPolygonAccuracyFactor(double accuracy) {
        percentAccuracy = accuracy;
    }

    /**
     * Looks for polygons within a image and adds them to the collection.
     *
     * @param img Typically a binary or gray scale image.
     *
     * @return Number of polygons found.
     */
    public List<Polygon> findPolygons(WPIBinaryImage img) {
        int fnd = 0;

        WPIContour[] contours = img.findContours();
        int total = contours.length;

        for (int i = 0; (i < total) && (fnd < maxFind); i++) {
            WPIContour c = contours[i];
            int width = c.getWidth();
            int height = c.getHeight();
            WPIPolygon wpiPolygon = c.approxPolygon(percentAccuracy);
            int area = wpiPolygon.getArea();
            WPIPolygon poly = wpiPolygon;

            if (poly != null) {
                int n = poly.getNumVertices();
                if ((n >= minPoints) && (n <= maxPoints)) {
                    double rectangleScore = (double) area / (width * height);
                    double measuredAspectRatio = (double)poly.getWidth() / (double)poly.getHeight();
                    double aspectRatioScore = 1.0 - Math.abs(measuredAspectRatio - EXPECTED_ASPECT_RATIO) / measuredAspectRatio;
                    if (rectangleScore > RECTANGLE_SCORE_THRESHOLD && aspectRatioScore > ASPECT_SCORE_THRESHOLD) {
                        ppolys.add(Points.fromWPIPolygon(poly));
                        boundingBoxes.add(new Dimension(width, height));
                        fnd++;
                    } else {
//                        System.out.println("Ignoring: " + rectangleScore + ", " + aspectRatioScore);
                    }
                }
            }
        }
        List<Polygon> polygonList = new ArrayList<Polygon>(ppolys.size());
        for (Points points : ppolys) {
            polygonList.add(points.toPolygon());
        }
        return polygonList;
    }

    /**
     * Looks for polygons within a image and adds them to the collection.
     *
     * @param img Typically a binary or gray scale image.
     *
     * @return Number of polygons found.
     */
    public int findPolygons(BufferedImage img) {
        return findPolygons(IplImage.createFrom(img));
    }

    /**
     * Looks for polygons within a image and adds them to the collection.
     *
     * @param iimg Typically a binary or gray scale image.
     *
     * @return Number of polygons found.
     */
    public int findPolygons(IplImage iimg) {
        int fnd = 0;

        // javacv crud
        CvMemStorage buf = CvMemStorage.create();
        CvSeq contours = new CvSeq();

        // From findContours() in WPIBinaryImage.java
        //int total = cvFindContours(iimg, buf, contours,
        //        256, CV_RETR_LIST, CV_CHAIN_APPROX_TC89_KCOS);

        int total = cvFindContours(iimg, buf, contours,
                Loader.sizeof(CvContour.class), 0, CV_CHAIN_APPROX_SIMPLE);

        if (total > 0) {
            while ((contours != null) && !contours.isNull() && (fnd < maxFind)) {

                if (contours.elem_size() > 0) {
                    CvSeq poly = null;

                    CvRect rect = cvBoundingRect(contours, 0);
                    int width = rect.width();
                    int height = rect.height();

                    CvMemStorage pbuf = CvMemStorage.create();
                    if (checkDimensions(width, height)) {
                        poly = cvApproxPoly(contours, contours.header_size(),
                                pbuf, CV_POLY_APPROX_DP, this.percentAccuracy, 0);
                    }

                    if (poly != null) {
                        int n = poly.total();
                        if ((n >= minPoints) && (n <= maxPoints)) {
                            ppolys.add(Points.fromCvPoints(poly));
                            boundingBoxes.add(new Dimension(width, height));
                            fnd++;
                        }
                    }
                }
                contours = contours.h_next();
            }
        }

        // This should be done automatically when storage objects are finalized
        //buf.release();
        return fnd;
    }

    /**
     * Verify that width and height of a polygon bounding box meet the min/max and
     * aspect ratio requirements.
     *
     * @param width Pixel width of bounding box.
     * @param height Pixel height of bounding box.
     * @return true if width and height meet the configured requirements.
     */
    public boolean checkDimensions(int width, int height) {
        // Make quick dimension check
        boolean ok =
                (width >= minDimension.width)
                && (width <= maxDimension.width)
                && (height >= minDimension.height)
                && (height <= maxDimension.height);

        if (ok && (height > 0)) {
            // Dimension was OK and we have some height, now check aspect ratio
            double aspect = width;
            aspect /= height;
            ok = (aspect >= minAspectRatio) && (aspect <= maxAspectRatio);
        }
        return ok;
    }

    /**
     * Generate a diagnostic string representation of the polygons found.
     *
     * @return Output string for debugging purposes.
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(8096);
        int n = ppolys.size();
        out.append("PolygonFinder: Found ");
        out.append(n);
        out.append(" polygons:");
        for (int i = 0; i < n; i++) {
            out.append("\n  ");
            out.append(i);
            out.append(": bounds: ");
            Dimension bounds = getBoundingBox(i);
            out.append(bounds.width);
            out.append("x");
            out.append(bounds.height);
            out.append(" (ratio ");
            out.append((double) bounds.width / (double) bounds.height);
            out.append("):\n    ");
            //out.append(pointsToString(getPolygon(i)));
            out.append(ppolys.get(i).toString());
        }
        return out.toString();
    }

    /**
     * Draws all of the polygons found using the current graphics context.
     *
     * @param g Graphics context to use for drawing (getGraphics()
     * from a BufferedImage).
     */
    public void drawPolygonOutlines(Graphics g) {
        int n = size();
        for (int i = 0; i < n; i++) {
            getPolygon(i).drawPolygonOutline(g);
        }
    }
}
