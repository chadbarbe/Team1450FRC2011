import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.wpijavacv.*;
import edu.wpi.first.wpilibj.networking.NetworkListener;
import edu.wpi.first.wpilibj.networking.NetworkTable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The camera extension will do the image processing to detect rectangles.
 * It will also be used on the driver station to display a HUD.
 */
public class CameraExtension extends WPICameraExtension {

    public static double HUE_MAX_THRESHOLD = .1176;
    public static double SATURATION_MIN_THRESHOLD = .17;
    public static double VALUE_MIN_THRESHOLD = .8;
    private boolean detectRectangles = false;


    public static int doubleToInt(double val) {
        return (int) (val * 255);
    }
    public static double intToDouble(int value) {
        return (double) value / 255.0;
    }

    final PolygonFinder polygonFinder;

    public CameraExtension() {
        polygonFinder = new PolygonFinder();
    }

    void detectRectangles(boolean _detectRectangles) {
        detectRectangles = _detectRectangles;
    }

    @Override
    public void init() {
        super.init();
        final NetworkTable table = NetworkTable.getTable("TARGET");
        table.addListener("detectRectangles", new NetworkListener() {
            @Override
            public void valueChanged(String s, Object o) {
                detectRectangles = table.getBoolean("detectRectangles");
                System.out.println("Detect Rectangles = " + detectRectangles);
            }

            @Override
            public void valueConfirmed(String s, Object o) {
            }
        });
        {
//            final DefaultBoundedRangeModel brm = new DefaultBoundedRangeModel(doubleToInt(HUE_MAX_THRESHOLD), 0, 0, 255);
//            brm.addChangeListener(new ChangeListener() {
//                @Override
//                public void stateChanged(ChangeEvent e) {
//                    HUE_MAX_THRESHOLD = intToDouble(brm.getValue());
//                    System.out.println("Hue Thresh: " + HUE_MAX_THRESHOLD);
//                }
//            });
//            JSlider hueMaxSlider = new JSlider(brm);
//            JLabel label = new JLabel("Hue");
//            label.setLabelFor(hueMaxSlider);
//            add(label);
//            add(hueMaxSlider);
//
//            final DefaultBoundedRangeModel satModel = new DefaultBoundedRangeModel(doubleToInt(SATURATION_MIN_THRESHOLD), 0, 0, 255);
//            final JSlider satMinSlider = new JSlider(satModel);
//            satModel.addChangeListener(new ChangeListener() {
//                @Override
//                public void stateChanged(ChangeEvent e) {
//                    SATURATION_MIN_THRESHOLD = intToDouble(satModel.getValue());
//                    System.out.println("Sat Thresh: " + SATURATION_MIN_THRESHOLD);
//                }
//            });
//            label = new JLabel("Sat");
//            label.setLabelFor(satMinSlider);
//            add(label);
//            add(satMinSlider);
//
//            final DefaultBoundedRangeModel valModel = new DefaultBoundedRangeModel(doubleToInt(VALUE_MIN_THRESHOLD), 0, 0, 255);
//            JSlider valMinSlider = new JSlider(valModel);
//            valModel.addChangeListener(new ChangeListener() {
//                @Override
//                public void stateChanged(ChangeEvent e) {
//                    VALUE_MIN_THRESHOLD = intToDouble(valModel.getValue());
//                    System.out.println("Val Thresh: " + VALUE_MIN_THRESHOLD);
//                }
//            });
//            label = new JLabel("Val");
//            label.setLabelFor(valMinSlider);
//            add(label);
//            add(valMinSlider);
        }
    }

    /**
     * This method is called to process a new image from the camera.
     *
     * @param rawImage the original image
     * @return the processed image
     */
    @Override
    public WPIImage processImage(WPIColorImage rawImage) {
        if (!detectRectangles) return rawImage;
        BufferedImage bufferedImage = rawImage.getBufferedImage();
        BufferedImage outputImage = rawImage.getBufferedImage(); //new BufferedImage(rawImage.getWidth(),rawImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        WPIBinaryImage binaryImage = threshold(bufferedImage, outputImage);

        List<Polygon> rectangles = detectRectangles(outputImage, binaryImage);
        binaryImage.dispose();
        Polygon targetPoly = null;
        for (Polygon rectangle : rectangles) {
            if (targetPoly == null || rectangle.getBounds().getY() < targetPoly.getBounds().getY()) {
                targetPoly = rectangle;
            }
        }
        if (targetPoly != null) {
            Target target = createTarget(targetPoly);
            NetworkTable networkTable = NetworkTable.getTable("TARGET");
            target.putData(networkTable);
        }
        System.out.println("------------------");
        rawImage.dispose();
        return new WPIImage(outputImage);
    }

