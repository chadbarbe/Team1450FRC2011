package Robot.Subsystems;

import Robot.Commands.Drive.DriveWithJoystick;
import RobotMain.Constants;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class DriveTrain extends Subsystem {

    private RobotDrive robotDrive = new RobotDrive(IODefines.LEFT_DRIVE, IODefines.RIGHT_DRIVE);
    private Encoder leftEncoder = new Encoder(IODefines.LEFT_DRIVE_ENCODER_A,IODefines.LEFT_DRIVE_ENCODER_B);
    private Encoder rightEncoder = new Encoder(IODefines.RIGHT_DRIVE_ENCODER_A,IODefines.RIGHT_DRIVE_ENCODER_B);

    public DriveTrain() {
        rightEncoder.setReverseDirection(true);
        rightEncoder.setDistancePerPulse(Constants.Drives.distancePerPulse);
        leftEncoder.setDistancePerPulse(Constants.Drives.distancePerPulse);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick());
    }

    public void arcadeDrive(double throttle, double rotation) {
        robotDrive.arcadeDrive(throttle,rotation);
    }
}
