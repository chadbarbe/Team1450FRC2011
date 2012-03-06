package Robot.Subsystems;

import Robot.Commands.Camera.CameraPickupCommand;
import Robot.Commands.Target;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkListener;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;

/**
 */
public class Camera extends Subsystem {

    private Servo cameraZServo = new Servo(IODefines.CAMERA_Z_SERVO);
    private final NetworkTable targetTable;
    private boolean newTarget;
    private Target target;

    public Camera() {
        targetTable = NetworkTable.getTable("TARGET");

        targetTable.addListener("distance", new NetworkListener() {
            public void valueChanged(String key, Object value) {
                System.out.println("Distance updated = " + value);
                try {
                    System.out.println("Angle = " + targetTable.getDouble("angle"));
                } catch (NetworkTableKeyNotDefined networkTableKeyNotDefined) {
                    networkTableKeyNotDefined.printStackTrace();
                }
                newTarget = true;
            }

            public void valueConfirmed(String key, Object value) {
                System.out.println("Distance confirmed " + value);
            }
        });
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

    public void resetTarget() {
        newTarget = false;
    }

    public boolean hasNewTarget() {
        return newTarget;
    }

    public Target getTarget() {
        try {
            return Target.getTarget(targetTable);
        } catch (NetworkTableKeyNotDefined e) {
            e.printStackTrace();
        }
        return null;
    }

    public void incCameraPosition() {
        cameraZServo.setAngle(cameraZServo.getAngle()+10);
    }
    public void decCameraPosition() {
        cameraZServo.setAngle(cameraZServo.getAngle()-10);
    }

    public int getPosition() {
        return (int) cameraZServo.getAngle();
    }


    public void setCameraAngle(int angle) {
        cameraZServo.setAngle(angle);
    }
}
