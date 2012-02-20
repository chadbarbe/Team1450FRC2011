package Robot.Commands.Camera;

import Robot.Commands.CommandBase;

/**
 */
public class CameraPickupCommand extends CommandBase {

    public CameraPickupCommand() {
        requires(camera);
    }

    protected void initialize() {
    }

    protected void execute() {
        camera.lookAtBallPickup();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
