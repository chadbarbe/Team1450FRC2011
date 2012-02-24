package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;
import edu.wpi.first.wpilibj.command.Command;

/**
 */
public class ShootBallCommand extends CommandBase {

    public ShootBallCommand() {
        requires(trigger);
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
       trigger.triggerOff();
    }

    protected void interrupted() {
        System.out.println(getName() + " interuppted.");
        end();
    }
}
