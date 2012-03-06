package Robot.Commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 */
public class WaitForTargetCommand extends CommandBase {
   public static Target target;

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
