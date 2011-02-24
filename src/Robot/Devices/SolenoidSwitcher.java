/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Devices;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author chad
 */
public class SolenoidSwitcher {

    private Solenoid sole1;
    private Solenoid sole2;
    private Joystick joystick;
    int sole1ActivateButton;
    int sole2ActivateButton;
    private Thread m_task;

    private class SolenoidSwitcherThread extends Thread {

        private SolenoidSwitcher solenoidSwitcher;
        private boolean m_run = true;

        SolenoidSwitcherThread(SolenoidSwitcher _solenoidSwitcher) {
            solenoidSwitcher = _solenoidSwitcher;
        }

        public void run() {
            while (m_run) {
                solenoidSwitcher.checkAndActuate();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public SolenoidSwitcher(Solenoid _sole1,
            Solenoid _sole2,
            Joystick _joystick,
            int _sole1ActivateButton,
            int _sole2ActivateButton) {
        sole1 = _sole1;
        sole2 = _sole2;
        joystick = _joystick;
        sole1ActivateButton = _sole1ActivateButton;
        sole2ActivateButton = _sole2ActivateButton;
        m_task = new SolenoidSwitcherThread(this);

    }

    public void checkAndActuate() {
        if (joystick.getRawButton(sole1ActivateButton)) {
            sole1.set(true);
            sole2.set(false);
        } else if (joystick.getRawButton(sole2ActivateButton)) {
            sole2.set(true);
            sole1.set(false);
        }

    }

    public void start() {
        m_task.start();
    }
}
