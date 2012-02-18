package Robot.Subsystems;

import Robot.Commands.Shooter.DontPivotShoulderCommand;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Shoulder extends Subsystem {
    
    private Victor shoulderMotor = new Victor(IODefines.SHOULDER_MOTOR);
    protected void initDefaultCommand() {
        setDefaultCommand(new DontPivotShoulderCommand());
    }

    public void dontPivot() {
        
    }
}
