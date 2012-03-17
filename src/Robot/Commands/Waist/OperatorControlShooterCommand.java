package Robot.Commands.Waist;

import Robot.Commands.CommandBase;

/**
 */
public class OperatorControlShooterCommand  extends CommandBase {

    public OperatorControlShooterCommand() {
        requires(waist);
        requires(shooter);
        requires(trigger);
        requires(arm);
        setInterruptible(true);
    }

    protected void initialize() {
    }

    protected void execute() {
        double rotation = oi.getShoulderRotation();
        waist.operatorControl(rotation);
        shooter.throttle(oi.getShooterThrottle());
        if (oi.getTrigger()) {
           trigger.triggerOn();
        } else {
           trigger.triggerOff();
        }
        arm.operatorControl(oi.getArmPower());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
