package Robot.Utils;

import RobotMain.Constants;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * A robot drive with a low pass filter
 */
public class LowPassFilteredDrivePlatform extends RobotDrive {

    private double leftFiltered = 0;
    private double rightFiltered = 0;

    public LowPassFilteredDrivePlatform(int leftMotorChannel, int rightMotorChannel) {
        super(leftMotorChannel, rightMotorChannel);
    }

    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        leftFiltered = leftOutput;
        rightFiltered = rightOutput;
        leftFiltered += (leftOutput - leftFiltered) * Constants.Drives.kFilter;
        rightFiltered += (rightOutput - rightFiltered) * Constants.Drives.kFilter;
        super.setLeftRightMotorOutputs(leftFiltered, rightFiltered);
    }
}
