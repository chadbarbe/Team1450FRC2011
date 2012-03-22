package Robot.Commands.Trigger;

import Robot.Commands.CommandBase;

/**
 */
public class StopTriggerCommand extends CommandBase {

    public StopTriggerCommand() {
        requires(trigger);
    }

    protected void initialize() {
    }

    protected void execute() {
        trigger.off();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
