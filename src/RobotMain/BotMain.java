/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package RobotMain;

import Robot.Devices.*;
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

    //Device Instantiation
    DrivePlatform drives = new DrivePlatform();

    BallReadySwitch ballReadySwitch = new BallReadySwitch();
    BallReadySwitch.BallReadyListener ballReadyListener = new BallReadySwitch.BallReadyListener() {

        public void ballReady(boolean ready) {
            System.out.println("BallReadySwitch -> " + ready);
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "BallReadySwitch = " + ready);
        }
    };

    /** Tongue to pick up the ball */
    Tongue tongue = new Tongue();

    /** The ramp the ball moves up to the trigger */
    PickupRamp pickupRamp = new PickupRamp();

    /** The trigger pushes the ball into the shooter */
    Trigger trigger = new Trigger(ballReadySwitch);

    /** The shoulder rotates the shooter */
    Shoulder shoulder = new Shoulder();

    /** Shooter shoots the ball */
    Shooter shooter = new Shooter();


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
        shoulder.disable();
        pickupRamp.disable();
        trigger.disable();
    }

    /**
     * This function is called when the robot is powered on and initialized.
     */
    protected void robotInit() {
        System.out.println("Disabling motors.");
        drives.disable();
        drives.initialize();
        shooter.initialize();
        tongue.initialize();
        trigger.initialize();
        ballReadySwitch.setBallReadyListener(ballReadyListener);
        System.out.println("Drives started");
    }
}
