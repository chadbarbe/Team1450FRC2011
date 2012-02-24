package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;

/**
 */
public class DontPivotShoulderCommand extends CommandBase {

    public DontPivotShoulderCommand() {
        requires(waist);
    }

    protected void initialize() {
    }

    protected void execute() {
        waist.dontPivot();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
