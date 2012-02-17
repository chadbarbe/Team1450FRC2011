/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Devices;

import Robot.Utils.Threading;
import RobotMain.Constants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author cbarbe
 */
public class VelocityControlMotor {
    private SpeedController motor;
    private Joystick joystick;

    public void disable() {
        motor.disable();
    }

    private class VelocityControlLoop implements Runnable {
        public void run() {
            motor.set(getUserInput());
        }
    }

    public VelocityControlMotor(SpeedController _motor,
            Joystick _joystick) {

        System.out.println("Velocity motor constuct");
        motor = _motor;
        joystick = _joystick;

    }

    public void start() {
        System.out.println("Velocity Motor Start");
        Threading.runInLoop(Constants.LimitSwitches.loopTime, new VelocityControlLoop(), "VelocityControlMotor");
    }
    
    private double getUserInput() {
        return (joystick.getZ()- 1) / 2;
    }

}
