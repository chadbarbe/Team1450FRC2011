/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Robot;


import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class OldBotMain extends IterativeRobot {

    private Compressor kickerPower;
    private Kicker ronaldinho;
    private int m_pressureSwitchChannel = 2;
    private int m_compressorRelayChannel = 1;
    private Joystick driveStick, kickStick;
    private SingleStickDrive spaceInvaders;
    private SWCrabDrive drive;
    private CANJaguar turnMotor;
    private Autonomous skynet;

    public void robotInit()
    {
        System.out.println("RobotInit()");
        kickerPower = new Compressor(m_pressureSwitchChannel, m_compressorRelayChannel);

        driveStick = new Joystick(1);
        kickStick = new Joystick(2);

        try {
            turnMotor = new CANJaguar(2);
        }
        catch (edu.wpi.first.wpilibj.can.CANTimeoutException e)
        {
            
        }

        try {

        //spaceInvaders = new SingleStickDrive(driveStick, kickStick);
        drive = new SWCrabDrive(turnMotor, new AnalogChannel(1), driveStick,
                                new CANJaguar(5), new CANJaguar(8),
                                new CANJaguar(7), new CANJaguar(4));

        }
        catch (edu.wpi.first.wpilibj.can.CANTimeoutException e)
        {

        }

        ronaldinho = new Kicker(kickStick);
        skynet = new Autonomous(drive, ronaldinho);
    }

    public void autonomousInit()
    {
        kickerPower.start();
        skynet.reset();
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        //skynet.run();
    }

    public void teleopInit()
    {
        ronaldinho.Reset();
        kickerPower.start();
    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        ronaldinho.Run();
        //spaceInvaders.Run();
        drive.update();
        updateDashboard();
    }

    public void disabledPeriodic() {}

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
                    try {
                        lowDashData.addFloat((float)turnMotor.getOutputCurrent());
                        lowDashData.addFloat((float)turnMotor.getTemperature());
                    }
                    catch (edu.wpi.first.wpilibj.can.CANTimeoutException e)
                    {

                    }
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

       //     lowDashData.addByte(Solenoid.getAll());
        }
        lowDashData.finalizeCluster();
        lowDashData.commit();

    }

}