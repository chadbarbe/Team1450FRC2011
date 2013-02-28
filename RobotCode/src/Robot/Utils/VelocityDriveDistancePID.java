/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Utils;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 * @author chad
 */
public class VelocityDriveDistancePID implements PIDOutput{
    private VelocityDrive velocityDrive;
    Encoder pidSource;
    PIDController pidController;

    public VelocityDriveDistancePID(Encoder _pidSource, VelocityDrive _velocityDrive) {
        velocityDrive = _velocityDrive;
        pidSource = _pidSource;
        pidController = new PIDController(1.5, 0, 0, pidSource, this);
        pidController.setOutputRange(-75, 75);
    }

    public void pidWrite(double output) {
        System.out.println("Drive Velocity = " + output);
        System.out.println("Drive Distance Target = " + pidController.getSetpoint());
        System.out.println("Drive Distance Actual = " + pidSource.getDistance());
        velocityDrive.setTarget(output);
    }

    public void start()
    {
        pidSource.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        pidController.setInputRange(0, 265);
        pidController.setTolerance(1);
        pidController.enable();
        velocityDrive.start();
    }

    public void stop(){
        velocityDrive.stop();
        pidController.disable();
    }

    public void setTarget(double target) {
        pidController.setSetpoint(target);
    }

    public boolean atTarget() {
        return pidController.onTarget();
    }



  
}
