/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpijavacv;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc;
import java.util.ArrayList;

/**
 *
 * @author jrussell
 */
public class DaisyExtensions
{
    public static CvSeq getCvSeq(WPIContour contour)
    {
        return contour.getCVSeq();
    }

    public static WPIContour makeWPIContour(CvSeq seq)
    {
        return new WPIContour(seq);
    }

    public static WPIGrayscaleImage makeWPIGrayscaleImage(IplImage arr)
    {
        IplImage tempImage = IplImage.create(arr.cvSize(), arr.depth(), 1);
        opencv_core.cvCopy(arr, tempImage);
        return new WPIGrayscaleImage(tempImage);
    }

    public static WPIColorImage makeWPIColorImage(IplImage arr)
    {
        IplImage tempImage = IplImage.create(arr.cvSize(), arr.depth(), 1);
        opencv_core.cvCopy(arr, tempImage);
        return new WPIColorImage(tempImage);
    }

    public static WPIBinaryImage makeWPIBinaryImage(IplImage arr)
    {
        IplImage tempImage = IplImage.create(arr.cvSize(), arr.depth(), 1);
        opencv_core.cvCopy(arr, tempImage);
        return new WPIBinaryImage(tempImage);
    }

    public static IplImage getIplImage(WPIImage image)
    {
        return image.image;
    }

    private static CvMemStorage storage;
    private static ArrayList<CvSeq> thingsToDispose;

    public static void init()
    {
        storage = CvMemStorage.create();
    }

    public static WPIContour[] findConvexContours(WPIBinaryImage image)
    {
        image.validateDisposed();

        IplImage tempImage = IplImage.create(image.image.cvSize(), image.image.depth(), 1);

        opencv_core.cvCopy(image.image, tempImage);

        CvSeq contours = new CvSeq();
        opencv_imgproc.cvFindContours(tempImage, storage, contours, 256, opencv_imgproc.CV_RETR_LIST, opencv_imgproc.CV_CHAIN_APPROX_TC89_KCOS);
        ArrayList<WPIContour> results = new ArrayList();
        while (!WPIDisposable.isNull(contours)) {
            CvSeq convexContour = opencv_imgproc.cvConvexHull2(contours, storage, opencv_imgproc.CV_CLOCKWISE, 1);
            WPIContour contour = new WPIContour(opencv_core.cvCloneSeq(convexContour, storage));
            results.add(contour);
            contours = contours.h_next();
        }

        tempImage.release();
        WPIContour[] array = new WPIContour[results.size()];
        return results.toArray(array);
    }

    public static void releaseMemory()
    {
        opencv_core.cvClearMemStorage(storage);
    }
    
}
