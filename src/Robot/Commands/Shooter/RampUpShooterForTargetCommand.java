package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;
import Robot.Utils.Target;
import Robot.Utils.TargetProvider;

/**
 */
public class RampUpShooterForTargetCommand extends CommandBase {

    private static final long RAMP_UP_TIME_MS = 1000L;
    private long startTime;
    private Target target;
    private static final double ANGLE_THRESHOLD = 5.0; // degrees
    private boolean lookingAtTarget = false;
    private final TargetProvider targetProvider;

    public RampUpShooterForTargetCommand(TargetProvider targetProvider) {
        requires(shooter);
        this.targetProvider = targetProvider;
    }

    protected void initialize() {
        target = targetProvider.getTarget();
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
