package RobotOld.Devices;

import Robot.Utils.Threading;
import RobotMain.Constants;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The camera system includes the camera and a couple of servos to aim the camera.
 */
public class CameraSystem extends AbstractRobotDevice {

    private final static double EXPECTED_ASPECT_RATIO = 24.0 / 18.0;

    private final AxisCamera camera = AxisCamera.getInstance();
    private CriteriaCollection criteriaCollection = new CriteriaCollection();
    private ParticleAnalysisReport highParticle = null;

    public void initialize() {
        criteriaCollection.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH,30,400,false);
        criteriaCollection.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT,40,400,false);
        Threading.runInLoop(Constants.LimitSwitches.loopTime, new CameraLoop(), "Camera");
    }


   public void processImage(ColorImage image) throws NIVisionException {
        BinaryImage threshholdImage = null;
        BinaryImage bigObjectsImage = null;
        BinaryImage convexHullImage = null;
        BinaryImage filteredImage = null;
        try {
            threshholdImage = image.thresholdHSV(0,20,0,255,0,255);
//            threshholdImage = image.thresholdRGB(25, 255, 0, 45, 0, 47);
            bigObjectsImage = threshholdImage.removeSmallObjects(false, 2);
            convexHullImage = bigObjectsImage.convexHull(false);
            filteredImage = convexHullImage.particleFilter(criteriaCollection);

            ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();
            for (int i = 0, reportsLength = reports.length; i < reportsLength; i++) {
                ParticleAnalysisReport report = reports[i];
                // rectangle score is a number in the range [0,1]
                scoreParticle(report);
            }
        } finally {
            if (threshholdImage != null) threshholdImage.free();
            if (bigObjectsImage != null) bigObjectsImage.free();
            if (convexHullImage != null) convexHullImage.free();
            if (filteredImage != null) filteredImage.free();
        }
    }


    private void scoreParticle(ParticleAnalysisReport report) {
        double rectangleScore = report.particleArea / (report.boundingRectHeight * report.boundingRectWidth);
        double measuredAspectRatio = (double)report.boundingRectWidth / (double)report.boundingRectHeight;
        double aspectRatioScore = 1.0 - Math.abs(measuredAspectRatio - EXPECTED_ASPECT_RATIO) / measuredAspectRatio;
        if (highParticle == null || report.center_mass_y <= highParticle.center_mass_y) {
            highParticle = report;
        }
        if (rectangleScore > 0.9 && aspectRatioScore > 0.9) {
            System.out.println("RectangleScore = " + rectangleScore);
            System.out.println("AspectRatioScore = " + aspectRatioScore);
            System.out.println(report);
        }
    }

    private class CameraLoop implements Runnable {

        public void run() {
            if (camera.freshImage()) {
                ColorImage image = null;
                try {
                    image = camera.getImage();
                    processImage(image);
                } catch (NIVisionException e) {
                    e.printStackTrace();
                } catch (AxisCameraException e) {
                    e.printStackTrace();
                } finally {
                    if (image != null) try {
                        image.free();
                    } catch (NIVisionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public AxisCamera getCamera() {
        return camera;
    }

    public void processImage() {
        try {
            processImage(camera.getImage());
        } catch (NIVisionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AxisCameraException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
