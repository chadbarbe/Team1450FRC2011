/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package Robot2011;

import Robot.Devices.SolenoidSwitcher;
import Robot.Devices.XQDrives;
import Robot.Devices.Elevator;
import Robot.Utils.DrivePIDOutput;
import Robot.Devices.Wrist;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;

//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.AnalogChannel;
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
    //Speed Controllers

    private SpeedController leftDrive = new Jaguar(IODefines.LEFT_DRIVE);
    private SpeedController rightDrive = new Jaguar(IODefines.RIGHT_DRIVE);
    private SpeedController wristDrive = new Jaguar(IODefines.WRIST_DRIVE);
    private SpeedController elevatorDrive = new Jaguar(IODefines.ELEVATOR_DRIVE);
    //Encoders
    private Encoder leftDriveEncoder = new Encoder(IODefines.LEFT_DRIVE_ENCODER_A,
            IODefines.LEFT_DRIVE_ENCODER_B);
    private Encoder rightDriveEncoder = new Encoder(IODefines.RIGHT_DRIVE_ENCODER_A,
            IODefines.RIGHT_DRIVE_ENCODER_B);
    private Encoder elevatorDriveEncoder = new Encoder(IODefines.ELEVATOR_DRIVE_ENCODER_A,
            IODefines.ELEVATOR_DRIVE_ENCODER_B);
    //Limit Switches
    private DigitalInput wristLimitUp = new DigitalInput(IODefines.WRIST_LIMIT_UP);
    private DigitalInput wristLimitDown = new DigitalInput(IODefines.WRIST_LIMIT_DOWN);
    private DigitalInput elevatorLimitUp = new DigitalInput(IODefines.ELEVATOR_LIMIT_UP);
    private DigitalInput elevatorLimitDown = new DigitalInput(IODefines.ELEVATOR_LIMIT_DOWN);
    //Compressor
    private Compressor compressor = new Compressor(IODefines.PRESSURE_SWITCH,
            IODefines.COMPRESSOR_POWER);
    //Analogs
    private AnalogChannel wristPot = new AnalogChannel(IODefines.WRIST_POT);
    //Solenoids
    private Solenoid driveHighGear = new Solenoid(IODefines.DRIVE_HIGH);
    private Solenoid driveLowGear = new Solenoid(IODefines.DRIVE_LOW);
    private Solenoid gripperGrab = new Solenoid(IODefines.GRIPPER_GRAB);
    private Solenoid gripperRelease = new Solenoid(IODefines.GRIPPER_RELEASE);
    private Solenoid shoulderUp = new Solenoid(IODefines.SHOULDER_UP);
    private Solenoid shoulderDown = new Solenoid(IODefines.SHOULDER_DOWN);
    private Solenoid miniBotDeploy = new Solenoid(IODefines.MINI_BOT_DEPLOY);
    private Solenoid miniBotRetract = new Solenoid(IODefines.MINI_BOT_RETRACT);
    //Joysticks
    private Joystick joy1 = new Joystick(1);
    private Joystick joy2 = new Joystick(2);
    //Operator Mode Drive Control
    private RobotDrive drives = new RobotDrive(leftDrive, rightDrive);
    private XQDrives xqDrives = new XQDrives(drives, joy1);
    //Operator Mode Elevator Control
    private DrivePIDOutput elevatorPIDOutput = new DrivePIDOutput(elevatorDrive, false, elevatorLimitUp, elevatorLimitDown);
    private Elevator elevator = new Elevator(joy2, elevatorDriveEncoder, elevatorPIDOutput);
    //Solenoids
    SolenoidSwitcher gripperGuy = new SolenoidSwitcher(gripperGrab,
            gripperRelease,
            joy2,
            IODefines.GRIPPER_GRAB_BUTTON,
            IODefines.GRIPPER_RELEASE_BUTTON);
    SolenoidSwitcher shoulderGuy = new SolenoidSwitcher(shoulderUp,
            shoulderDown,
            joy2,
            IODefines.SHOULDER_UP_BUTTON,
            IODefines.SHOULDER_DOWN_BUTTON);
    SolenoidSwitcher transmissionGuy = new SolenoidSwitcher(driveHighGear,
            driveLowGear,
            joy1,
            IODefines.DRIVE_HIGH_BUTTON,
            IODefines.DRIVE_LOW_BUTTON);
    //Wrist
    private DrivePIDOutput wristPIDOutput = new DrivePIDOutput(wristDrive, true, wristLimitUp, wristLimitDown);
    Wrist wrist = new Wrist(wristPot, wristPIDOutput, joy2);

    public BotMain() {
    }

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        System.out.println("Autonomous Control");
//        compressor.start();
//        encoder1.setDistancePerPulse(wheelDiameter / ticksPerRev);
//        encoder1.reset();
//        encoder1.start();
//        encoder2.setDistancePerPulse(wheelDiameter / ticksPerRev);
//        encoder2.reset();
//        encoder2.start();
//
//        VelocityDrive myVelocityDrive = new VelocityDrive(encoder1,
//                jag1,
//                75,
//                true,
//                DriverStationLCD.Line.kUser2,
//                DriverStationLCD.Line.kUser3,
//                DriverStationLCD.Line.kUser4,
//                "1");
//        myVelocityDrive.setTarget(50);
//        myVelocityDrive.start();
//
//        VelocityDrive myVelocityDrive2 = new VelocityDrive(encoder2,
//                jag2,
//                75,
//                false,
//                DriverStationLCD.Line.kUser4,
//                DriverStationLCD.Line.kUser5,
//                DriverStationLCD.Line.kUser6,
//                "2");
//        myVelocityDrive2.setTarget(50);
//        myVelocityDrive2.start();
//
//
//
////        while(true)
////        {
////            myStationLCD.println(DriverStationLCD.Line.kUser2, 1, "E1D = " + encoder1.getDistance());
////            myStationLCD.println(DriverStationLCD.Line.kUser3, 1, "E2D = " + encoder2.getDistance());
////            myStationLCD.updateLCD();
////        }
//
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        System.out.println("Operator Control!");
        compressor.start();
        xqDrives.start();
        elevator.start();
        gripperGuy.start();
        shoulderGuy.start();
        transmissionGuy.start();
        wrist.start();
    }

    public void disabled() {
        leftDrive.disable();
        rightDrive.disable();
        wristDrive.disable();
        elevatorDrive.disable();
    }

    protected void robotInit() {
    }
}
