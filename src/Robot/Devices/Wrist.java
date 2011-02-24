/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Devices;

import Robot.Utils.DrivePIDOutput;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;

/**
 *
 * @author chad
 */
public class Wrist {

    private Thread m_thread;
    private AnalogChannel pot;
    private DrivePIDOutput wristDrive;
    private Joystick stick;
    private PIDController pid;

    private class WristThread extends Thread {

        private Wrist wrist;
        private boolean m_run = true;

        WristThread(Wrist _wrist) {
            wrist = _wrist;
        }

        public void run() {
            while (m_run) {
                wrist.run();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public Wrist(AnalogChannel _pot, 
            DrivePIDOutput _wristDrive,
            Joystick _stick) {
        pot = _pot;
        wristDrive = _wristDrive;
        stick = _stick;
        pid = new PIDController(0.001, 0, 0, pot, wristDrive);
        pid.setSetpoint(379);
        pid.setOutputRange(-0.5, 0.5);
        m_thread = new WristThread(this);
    }

    private double getPot() {
        return pot.getAverageVoltage();
    }

    public void start() {
        pot.resetAccumulator();
        pid.enable();
        m_thread.start();
    }

    private void run() {
        double joyPositionPercent = (stick.getAxis(Joystick.AxisType.kY) + 1) / 2;
        double driveTarget = (joyPositionPercent * 555 + 215);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Target = " + driveTarget);
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "Pot = " + pot.getAverageValue());
        DriverStationLCD.getInstance().updateLCD();
        pid.setSetpoint(driveTarget);
    }
}
