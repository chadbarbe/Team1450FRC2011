/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Devices;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author chad
 */
public class VelocityDrive implements PIDOutput, PIDSource {

    double currentDriveValue = 0;
    private Encoder encoder;
    private SpeedController drive;
    double velocity = 0;
    double previousDistance = 0;
    private PIDController pid;
    private DriverStationLCD myStationLCD = DriverStationLCD.getInstance();
    private DriverStationLCD.Line line1;
    private DriverStationLCD.Line line2;
    private DriverStationLCD.Line line3;
    private String message;
    private boolean reversed;
    private double maxVelocity;

    public VelocityDrive(Encoder encoder,
            SpeedController drive,
            double maxVelocity,
            boolean reversed,
            DriverStationLCD.Line line1,
            DriverStationLCD.Line line2,
            DriverStationLCD.Line line3,
            String message) {
        this.encoder = encoder;
        this.drive = drive;
        this.pid = new PIDController(0.002, 0, 0, this, this);
        this.pid.setOutputRange(-maxVelocity, maxVelocity);
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.message = message;
        this.maxVelocity = maxVelocity;
        this.reversed = reversed;
    }

    void setTarget(double percentFullSpeed) {
        double target = percentFullSpeed / 100.0 * this.maxVelocity;
        myStationLCD.println(line1, 1, "Target" + message + " = " + target);
        myStationLCD.updateLCD();
        System.out.println("Target = " + target);
        if (reversed) {
            target = -target;
        }
        this.pid.setSetpoint(target);
    }

    void start() {
        this.pid.enable();
    }

    void end() {
        this.pid.disable();
    }

    public void pidWrite(double output) {
        currentDriveValue += output;
        myStationLCD.println(line3, 1, "Force" + message + " = " + currentDriveValue);
        myStationLCD.updateLCD();
        drive.set(currentDriveValue);
    }

    public double pidGet() {
        double distanceTravelled = encoder.getDistance() - previousDistance;
        previousDistance = encoder.getDistance();
        velocity = distanceTravelled / 0.05;
        myStationLCD.println(line2, 1, "Velocity" + message + " = " + velocity);
        myStationLCD.updateLCD();
        return velocity;
    }
}
