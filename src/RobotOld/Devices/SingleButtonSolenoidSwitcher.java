/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RobotOld.Devices;

import RobotMain.Constants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author chad
 */
public class SingleButtonSolenoidSwitcher {

    private Solenoid sole1;
    private Solenoid sole2;
    private Joystick joystick;
    int button;
    private Thread m_task;

    private class SolenoidSwitcherThread extends Thread {

        private SingleButtonSolenoidSwitcher solenoidSwitcher;
        private boolean m_run = true;

        SolenoidSwitcherThread(SingleButtonSolenoidSwitcher _solenoidSwitcher) {
            solenoidSwitcher = _solenoidSwitcher;
        }

        public void run() {
            while (m_run) {
                solenoidSwitcher.checkAndActuate();
                try {
                    Thread.sleep(Constants.Solenoid.loopTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public SingleButtonSolenoidSwitcher(Solenoid _sole1,
            Solenoid _sole2,
            Joystick _joystick,
            int _button) {
        sole1 = _sole1;
        sole2 = _sole2;
        joystick = _joystick;
        button = _button;
        m_task = new SolenoidSwitcherThread(this);

    }

    private void checkAndActuate() {
        if (joystick.getRawButton(button)) {
            actuate2();
        } else {
            actuate1();
        }

    }

    public void actuate1() {
        sole1.set(true);
        sole2.set(false);
    }

    public void actuate2() {
        sole1.set(false);
        sole2.set(true);
    }

    public void start() {
        m_task.start();
    }
}
