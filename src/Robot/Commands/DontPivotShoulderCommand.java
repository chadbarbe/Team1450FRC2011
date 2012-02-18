package Robot.Commands;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class DontPivotShoulderCommand extends CommandBase {

    public DontPivotShoulderCommand() {
        requires(shoulder);
    }

    protected void initialize() {
    }

    protected void execute() {
        shoulder.dontPivot();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
