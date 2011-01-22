/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Programmer
 */
public class SingleStickDrive {
    private CrabDrive crabby;
    private Joystick m_stick1, m_stick2;
    double m_defaultTurnSpeed, m_defaultRotateBotSpeed;
    
    
    public SingleStickDrive(Joystick stick1, Joystick stick2) throws edu.wpi.first.wpilibj.can.CANTimeoutException
    {
        System.out.println("SingleStickDrive()");
        m_defaultTurnSpeed = 0.2;
        m_defaultRotateBotSpeed = 0.2;
        m_stick1 = stick1;
        m_stick2 = stick2;
        crabby = new CrabDrive();
    }
    public void Run()
    {
        //For debug, manually manipulate the turning motor.

        if (m_stick1.getRawButton(7))
        {
            CheckManualTurning();
        }
        else
        {
                   //Tank Drive for testing
            TankDrive();
        }
        
        //Rotate the wheels and drive, or spin in place tank-style
        //CheckDriveControl();



        crabby.testPID.run();
    }

    private void CheckDriveControl()
    {
        if(m_stick1.getTrigger())
        {
            crabby.DriveWheels(0);

            if(m_stick1.getRawButton(4))
                crabby.Rotate(-m_defaultRotateBotSpeed);
            else if(m_stick1.getRawButton(5))
                crabby.Rotate(m_defaultRotateBotSpeed);
        }
        else //Not turning, so let's drive
        {
            //System.out.println("JOYSTICK mag: " + m_stick.getMagnitude() + " dir: " + m_stick.getDirectionDegrees());

            double driveSpeed = m_stick1.getMagnitude();
            double driveDir = m_stick1.getDirectionDegrees();

            if (driveSpeed > 1)
                driveSpeed = 1;
            else if (driveSpeed < -1)
                driveSpeed = -1;

            if(driveDir > 90)
            {
                driveDir = driveDir - 180;
                driveSpeed = -driveSpeed;
            }
            else if(driveDir < -90)
            {
                driveDir = driveDir + 180;
                driveSpeed = -driveSpeed;
            }

            if(driveSpeed != 0)
            {
                crabby.SetWheelPosition(driveDir);
            }

            //crabby.DriveWheels(driveSpeed);
        }
    }

    private void CheckManualTurning()
    {
        crabby.DriveWheels(0);
            
        crabby.ManualRotateWheels(m_stick1.getY());

        System.out.println("Turn power: " + m_stick1.getY());
        
    }

    private void TankDrive()
    {
        if(!m_stick1.getTrigger())
        {
            crabby.DriveLeftSide(m_stick1.getY());
            crabby.DriveRightSide(m_stick2.getY());
        }
    }
}
