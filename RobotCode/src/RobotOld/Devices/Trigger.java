package RobotOld.Devices;

import Robot.Utils.BallReadySwitch;
import Robot.Utils.Threading;
import RobotOld.ButtonMapping;
import RobotMain.Constants;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The trigger pushes the ball into the shooter.
 */
public class Trigger extends AbstractRobotDevice {

    private Victor triggerMotor = new Victor(IODefines.TRIGGER_RELAY);
    private JoystickButton button = IODefines.TRIGGER_BUTTON;
    private final BallReadySwitch ballReadySwitch;

    public Trigger(BallReadySwitch _ballReadySwitch) {
        ballReadySwitch = _ballReadySwitch;
    }

    /**
     * Shoot the ball.
     */
    public void shoot() {
        triggerMotor.set(1.0);
        Threading.sleep(2 * 1000);
        triggerMotor.set(0.0);
    }

    public void initialize() {
        Threading.runInLoop(Constants.LimitSwitches.loopTime, new TriggerLoop(), "Trigger");
    }

    public void disable() {
        triggerMotor.disable();
    }

    private class TriggerLoop implements Runnable {
        public void run() {
            final boolean triggerPressed = button.get();
            if (triggerPressed && ballReadySwitch.isBallReady()) {
                shoot();
            }
        }
    }
}
