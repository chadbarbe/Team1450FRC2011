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
    private Servo cameraXYServo = new Servo(IODefines.CAMERA_XY_SERVO);
    private Servo cameraZServo = new Servo(IODefines.CAMERA_Z_SERVO);
    
    protected void initDefaultCommand() {
        setDefaultCommand(new CenterCameraCommand());
    }

    public void centerCamera() {
        cameraXYServo.setAngle(90); // look straight ahead
        cameraZServo.setAngle(100); // the camera should face slightly up
    }
}
