package Robot.Subsystems;

import Robot.Commands.CenterCameraCommand;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Camera extends Subsystem {

    private AxisCamera camera = AxisCamera.getInstance();
//    private Servo cameraXYServo = new Servo(IODefines.CAMERA_XY_SERVO);
    private Servo cameraZServo = new Servo(IODefines.CAMERA_Z_SERVO);
    
    protected void initDefaultCommand() {
        setDefaultCommand(new CenterCameraCommand());
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
}
