package Robot.Commands;

/**
 */
public class DefaultShooterCommand extends CommandBase {

    public DefaultShooterCommand() {
        requires(shooter);
    }

    protected void initialize() {
        shooter.resetSpeedSensor();
    }

    protected void execute() {
        shooter.dontSpin();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
