package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;

/**
 */
public class MoveShooterArc extends CommandBase {

    private final double speed;

    public MoveShooterArc(double speed) {
        super("MoveShooterArc Speed=" + speed);
        this.speed = speed;
        requires(shooterArc);
    }

    protected void initialize() {
    }

    protected void execute() {
        shooterArc.setArcSpeed(speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooterArc.setArcSpeed(0.0);
    }

    protected void interrupted() {
        end();
    }
}
