package Robot.Subsystems;

import Robot.Commands.ShutUpTongue;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The tongue consists of a roller.
 */
public class Tongue extends Subsystem {

    private Relay relay = new Relay(IODefines.TONGUE_RELAY);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
       setDefaultCommand(new ShutUpTongue());
    }

    /**
     * Stop the tongue.
     */
    public void shutUp() {
        relay.set(Relay.Value.kOff);
    }

    /**
     * Run the tongue.
     */
    public void pickUp() {
        relay.set(Relay.Value.kOn);
    }
}

