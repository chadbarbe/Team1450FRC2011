package Robot.Subsystems;

import Robot.Commands.Shooter.DontPivotShoulderCommand;
import Robot.Commands.Shoulder.OperatorControlShooterCommand;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Shoulder extends Subsystem {
    
    private Victor shoulderMotor = new Victor(IODefines.SHOULDER_MOTOR);
    private Counter counter = new Counter(IODefines.SHOULDER_ENCODER);

    protected void initDefaultCommand() {
        setDefaultCommand(new OperatorControlShooterCommand());
    }

    public void dontPivot() {
        shoulderMotor.set(0.0);
    }

    public void operatorControl(double rotation) {
        shoulderMotor.set(rotation);
    }
}
