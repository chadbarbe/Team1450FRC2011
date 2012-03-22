package Robot.Commands.Tongue;

import Robot.Commands.CommandBase;

/**
 */
public class ReverseTongue extends CommandBase {

    public ReverseTongue() {
        requires(tongue);
    }

    protected void initialize() {
    }

    protected void execute() {
        tongue.reverse();
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
