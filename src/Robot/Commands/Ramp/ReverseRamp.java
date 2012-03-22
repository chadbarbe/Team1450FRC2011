package Robot.Commands.Ramp;

import Robot.Commands.CommandBase;

/**
 */
public class ReverseRamp extends CommandBase {

    public ReverseRamp() {
        requires(ramp);
    }

    protected void initialize() {
    }

    protected void execute() {
        ramp.reverse();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
