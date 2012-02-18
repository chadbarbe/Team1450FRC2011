package Robot.Commands;

/**
 */
public class ShootWithJoystickCommand extends CommandBase {

    public ShootWithJoystickCommand() {
        requires(shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
        shooter.throttle(oi.getShooterThrottle());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
