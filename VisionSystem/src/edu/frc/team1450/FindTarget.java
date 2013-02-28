package edu.frc.team1450;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.googlecode.javacpp.Loader;
import static com.googlecode.javacpp.Loader.*;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_core.CvContour;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import com.googlecode.javacv.cpp.opencv_imgproc;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import com.googlecode.javacv.cpp.opencv_imgproc.IplConvKernel;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author robots
 */
public class FindTarget {

    static int[] HueLowerthresH = new int[1];
    static int[] HueUpperthresH = new int[1];
    static int thresh = 50;
    static int N = 11;
    final static String wndname = "Square Detection Demo";
    static int hueLowerR = 10;
    static int hueUpperR = 76;
    static int satLowerR = 116;
    static int satUpperR = 255;
    static int valLowerR = 130;
    static int valUpperR = 255;

// helper function:
// finds a cosine of angle between vectors
// from pt0->pt1 and from pt0->pt2
    public double angle(CvPoint pt1, CvPoint pt2, CvPoint pt0) {
        double dx1 = pt1.x() - pt0.x();
        double dy1 = pt1.y() - pt0.y();
        double dx2 = pt2.x() - pt0.x();
        double dy2 = pt2.y() - pt0.y();
        return (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10);
    }

// returns sequence of squares detected on the image.
    public ArrayList<ASquare> findSquares(final IplImage src, CvMemStorage storage) {
        ArrayList<ASquare> mySquares = new ArrayList<ASquare>();
        System.out.println("Start find squares");
        CvSeq squares = new CvContour();
        squares = cvCreateSeq(0, sizeof(CvContour.class), sizeof(CvSeq.class), storage);

        IplImage pyr = null, timg = null, gray = null, tgray;
        timg = cvCloneImage(src);

        CvSize sz = cvSize(src.width() & -2, src.height() & -2);
        tgray = cvCreateImage(sz, src.depth(), 1);
        gray = cvCreateImage(sz, src.depth(), 1);
        pyr = cvCreateImage(cvSize(sz.width() / 2, sz.height() / 2), src.depth(), src.nChannels());

        // down-scale and upscale the image to filter out the noise
        cvPyrDown(timg, pyr, CV_GAUSSIAN_5x5);
        cvPyrUp(pyr, timg, CV_GAUSSIAN_5x5);
        CvSeq contours = new CvContour();
        // request closing of the application when the image window is closed
        // show image on window
        // find squares in every color plane of the image

//        // try several threshold levels
        for (int l = 0; l < N; l++) {
//             hack: use Canny instead of zero threshold level.
//             Canny helps to catch squares with gradient shading
            if (l == 0) {
//                apply Canny. Take the upper threshold from slider
//                and set the lower to 0 (which forces edges merging)
                cvCanny(src, gray, 0, thresh, 5);
//                 dilate canny output to remove potential
//                // holes between edge segments
                cvDilate(gray, gray, null, 1);
            } else {
//                apply threshold if l!=0:
                cvThreshold(src, gray, (l + 1) * 255 / N, 255, CV_THRESH_BINARY);
            }
//            find contours and store them all as a list
            cvFindContours(gray, storage, contours, sizeof(CvContour.class), CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);

            CvSeq approx;

//            test each contour
            while (contours != null && !contours.isNull()) {
                if (contours.elem_size() > 0) {
                    approx = cvApproxPoly(contours, Loader.sizeof(CvContour.class),
                            storage, CV_POLY_APPROX_DP, cvContourPerimeter(contours) * 0.02, 0);
                    if (approx.total() == 4
                            && Math.abs(cvContourArea(approx, CV_WHOLE_SEQ, 0)) > 1000
                            && cvCheckContourConvexity(approx) != 0) {
                        double maxCosine = 0;
                        //
                        for (int j = 2; j < 5; j++) {
                            //find the maximum cosine of the angle between joint edges
                            double cosine = Math.abs(angle(new CvPoint(cvGetSeqElem(approx, j % 4)), new CvPoint(cvGetSeqElem(approx, j - 2)), new CvPoint(cvGetSeqElem(approx, j - 1))));
                            maxCosine = Math.max(maxCosine, cosine);
                        }
                        if (maxCosine < 0.2) {
                            mySquares.add(new ASquare(approx));
                            //cvSeqPush(squares, approx);
                        }
                    }
                }
                contours = contours.h_next();
            }
            contours = new CvContour();
        }
        System.out.println("End find squares");
        return mySquares;
    }

// the function draws all the squares in the image
    public void drawSquares(String filename, IplImage image, final ArrayList<ASquare> mySquares) {
        System.out.println("Start draw squares");
        Collections.sort(mySquares, new ASquare.centerComparator());

        if (!mySquares.isEmpty()) {
            ASquare square = mySquares.get(0);
            if (!mySquares.isEmpty()) {
                CvPoint center = square.GetCenter();
                cvDrawCircle(image, new CvPoint(center.x(), center.y()), 2, CvScalar.BLUE, CV_FILLED, 1, 1);
                cvDrawLine(image,
                        new CvPoint(
                        square.getTop().firstPoint().x(),
                        square.getTop().firstPoint().y()),
                        new CvPoint(
                        square.getTop().secondPoint().x(),
                        square.getTop().secondPoint().y()),
                        CvScalar.BLUE,
                        1,
                        CV_AA,
                        0);
                cvDrawLine(image,
                        new CvPoint(
                        square.getBottom().firstPoint().x(),
                        square.getBottom().firstPoint().y()),
                        new CvPoint(
                        square.getBottom().secondPoint().x(),
                        square.getBottom().secondPoint().y()),
                        CvScalar.RED,
                        1,
                        CV_AA,
                        0);
                cvDrawLine(image,
                        new CvPoint(
                        square.getLeft().firstPoint().x(),
                        square.getLeft().firstPoint().y()),
                        new CvPoint(
                        square.getLeft().secondPoint().x(),
                        square.getLeft().secondPoint().y()),
                        CvScalar.GRAY,
                        1,
                        CV_AA,
                        0);
                cvDrawLine(image,
                        new CvPoint(
                        square.getRight().firstPoint().x(),
                        square.getRight().firstPoint().y()),
                        new CvPoint(
                        square.getRight().secondPoint().x(),
                        square.getRight().secondPoint().y()),
                        CvScalar.YELLOW,
                        1,
                        CV_AA,
                        0);
            }
        }
        final CanvasFrame canvas = new CanvasFrame(wndname);

//     request closing of the application when the image window is closed
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        // show image on window
        cvSaveImage("images/chad" + filename + ".jpg", image);
        cvShowImage("image" + filename, image);
        cvWaitKey(30);
        System.out.println("End draw squares");
    }

