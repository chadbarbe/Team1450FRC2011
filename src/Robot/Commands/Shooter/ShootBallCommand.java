package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;

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
        trigger.on();
        ramp.on();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
       trigger.off();
    }

    protected void interrupted() {
        System.out.println(getName() + " interuppted.");
        end();
    }
}
