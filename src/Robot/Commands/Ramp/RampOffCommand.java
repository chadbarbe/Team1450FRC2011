package Robot.Commands.Ramp;

import Robot.Commands.CommandBase;

/**
 */
public class RampOffCommand extends CommandBase {

    public RampOffCommand() {
        requires(ramp);
    }

    protected void initialize() {
    }

    protected void execute() {
        ramp.off();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
