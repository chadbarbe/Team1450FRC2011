package Robot.Commands;

import Robot.Utils.Target;
import Robot.Utils.TargetProvider;

public class FaceTargetCommand extends CommandBase implements TargetProvider {

    private Target target;

    private static final double ANGLE_THRESHOLD_DEG = 2.0;
    public static final double MAG = 0.15;

    public FaceTargetCommand() {
        requires(driveTrain);
    }

    protected void initialize() {
        camera.resetTarget();
    }

    protected void execute() {
        if (camera.hasNewTarget()) {
            target = camera.getTarget();
            camera.resetTarget();
        }
        if (facingTarget()) {
            // don't turn
            driveTrain.drive(0.0, 0.0);
        } else if (target.angleX < 0) {
            // turn left
            driveTrain.drive(-MAG, MAG);
        } else {
            // turn right
            driveTrain.drive(MAG, -MAG);
        }
    }

    private boolean facingTarget() {
        return Math.abs(target.angleX) <= ANGLE_THRESHOLD_DEG;
    }

    protected boolean isFinished() {
        return facingTarget();
    }

    protected void end() {
        driveTrain.drive(0.0, 0.0);
    }

    protected void interrupted() {
        // always stop the drives if autonomous is interrupted
        end();
    }

    public Target getTarget() {
        return target;
    }
}
