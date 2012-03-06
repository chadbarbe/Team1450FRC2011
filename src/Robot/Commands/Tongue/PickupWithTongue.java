package Robot.Commands.Tongue;

import Robot.Commands.CommandBase;

/**
 */
public class PickupWithTongue extends CommandBase {

    public PickupWithTongue() {
        // Use requires() here to declare subsystem dependencies
        requires(tongue);
        requires(ramp);
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        tongue.pickUp();
        if (trigger.ballReady()) {
            ramp.off();
        } else {
            ramp.on();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        tongue.stop();
        ramp.off();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}

