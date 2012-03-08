package Robot.Commands.Trigger;

import Robot.Commands.CommandBase;

public class PullTriggerCommand extends CommandBase {

    public PullTriggerCommand() {
        requires(trigger);
        setInterruptible(true);
    }

    protected void initialize() {
    }

    protected void execute() {
        trigger.triggerOn();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
