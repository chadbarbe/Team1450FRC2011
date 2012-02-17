package Robot.Devices;

import Robot.Utils.Joysticks;
import Robot.Utils.Threading;
import RobotMain.Constants;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

/**
 * The shoulder rotates the shooter.
 */
public class Shoulder extends AbstractRobotDevice {
    private Victor shoulderMotor = new Victor(IODefines.SHOULDER_MOTOR);
    private Joystick joystick = Joysticks.left();

    public void disable() {
        shoulderMotor.disable();
    }

    public void initialize() {
        Threading.runInLoop(Constants.LimitSwitches.loopTime, new ShoulderLoop(), "Shoulder");
    }

    private class ShoulderLoop implements Runnable {
        public void run() {
            shoulderMotor.set(joystick.getX());
        }
    }
}
