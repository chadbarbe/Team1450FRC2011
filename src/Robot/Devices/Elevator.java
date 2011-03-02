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

        ElevatorThread(Elevator _elevator) {
            elevator = _elevator;
        }

        public void run() {
            while (m_run) {
                double drivePositionTarget = (getCommandPercent() *
                        Constants.Elevator.distanceToTop);
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 
                        1, "Target = " + drivePositionTarget);
                DriverStationLCD.getInstance().updateLCD();
                elevator.setTarget(drivePositionTarget);

                try {
                    Thread.sleep(Constants.Elevator.loopTime);
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
            DrivePIDOutput _drive) {
        stick = _stick;
        pid = new PIDController(0.01, 0, 0, this, _drive);
        pid.setTolerance(1);
        pid.setOutputRange(-1, 1);
        pidTuner = new PIDTuner(pid, stick, .04, 0, 0);
        m_task = new ElevatorThread(this);
        encoder = _encoder;

        encoder.setDistancePerPulse(Constants.Elevator.distancePerPulse);
    }

    public void start() {
        encoder.reset();
        //encoder.setReverseDirection(true);
        encoder.start();
        m_task.start();
        pid.enable();
        pidTuner.start();
    }

    public double getCommandPercent() {
        return (1 - stick.getZ()) / 2;
    }

    public void setTarget(double target) {
        pid.setSetpoint(-target);
    }

    public void driveUp() {
        //pid.setSetpoint(-target);
    }

    public void driveDown() {
        //pid.setSetpoint(-target);
    }

    public double pidGet() {
        myStationLCD.println(DriverStationLCD.Line.kUser5, 1, "Position = " + encoder.getDistance());
        myStationLCD.updateLCD();
        return encoder.getDistance();
    }
}
