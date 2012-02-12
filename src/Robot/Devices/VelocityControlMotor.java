/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Devices;

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
    private Thread m_task;

    public void disable() {
        motor.disable();
    }
    
        private class VelocityControlMotorThread extends Thread {

        private VelocityControlMotor VelocityControlMotor;
        private boolean m_run = true;

        VelocityControlMotorThread(VelocityControlMotor _VelocityControlMotor) {
            VelocityControlMotor = _VelocityControlMotor;
        }

        public void run() {
            while (m_run) {
                motor.set(getUserInput());
                try {
                    Thread.sleep(Constants.MiniBotDeployment.loopTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public VelocityControlMotor(SpeedController _motor,
            Joystick _joystick) {

        System.out.println("Velocity motor constuct");
        motor = _motor;
        joystick = _joystick;
        m_task = new VelocityControlMotor.VelocityControlMotorThread(this);

    }

    public void start() {
        System.out.println("Velocity Motor Start"); 
        m_task.start();
    }
    
    private double getUserInput() {
        return (joystick.getZ()- 1) / 2;
    }

}
