/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Programmer
 */
public class CrabDrive
{
    private CANJaguar leftFrontDrive;
    private CANJaguar leftRearDrive;
    private CANJaguar rightFrontDrive;
    private CANJaguar rightRearDrive;
    private CANJaguar turningMotor;
    public boolean steeringControlEnabled;
    private PIDController pidCont;
    private PIDSource potData;

    public PIDTuner testPID;

    private short m_potTurns = 1;

    public CrabDrive()
    {
        System.out.println("CrabDrive()");
        leftFrontDrive = new CANJaguar(5);
        leftRearDrive = new CANJaguar(8);
        rightFrontDrive = new CANJaguar(7);
        rightRearDrive = new CANJaguar(4);
        turningMotor = new CANJaguar(2);

        //pidCont = new PIDController(4000.0, 0.0, 0.0);
        steeringControlEnabled = false;

        System.out.println("PID Enabled");
        //SetupSteeringControl();

        testPID = new PIDTuner(turningMotor, new Joystick(3));
    }

    public void SetupSteeringControl()
    {
        turningMotor.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
        //turningMotor.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        turningMotor.configPotentiometerTurns(m_potTurns);
        turningMotor.setPID(4000.0,0.0, 0.0);
        turningMotor.enableControl(0);
        turningMotor.set(0.5);
    }
/*
    public void SteeringControl(boolean enable)
    {
        if(steeringControlEnabled != enable)
        {
            steeringControlEnabled = enable;

            if(steeringControlEnabled)
            {
                System.out.println("PID Enabled");
                turningMotor = new CANJaguar(2, ControlMode.kPosition);
                turningMotor.enableControl(0);
                turningMotor.set(0.5);
            }
            else
            {
                System.out.println("PID Disabled");
                turningMotor.disableControl();
                turningMotor = new CANJaguar(2);
            }
        }
    }*/

    public void ManualRotateWheels(double velocity)
    {
        //System.out.println("Pot position: " + turningMotor.getPosition());

        turningMotor.set(velocity);
    }

     //Rotate wheels to a position from -90 degrees to 90 degrees relative to forward.
    public void SetWheelPosition(double degrees)
    {
        System.out.println("drive angle: " + degrees);
        double turnPos = (degrees+90.0)/180.0 * m_potTurns;
        turningMotor.set(turnPos);
    }

    // Drive wheels at a velocity between -1 and 1 where -1 is reverse, 0 is stopped,
    // and 1 is forward.
    public void DriveWheels(double velocity)
    {
        System.out.println("Drive: "+ leftFrontDrive.toString());
    
        leftFrontDrive.set(velocity);
        leftRearDrive.set(velocity);
        rightFrontDrive.set(velocity);
        rightRearDrive.set(velocity);
    }

    // Rotate the robot (+ is clockwise).
    public void Rotate(double velocity)
    {        
        leftFrontDrive.set(velocity);
        leftRearDrive.set(velocity);
        rightFrontDrive.set(-velocity);
        rightRearDrive.set(-velocity);
    }

    public void DriveLeftSide(double velocity)
    {
        leftFrontDrive.set(velocity);
        leftRearDrive.set(velocity);
    }

    public void DriveRightSide(double velocity)
    {
        rightFrontDrive.set(velocity);
        rightRearDrive.set(velocity);
    }
}
