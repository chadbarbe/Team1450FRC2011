/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot;

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
public class VelocityDrive implements PIDOutput, PIDSource{
    double currentDriveValue = 0;
    private Encoder encoder;
    private SpeedController drive;
    double velocity = 0;
    double previousDistance = 0;
    private PIDController pid;
    private DriverStationLCD myStationLCD = DriverStationLCD.getInstance();
    private DriverStationLCD.Line line;
    private String message;

    VelocityDrive(Encoder encoder, SpeedController drive, double maxVelocity, DriverStationLCD.Line line, String message)
    {
        this.encoder = encoder;
        this.drive = drive;
        this.pid = new PIDController(0.002, 0, 0, this, this);
        this.pid.setOutputRange(-maxVelocity, maxVelocity);
        this.line = line;
        this.message = message;


    }
    
    void setTarget(double target)
    {
        this.pid.setSetpoint(target);
    }

    void start()
    {
        this.pid.enable();
    }

    void end()
    {
        this.pid.disable();
    }

    public void pidWrite(double output) {
        currentDriveValue += output;
        drive.set(currentDriveValue);
    }

    public double pidGet() {
        double distanceTravelled = encoder.getDistance() - previousDistance;
        previousDistance = encoder.getDistance();
        velocity = distanceTravelled / 0.05;
        myStationLCD.println(line, 1, "Velocity" + message + " = " + velocity);
        myStationLCD.updateLCD();
        return velocity;
    }

}
