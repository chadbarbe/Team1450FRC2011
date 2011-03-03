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
    }

    public void pidWrite(double output) {
        velocityDrive.setTarget(output);
    }

    public void start()
    {
        pidSource.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        pidController.setSetpoint(0);
        pidController.enable();
    }

    public void stop(){
        pidController.disable();
    }

    public void setTarget(double target) {
        pidController.setSetpoint(target);
    }

    public boolean atTarget() {
        return pidController.onTarget();
    }



  
}
