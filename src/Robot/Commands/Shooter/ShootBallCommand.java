package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;
import edu.wpi.first.wpilibj.command.Command;

/**
 */
public class ShootBallCommand extends CommandBase {

    public ShootBallCommand() {
        requires(trigger);
        requires(ramp);
        setInterruptible(true);
    }

    protected void initialize() {
    }

    protected void execute() {
        trigger.triggerOn();
        ramp.on();
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
