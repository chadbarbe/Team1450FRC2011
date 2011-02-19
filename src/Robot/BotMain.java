/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Robot;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;

//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import Robot.VelocityDrive;
import Robot.Elevator;
import edu.wpi.first.wpilibj.DigitalInput;

//import edu.wpi.first.wpilibj.CANJaguar;
//import edu.wpi.first.wpilibj.AnalogChannel;
//import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.Compressor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BotMain extends SimpleRobot {
    private double ticksPerRev = 250.0;
    private double wheelDiameter = 20.25;
    private DriverStationLCD myStationLCD = DriverStationLCD.getInstance();
    private Encoder encoder1 = new Encoder(3,4);
    private PIDSource autonomousPIDSourceDistance1;
    private PIDController autonomousPIDDistance1;
    private Console console = new Console();
    private SpeedController jag1 = new Jaguar(1);
    private SpeedController jag2 = new Jaguar(2);
    private DrivePIDOutput pidDrive1 = new DrivePIDOutput(jag1, false);
    private Joystick joy1  = new Joystick(1);
    private Joystick joy2  = new Joystick(2);
    private Encoder encoder2 = new Encoder(1,2);
    private PIDSource autonomousPIDSourceDistance2;
    private PIDController autonomousPIDDistance2;
    private DigitalInput myDrive2ForwardLimit = new DigitalInput(6);
    private DigitalInput myDrive2BackwardLimit = new DigitalInput(9);
    private DrivePIDOutput pidDrive2 = new DrivePIDOutput(jag2, true, myDrive2ForwardLimit, myDrive2BackwardLimit);
    private Compressor windtunnel = new Compressor(12,1);
    private Solenoid sole1 = new Solenoid(1);
    private Solenoid sole2 = new Solenoid(2);
   // private RobotDrive drive = new RobotDrive(jag1,jag2);
    private Elevator elevator = new Elevator(joy2, encoder2, pidDrive2, 400);

    public BotMain()
    {
       // drive.setSafetyEnabled(false);

    }
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        System.out.println("Autonomous Control");
        console.start();
        windtunnel.start();
        encoder1.setDistancePerPulse(wheelDiameter / ticksPerRev);
        encoder1.reset();
        encoder1.start();
        encoder2.setDistancePerPulse(wheelDiameter / ticksPerRev);
        encoder2.reset();
        encoder2.start();

        VelocityDrive myVelocityDrive = new VelocityDrive(encoder1,
                jag1,
                75,
                true,
                DriverStationLCD.Line.kUser2,
                DriverStationLCD.Line.kUser3,
                DriverStationLCD.Line.kUser4,
                "1");
        myVelocityDrive.setTarget(50);
        myVelocityDrive.start();

        VelocityDrive myVelocityDrive2 = new VelocityDrive(encoder2, 
                jag2,
                75,
                false,
                DriverStationLCD.Line.kUser4,
                DriverStationLCD.Line.kUser5,
                DriverStationLCD.Line.kUser6,
                "2");
        myVelocityDrive2.setTarget(50);
        myVelocityDrive2.start();



//        while(true)
//        {
//            myStationLCD.println(DriverStationLCD.Line.kUser2, 1, "E1D = " + encoder1.getDistance());
//            myStationLCD.println(DriverStationLCD.Line.kUser3, 1, "E2D = " + encoder2.getDistance());
//            myStationLCD.updateLCD();
//        }

    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        System.out.println("Operator Control!");
        encoder1.setDistancePerPulse(wheelDiameter / ticksPerRev);
        encoder1.reset();
        encoder1.start();
        encoder2.setDistancePerPulse(wheelDiameter / ticksPerRev);
        encoder2.setReverseDirection(true);
        encoder2.reset();
        encoder2.start();
        windtunnel.start();
        elevator.start();
       
    }

    public void disabled()
    {
        encoder1.stop();
        encoder2.stop();
    }

    protected void robotInit() {

    }

 

}
