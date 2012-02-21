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
