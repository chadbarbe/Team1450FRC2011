package Robot.Commands;

/**
 */
public class CenterCameraCommand extends CommandBase {

    public CenterCameraCommand() {
        requires(camera);
    }

    protected void initialize() {
        camera.centerCamera();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
