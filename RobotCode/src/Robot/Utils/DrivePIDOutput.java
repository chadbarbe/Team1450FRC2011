/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Utils;

//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author chad
 */
public class DrivePIDOutput implements PIDOutput, DigitalInputNotify {

    private SpeedController drive;
    private boolean inverted;
    private boolean upperLimitReached = false;
    private boolean lowerLimitReached = false;
    private DigitalInputListener digiUpListener;
    private DigitalInputListener digiDownListener;
    private DigitalInput limitUp;
    private DigitalInput limitDown;
    private String name;

    public DrivePIDOutput(SpeedController _drive, 
            DigitalInput _limitUp,
            DigitalInput _limitDown,
            boolean _inverted,
            String _name) {
        drive = _drive;
        inverted = _inverted;
        limitUp = _limitUp;
        limitDown = _limitDown;
        name = _name;
        digiUpListener = new DigitalInputListener(limitUp, this, name + "LimitUp");
        digiDownListener = new DigitalInputListener(limitDown, this, name + "LimitDown");
    }

    public void pidWrite(double output) {
        double myOutput = output;


        if (inverted) {
            myOutput = -myOutput;
        }

        upperLimitReached = !limitUp.get();
        lowerLimitReached = !limitDown.get();

        if(upperLimitReached && myOutput < 0)
        {
            myOutput = 0;
        }
        else if (lowerLimitReached && myOutput > 0) {
            myOutput = 0;
        }

        drive.set(myOutput);
    }

    public void digitalNotify(DigitalInput changedInput) {
        drive.set(0);
        if(changedInput == limitUp)
        {
            upperLimitReached = true;
        }
        else if (changedInput == limitDown)
        {
            lowerLimitReached = true;
        }
        else
        {
            System.out.println("WTF Mate?");
        }
    }

    public void start()
    {
        digiUpListener.start();
        digiDownListener.start();
    }
}
