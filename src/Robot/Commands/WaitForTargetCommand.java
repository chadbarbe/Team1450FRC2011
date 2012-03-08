package Robot.Commands;

/**
 */
public class WaitForTargetCommand extends CommandBase {

    public Target target;

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
}
