/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot.Devices;

import RobotMain.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author chad
 */
public class TwoButtonMotor {

    private SpeedController motor;
    private Joystick joystick;
    private int forwardButton;
    private int backButton;
    private long startTime;
    private Thread m_task;
    private DigitalInput limit;

        private class TwoButtonMotorThread extends Thread {

        private TwoButtonMotor twoButtonMotor;
        private boolean m_run = true;

        TwoButtonMotorThread(TwoButtonMotor _twoButtonMotor) {
            twoButtonMotor = _twoButtonMotor;
        }

        public void run() {
            while (m_run) {
                twoButtonMotor.checkAndActuate();
                try {
                    Thread.sleep(Constants.MiniBotDeployment.loopTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public TwoButtonMotor(SpeedController _motor,
            Joystick _joystick,
            int _forwardButton,
            int _backButton,
            DigitalInput _limit) {

        System.out.println("Mini bot constuct");
        motor = _motor;
        joystick = _joystick;
        forwardButton = _forwardButton;
        backButton = _backButton;
        limit = _limit;
        m_task = new TwoButtonMotorThread(this);

    }

    private void checkAndActuate() {

        if((System.currentTimeMillis() - startTime) > Constants.MiniBotDeployment.enableAfterTime &&
          limit.get())
        {
            if (joystick.getRawButton(forwardButton)) {
                System.out.println("MINI BOT FORWARD!");
                motor.set(Constants.MiniBotDeployment.speed);
            } else if (joystick.getRawButton(backButton)) {
                System.out.println("MINI BOT BACKWARD!");
                motor.set(-Constants.MiniBotDeployment.speed);
            }
            else
            {
                motor.set(0);
            }
        }
        else {
            motor.set(0);
        }

    }

    public void start() {
        System.out.println("Mini bot start");
        startTime = System.currentTimeMillis();
        m_task.start();
    }


    public void resetStartTime()
    {
        startTime = System.currentTimeMillis();
    }


}
