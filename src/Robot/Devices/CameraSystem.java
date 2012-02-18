package Robot.Devices;

import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;

/**
 * The camera system includes the camera and a couple of servos to aim the camera.
 */
public class CameraSystem extends AbstractRobotDevice {

    private final static double EXPECTED_ASPECT_RATIO = 24.0 / 18.0;

    private final AxisCamera camera = AxisCamera.getInstance();
    private Servo xyServo = new Servo(IODefines.CAMERA_XY_SERVO);
    private Servo zServo = new Servo(IODefines.CAMERA_Z_SERVO);
    private CriteriaCollection criteriaCollection = new CriteriaCollection();
    private ParticleAnalysisReport highParticle = null;

    public void initialize() {
        criteriaCollection.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH,30,400,false);
        criteriaCollection.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT,40,400,false);
    }

    private void processImage(ColorImage image) throws NIVisionException {
        BinaryImage threshholdImage = null;
        BinaryImage bigObjectsImage = null;
        BinaryImage convexHullImage = null;
        BinaryImage filteredImage = null;
        try {
            threshholdImage = image.thresholdRGB(25, 255, 0, 45, 0, 47);
            bigObjectsImage = threshholdImage.removeSmallObjects(false, 2);
            convexHullImage = bigObjectsImage.convexHull(false);
            filteredImage = convexHullImage.particleFilter(criteriaCollection);

            ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();
            for (int i = 0, reportsLength = reports.length; i < reportsLength; i++) {
                ParticleAnalysisReport report = reports[i];
                // rectangle score is a number in the range [0,1]
                scoreParticle(report);
                System.out.println(report);
            }
            System.out.println("Highest report = " + highParticle);
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
        double aspectRatioScore = 1.0 - measuredAspectRatio / (measuredAspectRatio - EXPECTED_ASPECT_RATIO);
        if (highParticle == null || report.center_mass_y <= highParticle.center_mass_y) {
            highParticle = report;
        }
        System.out.println("RectangleScore = " + rectangleScore);
        System.out.println("AspectRatioScore = " + aspectRatioScore);
    }

    private void run() throws NIVisionException, AxisCameraException {
        if (camera.freshImage()) {
            ColorImage image = null;
            try {
                image = camera.getImage();
                processImage(image);
            } finally {
                if (image != null) image.free();
            }
        }
    }
}
