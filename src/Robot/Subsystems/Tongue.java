package Robot.Subsystems;

import Robot.Commands.Tongue.ShutUpTongue;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The tongue consists of a roller.
 */
public class Tongue extends Subsystem {

    private Relay relay = new Relay(IODefines.TONGUE_RELAY);

    public Tongue() {
        relay.setDirection(Relay.Direction.kBoth);
    }

    public void initDefaultCommand() {
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
        relay.set(Relay.Value.kForward);
    }
}

