package Robot.Commands.Shoulder;

import Robot.Commands.CommandBase;

/**
 */
public class OperatorControlShooterCommand  extends CommandBase {

    public OperatorControlShooterCommand() {
        requires(waist);
        requires(shooter);
        requires(trigger);
    }

    protected void initialize() {
    }

    protected void execute() {
        double rotation = oi.getShoulderRotation();
        double arc = oi.getArc();
        waist.operatorControl(rotation);
        shooter.setArc(arc);
        shooter.throttle(oi.getShooterThrottle());
        if (oi.getTrigger()) {
           trigger.triggerOn();
        } else {
           trigger.triggerOff();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
