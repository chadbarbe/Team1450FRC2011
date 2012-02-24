package Robot.Commands.Waist;

import Robot.Commands.CommandBase;

/**
 */
public class MoveWaistByAngle extends CommandBase {

    private int angleCount;

    /**
     * Move by angle in degrees
     * @param _angle in degress
     */
    public MoveWaistByAngle(double _angle) {
        super("MoveWaistByAngle" + _angle);
        requires(waist);
        angleCount = (int) _angle;
        setTimeout(0.5); // just in case the encoder is broken
        // todo: calculate encoder tick to angle ratio
        // todo: this should use a pid with the counter instead
    }

    protected void initialize() {
        waist.resetCounter();
    }

    protected void execute() {
        if (angleCount > 0) {
            waist.turn(0.25);
        } else if (angleCount < 0) {
            waist.turn(-0.25);
        }
    }

    protected boolean isFinished() {
        System.out.println(getName() + ": waist counter = " + waist.counterValue());
        return angleCount == 0 || waist.counterValue() >= angleCount || isTimedOut();
    }

    protected void end() {
        waist.dontPivot();
    }

    protected void interrupted() {
        System.out.println(getName() + " interrupted");
        end();
    }
}
