package Robot.Commands.Drive;

import Robot.Commands.CommandBase;

/**
 */
public class DriveWithJoystick extends CommandBase {

    public DriveWithJoystick() {
        requires(driveTrain);
    }

    protected void initialize() {
    }

    protected void execute() {
        driveTrain.arcadeDrive(oi.getDriveRotation(),oi.getDriveThrottle());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
