package Robot.Commands.Tongue;

import Robot.Commands.CommandBase;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Stop the tongue
 */
public class ShutUpTongue extends CommandBase {

    public ShutUpTongue() {
        requires(tongue);
    }

    protected void initialize() {
    }

    protected void execute() {
        tongue.shutUp();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
