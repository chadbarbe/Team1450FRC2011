package Robot.Commands.Arm;

import Robot.Commands.CommandBase;

/**
 */
public class MoveArm  extends CommandBase {

    public MoveArm() {
        requires(arm);
    }

    protected void initialize() {
    }

    protected void execute() {
        double rotation = oi.getArmRotation();
        arm.turnArm(rotation);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
