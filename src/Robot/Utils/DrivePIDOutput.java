/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author chad
 */
public class DrivePIDOutput implements PIDOutput {

    private SpeedController drive;
    private boolean inverted;
    private DigitalInput forwardSwitch = null;
    private DigitalInput backwardSwitch = null;

    public DrivePIDOutput(SpeedController drive,
            boolean inverted,
            DigitalInput _forwardSwitch,
            DigitalInput _backwardSwitch) {
        this(drive, inverted);
        forwardSwitch = _forwardSwitch;
        backwardSwitch = _backwardSwitch;
    }

    public DrivePIDOutput(SpeedController drive, boolean inverted) {
        this.drive = drive;
        this.inverted = inverted;
    }

    public void pidWrite(double output) {
        double myOutput = output;

        if (inverted) {
            myOutput = -myOutput;
        }
//
//        if (output < 0 && !backwardSwitch.get() || output > 0 && !forwardSwitch.get()) {
//            myOutput = 0;
//        }

        drive.set(myOutput);
    }
}
