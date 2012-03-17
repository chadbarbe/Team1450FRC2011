package edu.wpi.first.wpijavacv;

import com.googlecode.javacv.cpp.opencv_core;

/**
*/
public class WpiImageWrapper extends WPIImage {
    public WpiImageWrapper(WPIImage wpiImage) {
        super(wpiImage.image);
    }

    public opencv_core.IplImage getImage() {
        return image;
    }
}
