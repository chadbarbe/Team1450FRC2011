package Robot.Commands.Shoulder;

import Robot.Commands.CommandBase;

/**
 */
public class OperatorControlShooterCommand  extends CommandBase {

    public OperatorControlShooterCommand() {
        requires(shoulder);
        requires(shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
        double rotation = oi.getShoulderRotation();
        double arc = oi.getArc();
        shoulder.operatorControl(rotation);
        shooter.setArc(arc);
        shooter.throttle(oi.getShooterThrottle());
        if (oi.getTrigger()) {
            shooter.triggerOn();
        } else {
            shooter.triggerOff();
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
