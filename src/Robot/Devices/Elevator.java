/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Devices;

import Robot.Utils.DrivePIDOutput;
import Robot.Utils.PIDTuner;
import Robot2011.Constants;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author chad
 */
public class Elevator implements PIDSource {

    private class ElevatorThread extends Thread {

        private Elevator elevator;
        private boolean m_run = true;
        private double distanceToTop;

        ElevatorThread(Elevator _elevator, double _distanceToTop) {
            elevator = _elevator;
            distanceToTop = _distanceToTop;
        }

        public void run() {
            while (m_run) {
                double joyPositionPercent = (elevator.getZAxis() + 1) / 2;
                double drivePosition = (joyPositionPercent * distanceToTop * 1);
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1, "Target = " + drivePosition);
                DriverStationLCD.getInstance().updateLCD();
                elevator.setTarget(drivePosition);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    private Joystick stick;
    private Thread m_task;
    private Encoder encoder;
    private PIDController pid;
    private DriverStationLCD myStationLCD = DriverStationLCD.getInstance();
    private PIDTuner pidTuner;

    public Elevator(Joystick _stick,
            Encoder _encoder,
            DrivePIDOutput _drive,
            double _distanceToTop) {
        stick = _stick;
        pid = new PIDController(0.01, 0, 0, this, _drive);
        pid.setTolerance(1);
        pid.setOutputRange(-1, 1);
        pidTuner = new PIDTuner(pid, stick, .04, 0, 0);
        m_task = new ElevatorThread(this, _distanceToTop);
        encoder = _encoder;

        encoder.setDistancePerPulse(Constants.elevatorDistancePerPulse);
    }

    public void start() {
        encoder.reset();
        //encoder.setReverseDirection(true);
        encoder.start();
        m_task.start();
        pid.enable();
        pidTuner.start();

    }

    public double getZAxis() {
        return -stick.getZ();
    }

    public void setTarget(double target) {
        pid.setSetpoint(-target);
    }

    public double pidGet() {
        myStationLCD.println(DriverStationLCD.Line.kUser5, 1, "Position = " + encoder.getDistance());
        myStationLCD.updateLCD();
        return encoder.getDistance();
    }
}
