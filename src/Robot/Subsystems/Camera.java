package Robot.Subsystems;

import Robot.Commands.Camera.CameraPickupCommand;
import Robot.Commands.Camera.CameraTargetCommand;
import Robot.Commands.CenterCameraCommand;
import Robot.Devices.CameraSystem;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.*;

/**
 */
public class Camera extends Subsystem {

//    private CameraSystem cameraSystem = new CameraSystem();
    private Servo cameraZServo = new Servo(IODefines.CAMERA_Z_SERVO);

    public Camera() {
//        cameraSystem.initialize();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new CameraPickupCommand());
    }

    public void centerCamera() {
        cameraZServo.setAngle(90);
    }

    public void lookAtBallPickup() {
        cameraZServo.setAngle(90-15);
    }

    public void lookAtBasket() {
        cameraZServo.setAngle(90+15);
    }

//    public AxisCamera getCamera() {
//        return cameraSystem.getCamera();
//    }
//
//    public void processImage() {
//        cameraSystem.processImage();
//    }
}
