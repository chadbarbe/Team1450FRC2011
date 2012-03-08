package Robot.Commands;

/**
 */
public class RampUpShooterForTargetCommand extends CommandBase {

    private static final long RAMP_UP_TIME_MS = 1000L;
    private long startTime;
    private Target target;
    private static final double ANGLE_THRESHOLD = 5.0; // degrees
    private boolean lookingAtTarget = false;
    private final WaitForTargetCommand waitForTargetCommand;

    public RampUpShooterForTargetCommand(WaitForTargetCommand waitForTargetCommand) {
        requires(shooter);
        this.waitForTargetCommand = waitForTargetCommand;
    }

    protected void initialize() {
        target = waitForTargetCommand.target;
        if (target != null) {
            lookingAtTarget = (Math.abs(target.angleX) < ANGLE_THRESHOLD);
        }
        startTime = System.currentTimeMillis();
    }

    protected void execute() {
        if (lookingAtTarget) {
            shooter.throttle(0.5);
        } else {
            shooter.dontSpin();
        }
    }

    protected boolean isFinished() {
        // if there is no target never finish
        return lookingAtTarget && ((System.currentTimeMillis() - startTime) >= RAMP_UP_TIME_MS);
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