    private Target createTarget(Polygon targetPoly) {
        Target target = new Target();
        target.posX = (int) targetPoly.getBounds().getX();
        target.posY = (int) targetPoly.getBounds().getY();
        target.angleX = calculateAngle(targetPoly);
        target.distance = calculateDistance(targetPoly, target.angleX);
        target.timestamp = System.currentTimeMillis();
        return target;
    }

    private List<Polygon> detectRectangles(BufferedImage outputImage, WPIBinaryImage binaryImage) {
        polygonFinder.clear();
        List<Polygon> polygons = polygonFinder.findPolygons(binaryImage);

        List<Polygon> independentPolygons = new ArrayList<Polygon>();
        for (Polygon polygon : polygons) {
            boolean insideOtherPoly = false;
            for (Polygon otherPolygon : polygons) {
                insideOtherPoly = polygon.contains(otherPolygon.getBounds2D());
                if (insideOtherPoly) {
//                    System.out.println("detected poly inside other");
                    break;
                }
            }
            Graphics graphics = outputImage.getGraphics();

            if (insideOtherPoly) {
                independentPolygons.add(polygon);
                graphics.setColor(Color.RED);
                graphics.drawPolygon(polygon);
                double angle = calculateAngle(polygon);
                double distance = calculateDistance(polygon, angle);
//                double offsetY = calculateOffset(polygon,distance);
                double x = polygon.getBounds().getX();
                double y = polygon.getBounds().getY();
                graphics.setColor(Color.BLACK);
                graphics.fillRoundRect((int) x - 5, (int) y - 20, (int) polygon.getBounds().getWidth(), 20, 5, 1);
                graphics.setColor(Color.YELLOW);
                graphics.setFont(new Font("Arial", Font.BOLD, 16));
                graphics.drawString(String.format("%.2f ft, %.2f deg", distance, angle), (int) x, (int) y - 5);
            } else {
                graphics.setColor(Color.GREEN);
                graphics.drawPolygon(polygon);
            }
        }
        return independentPolygons;
    }

    private double calculateOffset(Polygon polygon, double distance) {
        double offset = 0;
        double centerY_px = polygon.getBounds2D().getCenterY();
        double height_px = polygon.getBounds2D().getHeight();
        final double height = 480;
        return offset;
    }

    private WPIBinaryImage threshold(BufferedImage bufferedImage, BufferedImage outputImage) {
        BufferedImage tempImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                int rgb = bufferedImage.getRGB(i,j);
                Color color = new Color(rgb);
                float[] hsv = new float[3];
                Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),hsv);
                if (hsv[0] < HUE_MAX_THRESHOLD && hsv[1] > SATURATION_MIN_THRESHOLD && hsv[2] > VALUE_MIN_THRESHOLD) {
                   tempImage.setRGB(i, j, 0xffffff);
                } else {
                   tempImage.setRGB(i, j, 0x000000);
                }
            }
        }
        // I only do this since I can't directly create a binary image; it has no public constructor.
        WPIColorImage threshholdImage = new WPIColorImage(tempImage);
        WPIBinaryImage binaryImage = threshholdImage.getRedChannel().getThreshold(0);
        threshholdImage.dispose();
        return binaryImage;
    }

    private static final double fovInDegrees = 51.0 / 2.0; // we only consider half the fov to make the math simpler
    private static final double fovInRadians = fovInDegrees * Math.PI / 180.0;
    private static final double cameraImageWidthInPx = 640.0;

    private double calculateDistance(Polygon polygon, double angle_deg) {
        // the fov of the camera is 56 deg.
        // the resolution of the camera is 640x480 px
        // the rectangle target width is 2 ft
        // the rest is math and geometry!

        // target width in px
        int width = (int) polygon.getBounds2D().getWidth();
        double distance_ft = cameraImageWidthInPx / (width * Math.tan(fovInRadians));
        double tangent_ft = distance_ft * Math.cos(angle_deg * Math.PI / 180.0);
        System.out.printf("distance = %.2f ft\n", tangent_ft);
        return tangent_ft;
    }

    // It's just geometry and math!
    private double calculateAngle(Polygon polygon) {
        int centerX = (int) polygon.getBounds2D().getCenterX();
        double offset_px = centerX - 640 / 2;
        double targetWidth_px = polygon.getBounds2D().getWidth();
        double targetAngle_rad = Math.atan(offset_px / targetWidth_px * Math.tan(fovInRadians));
        double targetAngle_deg = targetAngle_rad * 180.0 / Math.PI;
        System.out.printf("angle = %.2f deg\n",targetAngle_deg);
        return targetAngle_deg;
    }
}
