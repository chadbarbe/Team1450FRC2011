package Robot.Subsystems;

import Robot.Commands.Waist.OperatorControlShooterCommand;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The waist pivots the shooter.
 */
public class Waist extends Subsystem {
    
    private Victor waistMotor = new Victor(IODefines.WAIST_MOTOR);
    private Counter counter = new Counter(IODefines.WAIST_ENCODER);

    public Waist() {
        //todo: This should be a pid subsystem where the shooter angle can be controlled by the encoder
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new OperatorControlShooterCommand());
    }

    public void dontPivot() {
        waistMotor.set(0.0);
    }

    // todo: deprecate; turn is more descriptive
    public void operatorControl(double rotation) {
        turn(rotation);
    }

    public void turn(double rotation) {
        waistMotor.set(rotation);
    }

    public void resetCounter() {
        counter.reset();
    }

    public int counterValue() {
        return counter.get();
    }
}
