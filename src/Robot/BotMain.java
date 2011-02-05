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
    private SpeedController jag1 = new Jaguar(1);
    private SpeedController jag2 = new Jaguar(2);
    private DrivePIDOutput pidDrive1 = new DrivePIDOutput(jag1, false);
    private Joystick joy1  = new Joystick(1);
    private Joystick joy2  = new Joystick(2);
    private Encoder encoder2 = new Encoder(1,2);
    private PIDSource autonomousPIDSourceDistance2;
    private PIDController autonomousPIDDistance2;
    private DrivePIDOutput pidDrive2 = new DrivePIDOutput(jag2, true);
    private Compressor windtunnel = new Compressor(12,1);
    private Solenoid sole1 = new Solenoid(1);
    private Solenoid sole2 = new Solenoid(2);
    private RobotDrive drive = new RobotDrive(jag1,jag2);
   

    public BotMain()
    {
       // drive.setSafetyEnabled(false);

        autonomousPIDSourceDistance1 = new EncoderPIDSource(encoder1);
        autonomousPIDDistance1 = new PIDController(0.2, 0, 0, autonomousPIDSourceDistance1, pidDrive1);

        autonomousPIDSourceDistance2 = new EncoderPIDSource(encoder2);
        autonomousPIDDistance2 = new PIDController(0.2, 0, 0, autonomousPIDSourceDistance2, pidDrive2);
        
    }
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        System.out.println("Autonomous Control");
        windtunnel.start();
        encoder1.setDistancePerPulse(wheelDiameter / ticksPerRev);
        encoder1.reset();
        encoder1.start();
        encoder2.setDistancePerPulse(wheelDiameter / ticksPerRev);
        encoder2.reset();
        encoder2.start();

//        autonomousPIDDistance1.setTolerance(2);
//        autonomousPIDDistance1.setSetpoint(230);
//        autonomousPIDDistance1.setOutputRange(-0.7, 0.7);
//        autonomousPIDDistance1.enable();
//
//        autonomousPIDDistance2.setTolerance(2);
//        autonomousPIDDistance2.setSetpoint(230);
//        autonomousPIDDistance2.setOutputRange(-0.7, 0.7);
//        autonomousPIDDistance2.enable();

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
        encoder2.reset();
        encoder2.start();
        encoder2.setReverseDirection(true);
        windtunnel.start();
        while(true)
        {
            Timer.delay(0.005);
            drive.tankDrive(joy1, joy2);
            myStationLCD.println(DriverStationLCD.Line.kUser2, 1, "E1D = " + encoder1.getDistance());
            myStationLCD.println(DriverStationLCD.Line.kUser2, 1, "E2D = " + encoder2.getDistance());
            
            myStationLCD.updateLCD();

            if(joy1.getTrigger(Hand.kLeft))
            {
                System.out.println("TRIGGER!");
                sole1.set(false);
                sole2.set(true);
            }
            else
            {
                sole1.set(true);
                sole2.set(false);
            }
        }
    }

    public void disabled()
    {
        encoder1.stop();
        encoder2.stop();

        autonomousPIDDistance1.disable();
        autonomousPIDDistance2.disable();
    }

    protected void robotInit() {

    }

 

}
