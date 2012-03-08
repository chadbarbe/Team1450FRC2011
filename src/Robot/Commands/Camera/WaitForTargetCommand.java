package Robot.Commands.Camera;

import Robot.Commands.CommandBase;
import Robot.Utils.Target;
import Robot.Utils.TargetProvider;

/**
 */
public class WaitForTargetCommand extends CommandBase implements TargetProvider {

    private Target target;

    protected void initialize() {
        target = null;
        camera.resetTarget();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        boolean newTarget = camera.hasNewTarget();
        if (newTarget) {
            target = camera.getTarget();
            System.out.println("camera has new target");
        }
        return newTarget;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

    public Target getTarget() {
        return target;
    }
}
