/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Devices;

import Robot2011.Constants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 * @author chad
 */
public class XQDrives {

    private RobotDrive drives;
    private Joystick joystick;
    private Thread m_task;

    public XQDrives(RobotDrive _drives, Joystick _joystick) {
        drives = _drives;
        joystick = _joystick;
        m_task = new XQDrivesThread(this);
    }

    private class XQDrivesThread extends Thread {

        private XQDrives xqDrives;
        private boolean m_run = true;

        XQDrivesThread(XQDrives _xqDrives) {
            xqDrives = _xqDrives;
        }

        public void run() {
            while (m_run) {
                xqDrives.drive();

                try {
                    Thread.sleep(Constants.Drives.loopTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void drive() {
        drives.arcadeDrive(joystick);
    }

    public void start() {
        m_task.start();
    }
}
