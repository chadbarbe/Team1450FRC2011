package Robot.Subsystems;

import Robot.Commands.Drive.DriveWithJoystick;
import Robot.Devices.DrivePlatform;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class DriveTrain extends Subsystem {

//    private DrivePlatform drivePlatform = new DrivePlatform();
    private RobotDrive robotDrive = new RobotDrive(IODefines.LEFT_DRIVE, IODefines.RIGHT_DRIVE);

    public DriveTrain() {
//        drivePlatform.initialize();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick());
    }

    public void arcadeDrive(double rotation, double throttle) {
//        drivePlatform.arcadeDrive(throttle,rotation);
        robotDrive.arcadeDrive(throttle,rotation);
    }
}
