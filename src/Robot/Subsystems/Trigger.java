package Robot.Subsystems;

import RobotMain.IODefines;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Trigger extends Subsystem {

    private Relay triggerRelay = new Relay(IODefines.TRIGGER_RELAY);
    private DigitalInput ballReadySwitch = new DigitalInput(IODefines.BALL_READY_SWITCH_DI);

    public Trigger() {
        triggerRelay.setDirection(Relay.Direction.kBoth);
    }

    protected void initDefaultCommand() {
    }

    public void on() {
        triggerRelay.set(Relay.Value.kForward);
    }

    public void off() {
        triggerRelay.set(Relay.Value.kOff);
    }

    public boolean ballReady() {
        return ! ballReadySwitch.get();
    }

    public void reverse() {
        triggerRelay.set(Relay.Value.kReverse);
    }
}
