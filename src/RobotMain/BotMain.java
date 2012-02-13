/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package RobotMain;

import Robot.Devices.DrivePlatform;
import Robot.Utils.ShooterSpeedSensor;
import Robot.Devices.Tongue;
import Robot.Devices.VelocityControlMotor;
import Robot.Utils.BallReadySwitch;
import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BotMain extends SimpleRobot {
    //Joystick Instantiation (shared between devices)
    private Joystick rightJoystick = new Joystick(1);
    private Joystick leftJoystick = new Joystick(2);
    
    SpeedController shooterMotor = new Jaguar(IODefines.SHOOTER_DRIVE);

    //Device Instantiation
    DrivePlatform drives = new DrivePlatform(rightJoystick);
    VelocityControlMotor shooter = new VelocityControlMotor(shooterMotor, rightJoystick);
    Relay tongueRelay = new Relay(IODefines.TONGUE_RELAY);
    Tongue tongue = new Tongue(tongueRelay,rightJoystick);
  
    BallReadySwitch.BallReadyListener ballReadyListener = new BallReadySwitch.BallReadyListener() {

        public void ballReady(boolean ready) {
            System.out.println("BallReadySwitch -> " + ready);
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "BallReadySwitch = " + ready);
        }
    };
    BallReadySwitch ballReadySwitch = new BallReadySwitch();
    
    ShooterSpeedSensor shooterSpeedSensor = new ShooterSpeedSensor();

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
      
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        
    }

    /**
     * This function is called when the competition field is disabled or inactive.
     */
    public void disabled() {
        System.out.println("Disable!!!");
        drives.disable();
        shooter.disable();
        tongue.disable();
    }

    /**
     * This function is called when the robot is powered on and initialized.
     */
    protected void robotInit() {
        System.out.println("Disabling motors.");
        drives.disable();
        drives.start();
        shooter.start();
        tongue.enable();
        ballReadySwitch.setBallReadyListener(ballReadyListener);
        System.out.println("Drives started");
    }
}
