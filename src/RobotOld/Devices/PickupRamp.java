package RobotOld.Devices;

import Robot.Utils.Threading;
import RobotOld.ButtonMapping;
import RobotMain.Constants;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

/**
 * The pickup ramp
 */
public class PickupRamp  extends AbstractRobotDevice {

    private final Victor pickupRampMotor = new Victor(IODefines.PICKUP_SHUTE_MOTOR);
    private final ButtonMapping buttonMapping = IODefines.PICKUP_RAMP_BUTTON;
    private final Joystick leftJoystick = buttonMapping.joystick;

    public void disable() {
        pickupRampMotor.disable();
    }

    public void initialize() {
        Threading.runInLoop(Constants.LimitSwitches.loopTime, new PickupRampLoop(), "PickupRamp");
    }

    private class PickupRampLoop implements Runnable {
        public void run() {
            if (leftJoystick.getRawButton(buttonMapping.button)) {
                pickupRampMotor.set(1.0);
            } else {
                pickupRampMotor.set(0.0);
            }
        }
    }
}
