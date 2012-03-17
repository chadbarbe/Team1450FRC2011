import edu.wpi.first.wpijavacv.WPIColorImage;
import edu.wpi.first.wpijavacv.WPIImage;
import edu.wpi.first.wpilibj.networking.NetworkTable;
import sun.awt.image.BufImgSurfaceData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * This requires IntelliJ to build.
 */
public class CameraPreviewFrame {
    private JLabel originalImageIcon;
    private JLabel stillImageIcon;
    private JPanel main;
    private JSlider hueMaxThreshSlider;
    private JSlider satMinThreshSlider;
    private JSlider valueMinThreshSlider;
    private JPanel sliderPanel;
    private final DefaultBoundedRangeModel hueSliderModel;
    final CameraExtension cameraExtension = new CameraExtension();
    private BufferedImage originalImage;
    private final DefaultBoundedRangeModel satSliderModel;
    private final DefaultBoundedRangeModel valueSliderModel;

    public CameraPreviewFrame() {
        cameraExtension.detectRectangles(true);
        hueSliderModel = new DefaultBoundedRangeModel(CameraExtension.doubleToInt(CameraExtension.HUE_MAX_THRESHOLD), 0, 0, 255);
        hueSliderModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                CameraExtension.HUE_MAX_THRESHOLD = CameraExtension.intToDouble(hueSliderModel.getValue());
                System.out.println("New hue max thresh = " + CameraExtension.HUE_MAX_THRESHOLD);
                reprocess();
            }
        });
        hueMaxThreshSlider.setModel(hueSliderModel);
        satSliderModel = new DefaultBoundedRangeModel(CameraExtension.doubleToInt(CameraExtension.SATURATION_MIN_THRESHOLD), 0, 0, 255);
        satSliderModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                CameraExtension.SATURATION_MIN_THRESHOLD = CameraExtension.intToDouble(satSliderModel.getValue());
                System.out.println("New sat min thresh = " + CameraExtension.SATURATION_MIN_THRESHOLD);
                reprocess();
            }
        });
        satMinThreshSlider.setModel(satSliderModel);
        valueSliderModel = new DefaultBoundedRangeModel(CameraExtension.doubleToInt(CameraExtension.VALUE_MIN_THRESHOLD), 0, 0, 255);
        valueMinThreshSlider.setModel(valueSliderModel);
        valueSliderModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                CameraExtension.VALUE_MIN_THRESHOLD = CameraExtension.intToDouble(valueSliderModel.getValue());
                System.out.println("New val min thresh = " + CameraExtension.VALUE_MIN_THRESHOLD);
                reprocess();
            }
        });
    }

    private void processImage(BufferedImage image) {
        originalImage = image;
        originalImageIcon.setIcon(new ImageIcon(originalImage));
        reprocess();
    }

    private void reprocess() {
        cameraExtension.polygonFinder.clear();
        WPIImage processedImage = cameraExtension.processImage(new WPIColorImage(originalImage));
        NetworkTable table = NetworkTable.getTable("TARGET");
        Target target = new Target(table);
        originalImage.getGraphics().drawString(String.format("d=%.2f; a=%.2f",target.distance,target.angleX),
                target.posX, target.posY-5);
        stillImageIcon.setIcon(new ImageIcon(processedImage.getBufferedImage()));
    }

    public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {
        NetworkTable.setTeam(1450);
        String[] images = new String[] {
//                "RectangleRed4ft.jpg",
//                "Rectanglebelow.jpg",
//                "rectanglebelow2.jpg",
//                "rectanglebelow3.jpg",
//                "rectanglebelow4.jpg",
                "newrectangle.jpg",
                "newrectangle_titleright.jpg",
                "rectanglefar4.jpg"
        };
        for (final String image : images) {
            final CameraPreviewFrame cameraPreviewFrame = new CameraPreviewFrame();
            final BufferedImage bufferedImage = ImageIO.read(new File(image));
            cameraPreviewFrame.processImage(bufferedImage);

            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    JFrame frame = new JFrame("Preview Window: " + image);
                    frame.getContentPane().add(cameraPreviewFrame.main);
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }
            });
        }
    }
}
