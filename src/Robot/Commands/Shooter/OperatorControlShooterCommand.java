package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;

/**
 */
public class OperatorControlShooterCommand  extends CommandBase {

    public OperatorControlShooterCommand() {
        requires(shooter);
        requires(trigger);
        requires(arm);
        setInterruptible(true);
    }

    protected void initialize() {
    }

    protected void execute() {
        shooter.operatorControlThrottle(oi.getShooterThrottle());
        if (oi.getTrigger()) {
            boolean reversed = oi.isInReverseMode();
            if (reversed) {
                trigger.reverse();
            } else {
                trigger.on();
            }
        } else {
           trigger.off();
        }
        arm.operatorControl(oi.getArmPower());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.dontSpin();
        trigger.off();
        arm.off();
    }

    protected void interrupted() {
        end();
    }
}
