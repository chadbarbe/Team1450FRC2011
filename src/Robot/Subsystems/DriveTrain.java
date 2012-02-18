package Robot.Subsystems;

import Robot.Commands.Drive.DriveWithJoystick;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class DriveTrain extends Subsystem {

    private RobotDrive robotDrive;
    private Encoder leftEncoder = new Encoder(IODefines.LEFT_DRIVE_ENCODER_A, IODefines.LEFT_DRIVE_ENCODER_B);
    private Encoder rightEncoder = new Encoder(IODefines.RIGHT_DRIVE_ENCODER_A, IODefines.RIGHT_DRIVE_ENCODER_B);

    public DriveTrain() {
        Jaguar leftMotor = new Jaguar(IODefines.LEFT_DRIVE);
        Jaguar rightMotor = new Jaguar(IODefines.RIGHT_DRIVE);
        robotDrive = new RobotDrive(leftMotor, rightMotor);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick());
    }

    public void arcadeDrive(double rotation, double throttle) {
        robotDrive.arcadeDrive(throttle, rotation);
    }

    public void arcadeDrive(Joystick driveJoystick) {
        robotDrive.arcadeDrive(driveJoystick);
    }
}
