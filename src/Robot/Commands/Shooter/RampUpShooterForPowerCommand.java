package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;

public class RampUpShooterForPowerCommand extends CommandBase {

    private final double power;

    public RampUpShooterForPowerCommand(double power, int timeout) {
        this.power = power;
        setTimeout(timeout);
        setInterruptible(true);
    }

    protected void initialize() {
    }

    protected void execute() {
        shooter.throttle(power);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
