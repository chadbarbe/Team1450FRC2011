/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author chad
 */
public class DrivePIDOutput implements PIDOutput{

    private SpeedController drive;
    private boolean inverted;

    public DrivePIDOutput(SpeedController drive, boolean inverted)
    {
        this.drive = drive;
        this.inverted = inverted;
    }

    public void pidWrite(double output) {
        double myOutput = output;

        if(inverted)
        {
            myOutput = -myOutput;
        }
        drive.set(myOutput);
    }

}
