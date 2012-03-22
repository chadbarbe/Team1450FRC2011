package Robot.Commands.Trigger;

import Robot.Commands.CommandBase;

/**
 */
public class ReverseTrigger extends CommandBase {
    public ReverseTrigger() {
        requires(trigger);
    }

    protected void initialize() {
    }

    protected void execute() {
        trigger.reverse();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        trigger.off();
    }

    protected void interrupted() {
        end();
    }
}
