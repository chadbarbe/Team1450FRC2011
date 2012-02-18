package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;
import edu.wpi.first.wpilibj.command.Command;

/**
 */
public class ShootBallCommand extends CommandBase {

    public ShootBallCommand() {
        requires(shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
        shooter.triggerOn();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.triggerOff();
    }

    protected void interrupted() {
        end();
    }
}
