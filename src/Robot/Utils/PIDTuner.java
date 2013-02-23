/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Utils;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;

/**
 *
 * @author Administrator
 */
public class PIDTuner {

    private void updateP() {
        if (!(control.getRawButton(6) && control.getRawButton(7))) {
            pDebounced = true;
        }

        if (pDebounced) {
            if (control.getRawButton(6)) //Menu Up
            {
                pDebounced = false;
                pValue = pValue + increment;
            } else if (control.getRawButton(7)) //Menu Down
            {
                pDebounced = false;
                pValue = pValue - increment;
            }

        }
    }

    private void updateI() {
        if (!(control.getRawButton(11) && control.getRawButton(10))) {
            iDebounced = true;
        }

        if (iDebounced) {
            if (control.getRawButton(11)) //Menu Up
            {
                iDebounced = false;
                iValue = iValue + increment;
            } else if (control.getRawButton(10)) //Menu Down
            {
                iDebounced = false;
                iValue = iValue - increment;
            }

        }
    }

    private void updateD() {
        if (!(control.getRawButton(8) && control.getRawButton(9))) {
            dDebounced = true;
        }

        if (dDebounced) {
            if (control.getRawButton(9)) //Menu Up
            {
                dDebounced = false;
                dValue = dValue + increment;
            } else if (control.getRawButton(8)) //Menu Down
            {
                dDebounced = false;
                dValue = dValue - increment;
            }

        }
    }
    private int menuSize = 3;
    private double increment;
    private double pValue, iValue, dValue;
    private Joystick control;
    private boolean pDebounced;
    private boolean iDebounced;
    private boolean dDebounced;
    private PIDController pid;
    private Thread m_task;
    private DriverStationLCD myStationLCD = DriverStationLCD.getInstance();

    public PIDTuner(PIDController _pid, Joystick stick, double initP, double initI, double initD) {
        control = stick;
        pid = _pid;
        pValue = initP;
        iValue = initI;
        dValue = initD;
        increment = 0.001;

        pDebounced = false;
        iDebounced = false;
        dDebounced = false;

        m_task = new PIDTunerThread(this);
    }

    private class PIDTunerThread extends Thread {

        private PIDTuner pidTuner;

        PIDTunerThread(PIDTuner _pidTuner) {
            pidTuner = _pidTuner;
        }

        public void run() {
            while (true) {
                pidTuner.run();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void run() {
        updateP();
        updateI();
        updateD();
    //    myStationLCD.println(DriverStationLCD.Line.kUser4, 1, "kP = " + pValue);
    //    myStationLCD.println(DriverStationLCD.Line.kUser5, 1, "ki = " + iValue);
    //    myStationLCD.println(DriverStationLCD.Line.kUser6, 1, "kd = " + dValue);
    //    myStationLCD.updateLCD();

        if (control.getRawButton(2)) {
            UpdatePID();
        }
    }

    public void UpdatePID() {

        pid.setPID(pValue, iValue, dValue);

    }

    public void start() {
        m_task.start();
    }
}
