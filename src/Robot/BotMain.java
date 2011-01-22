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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.Dashboard;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.AnalogModule;

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
            //updateDashboard();
        }
    }

    public void disabled()
    {
        myStationLCD.println(DriverStationLCD.Line.kUser2, 1, "Good luck XQ!");
        myStationLCD.updateLCD();
    }

    public void updateDashboard() {
    Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
    lowDashData.addCluster();
    {
        lowDashData.addCluster();
        {     //analog modules
            lowDashData.addCluster();
            {
                for (int i = 1; i <= 5; i++) {
                    lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                }
                //lowDashData.addFloat((float)turnMotor.getOutputCurrent());
                //lowDashData.addFloat((float)turnMotor.getTemperature());
                lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(8));
            }
            lowDashData.finalizeCluster();
            lowDashData.addCluster();
            {
                for (int i = 1; i <= 8; i++) {
                    lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                }
            }
            lowDashData.finalizeCluster();
        }
        lowDashData.finalizeCluster();

        lowDashData.addCluster();
        { //digital modules
            lowDashData.addCluster();
            {
                lowDashData.addCluster();
                {
                    int module = 4;
                    lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                    lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                    lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                    lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                    lowDashData.addCluster();
                    {
                        for (int i = 1; i <= 10; i++) {
                            lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                        }
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

            lowDashData.addCluster();
            {
                lowDashData.addCluster();
                {
                    int module = 6;
                    lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                    lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                    lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                    lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                    lowDashData.addCluster();
                    {
                        for (int i = 1; i <= 10; i++) {
                            lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                        }
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

        }
        lowDashData.finalizeCluster();

       // lowDashData.addByte(Solenoid.getAll());
    }
    lowDashData.finalizeCluster();
    lowDashData.commit();

}

}
