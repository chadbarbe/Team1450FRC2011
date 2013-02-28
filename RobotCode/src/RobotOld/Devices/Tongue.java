/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RobotOld.Devices;

import Robot.Utils.Threading;
import RobotMain.Constants;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The tongue is responsible for picking up the ball from the ground.
 *
 * It is controlled by the driver joystick using button 3 as a press button
 */
public class Tongue extends AbstractRobotDevice {

    private final Relay tongueRelay = new Relay(IODefines.TONGUE_RELAY);
    private final JoystickButton button = IODefines.TONGUE_BUTTON;

    private void checkButtonAndActivateTongue() {
        boolean pressed = button.get();
        if (pressed) {
            tongueRelay.set(Relay.Value.kOn);
        } else {
            tongueRelay.set(Relay.Value.kOff);
        }
    }

    public void initialize() {
        tongueRelay.setDirection(Relay.Direction.kForward);
        Threading.runInLoop(Constants.LimitSwitches.loopTime, new TongueLoop(), "Tongue");
    }

    public void disable() {
        tongueRelay.set(Relay.Value.kOff);
    }

   private class TongueLoop implements Runnable {
        public void run() {
            checkButtonAndActivateTongue();
        }
    }

}
