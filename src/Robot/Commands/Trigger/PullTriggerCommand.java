package Robot.Commands.Trigger;

import Robot.Commands.CommandBase;

public class PullTriggerCommand extends CommandBase {

    public PullTriggerCommand() {
        requires(trigger);
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        trigger.triggerOn();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
