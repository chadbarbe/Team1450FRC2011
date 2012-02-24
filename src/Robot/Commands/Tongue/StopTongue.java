package Robot.Commands.Tongue;

import Robot.Commands.CommandBase;

/**
 * Stop the tongue
 */
public class StopTongue extends CommandBase {

    public StopTongue() {
        requires(tongue);
    }

    protected void initialize() {
    }

    protected void execute() {
        tongue.stop();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
