package Robot.Commands.Ramp;

import Robot.Commands.CommandBase;
import edu.wpi.first.wpilibj.command.Command;

/**
 */
public class RampOnCommand extends CommandBase {
    protected void initialize() {
    }

    protected void execute() {
        ramp.on();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
