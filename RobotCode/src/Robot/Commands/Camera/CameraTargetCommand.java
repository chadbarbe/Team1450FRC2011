package Robot.Commands.Camera;


import Robot.Commands.CommandBase;

/**
 */
public class CameraTargetCommand extends CommandBase {

    public CameraTargetCommand() {
        requires(camera);
    }

    protected void initialize() {
    }

    protected void execute() {
        camera.lookAtBasket();
        // get turn angle and distance to basket.
        // if angle > thresh
            // move waist by angle
            // set shooter speed
        // else
            // done
//        if (camera.getCamera().freshImage()) {
//            camera.processImage();
//        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
