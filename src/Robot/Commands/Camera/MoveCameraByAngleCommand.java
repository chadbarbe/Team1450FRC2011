package Robot.Commands.Camera;

import Robot.Commands.CommandBase;

/**
 */
public class MoveCameraByAngleCommand extends CommandBase {
    int angle;
    public MoveCameraByAngleCommand(int _angle) {
        super("MoveCameraBy " + _angle);
        requires(camera);
        angle = _angle;
    }

    protected void initialize() {
        angle = angle + camera.getPosition();
    }

    protected void execute() {
        camera.setCameraAngle(angle);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
