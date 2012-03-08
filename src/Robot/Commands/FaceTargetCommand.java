package Robot.Commands;

import Robot.Utils.Target;
import Robot.Utils.TargetProvider;

public class FaceTargetCommand extends CommandBase implements TargetProvider {

    private Target target;

    private static final double ANGLE_THRESHOLD_DEG = 2.0;

    public FaceTargetCommand() {
        requires(driveTrain);
    }

    @Override
    protected void initialize() {
        camera.resetTarget();
    }

    @Override
    protected void execute() {
        if (camera.hasNewTarget()) {
            target = camera.getTarget();
            camera.resetTarget();
        }
        double mag = 0.25;
        if (facingTarget()) {
            // don't turn
            driveTrain.drive(0.0, 0.0);
        } else if (target.angleX < 0) {
            // turn left
            driveTrain.drive(-mag, mag);
        } else {
            // turn right
            driveTrain.drive(mag, -mag);
        }
    }

    private boolean facingTarget() {
        return Math.abs(target.angleX) <= ANGLE_THRESHOLD_DEG;
    }

    @Override
    protected boolean isFinished() {
        return facingTarget();
    }

    @Override
    protected void end() {
        driveTrain.drive(0.0, 0.0);
    }

    @Override
    protected void interrupted() {
        // always stop the drives if autonomous is interrupted
        end();
    }

    public Target getTarget() {
        return target;
    }
}
