package Robot.Commands;

import Robot.Commands.Waist.MoveWaistByAngle;

/**
 */
public class TurnWaistToTargetCommand extends MoveWaistByAngle {
    public TurnWaistToTargetCommand() {
        super(WaitForTargetCommand.target.angleX);
    }
}
