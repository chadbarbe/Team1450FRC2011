/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package RobotMain;

import Robot.Devices.DrivePlatform;
import Robot.Devices.Tongue;
import Robot.Devices.VelocityControlMotor;
import Robot.Utils.DigitalInputListener;
import Robot.Utils.DigitalInputNotify;
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
  
    DigitalInput stopAndGoDigitalInput = new DigitalInput(4);
    private DigitalInputNotify stopAndGoNotifier = new DigitalInputNotify() {

        public void digitalNotify(DigitalInput changedInput) {
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "StopAndGo Switch = " + changedInput.get());
        }
    };
    DigitalInputListener stopAndGoListener = new DigitalInputListener(stopAndGoDigitalInput, stopAndGoNotifier, "stopAndGo");
    

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
        System.out.println("Drives started");
    }
}
