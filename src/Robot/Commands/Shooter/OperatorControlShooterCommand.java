package Robot.Commands.Shooter;

import Robot.Commands.CommandBase;
import java.lang.Math;

/**
 */
public class OperatorControlShooterCommand  extends CommandBase {

    public OperatorControlShooterCommand() {
        requires(shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
        if (oi.getShooterThrottle() == 0)
        {
            shooter.throttle(0);
            shooter.throttle2(0);
        }else{
            double speed;
            speed = (Math.sin(oi.getShooterThrottle()*Math.PI/2.0)+Math.sin(Math.PI/4.0+oi.getShooterThrottle()*Math.PI/4.0))/2.0;
            shooter.throttle(speed*-1.0);
            shooter.throttle2(speed*-0.8);
        }
        //shooter.throttle2(oi.getShooter2Throttle());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
