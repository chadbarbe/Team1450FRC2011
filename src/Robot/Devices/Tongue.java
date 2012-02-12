/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Devices;

import RobotMain.Constants;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Programer
 */
public class Tongue implements Runnable {
    private final Relay tongueRelay;
    private final Joystick joystick;
    private boolean enabled;
    private Thread mThread;

    public Tongue(Relay _tongueRelay, Joystick _rightJoystick) {
        tongueRelay = _tongueRelay;
        joystick = _rightJoystick;
        tongueRelay.setDirection(Relay.Direction.kForward);
        mThread = new Thread(this);
    }
    
    public void checkButtonAndActivateTongue() {
        boolean pressed = joystick.getRawButton(3);
        if (pressed) {
            tongueRelay.set(Relay.Value.kOn);
        } else {
            tongueRelay.set(Relay.Value.kOff);
        }
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
        tongueRelay.set(Relay.Value.kOff);
    }

    public void run() {
        while (true) {
            if (enabled) {
                checkButtonAndActivateTongue();
            }
            try {
                Thread.sleep(Constants.LimitSwitches.loopTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
