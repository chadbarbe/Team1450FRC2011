package Robot.Subsystems;

import Robot.Commands.Ramp.RampOffCommand;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Ramp extends Subsystem {
    private Victor pickupMotor = new Victor(IODefines.PICKUP_SHUTE_MOTOR);
//    private Relay pickupRelay = new Relay(IODefines.PICKUP_SHUTE_MOTOR);

    protected void initDefaultCommand() {
        setDefaultCommand(new RampOffCommand());
    }

    public void off() {
        pickupMotor.set(0.0);
    }
    
    public void on() {
        pickupMotor.set(1.0);
    }
}