    public void run(String filename) {
        IplImage src = cvLoadImage(filename, CV_LOAD_IMAGE_UNCHANGED);
        CvSize size = cvGetSize(src);
        IplImage bin = IplImage.create(size, 8, 1);
        IplImage hsv = IplImage.create(size, 8, 3);
        IplImage hue = IplImage.create(size, 8, 1);
        IplImage sat = IplImage.create(size, 8, 1);
        IplImage val = IplImage.create(size, 8, 1);

        opencv_imgproc.cvCvtColor(src, hsv, opencv_imgproc.CV_BGR2HSV);
        opencv_core.cvSplit(hsv, hue, sat, val, null);

        opencv_imgproc.cvThreshold(hue, bin, 60 - 15, 255, opencv_imgproc.CV_THRESH_BINARY);
        opencv_imgproc.cvThreshold(hue, hue, 60 + 15, 255, opencv_imgproc.CV_THRESH_BINARY_INV);

        // Saturation
        opencv_imgproc.cvThreshold(sat, sat, 200, 255, opencv_imgproc.CV_THRESH_BINARY);

        // Value
        opencv_imgproc.cvThreshold(val, val, 55, 255, opencv_imgproc.CV_THRESH_BINARY);

        // Combine the results to obtain our binary image which should for the most
        // part only contain pixels that we care about
        opencv_core.cvAnd(hue, bin, bin, null);
        opencv_core.cvAnd(bin, sat, bin, null);
        opencv_core.cvAnd(bin, val, bin, null);

        final int kHoleClosingIterations = 9;
        IplConvKernel morphKernel = IplConvKernel.create(3, 3, 1, 1, opencv_imgproc.CV_SHAPE_RECT, null);
        opencv_imgproc.cvMorphologyEx(bin, bin, null, morphKernel, opencv_imgproc.CV_MOP_CLOSE, kHoleClosingIterations);

        drawSquares(filename, src, findSquares(bin, cvCreateMemStorage(0)));
    }
}
