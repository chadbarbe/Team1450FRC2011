/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Robot;


import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BotMain extends SimpleRobot {
    private RobotDrive drive = new RobotDrive(1,2);
    private Joystick leftStick = new Joystick(1);
    private Joystick rightStick = new Joystick(2);
    private DriverStationLCD myStationLCD = DriverStationLCD.getInstance();

    public BotMain()
    {
        drive.setSafetyEnabled(false);
    }
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        System.out.println("Autonomous Control");
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        System.out.println("Operator Control!");
        while(true)
        {
            drive.tankDrive(leftStick, rightStick);
            Timer.delay(0.005);
        }
    }

    public void disabled()
    {
        myStationLCD.println(DriverStationLCD.Line.kUser2, 1, "suttin else");
        myStationLCD.updateLCD();
    }
}
