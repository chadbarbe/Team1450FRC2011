package Robot.Subsystems;

import Robot.Commands.Ramp.RampOffCommand;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Ramp extends Subsystem {

    private Victor pickupMotor = new Victor(IODefines.PICKUP_SHUTE_MOTOR);

    protected void initDefaultCommand() {
        setDefaultCommand(new RampOffCommand());
    }

    public void off() {
        pickupMotor.set(0.0);
    }
    
    public void on() {
        pickupMotor.set(1.0);
    }

    public void reverse() {
        pickupMotor.set(-1.0);
    }
}
