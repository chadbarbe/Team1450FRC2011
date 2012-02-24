package Robot.Commands.Tongue;

import Robot.Commands.CommandBase;

/**
 */
public class PickupWithTongue extends CommandBase {

    public PickupWithTongue() {
        // Use requires() here to declare subsystem dependencies
        requires(tongue);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        tongue.pickUp();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        tongue.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}

