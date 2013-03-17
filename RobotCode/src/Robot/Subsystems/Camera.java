package Robot.Subsystems;

import Robot.Commands.Camera.CameraPickupCommand;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Camera extends Subsystem {

    private Servo cameraZServo = new Servo(IODefines.CAMERA_Z_SERVO);

    public Camera() {
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
}
