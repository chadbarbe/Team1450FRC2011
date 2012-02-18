package Robot.Subsystems;

import Robot.Commands.DriveWithJoystick;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class DriveTrain extends Subsystem {

    private RobotDrive robotDrive;
    private Encoder leftEncoder = new Encoder(IODefines.LEFT_DRIVE_ENCODER_A, IODefines.LEFT_DRIVE_ENCODER_B);
    private Encoder rightEncoder = new Encoder(IODefines.RIGHT_DRIVE_ENCODER_A, IODefines.RIGHT_DRIVE_ENCODER_B);

    public DriveTrain() {
        robotDrive = new RobotDrive(new Jaguar(IODefines.LEFT_DRIVE),new Jaguar(IODefines.RIGHT_DRIVE));
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick());
    }

    public void arcadeDrive(double rotation, double throttle) {
        robotDrive.arcadeDrive(throttle,rotation);
    }
}
