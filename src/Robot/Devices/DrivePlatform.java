/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot.Devices;

import Robot2011.Constants;
import Robot2011.IODefines;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author gferencz
 */
public class DrivePlatform {

    private Thread m_thread;
    private Joystick joy;
    private DriverStationLCD myStationLCD = DriverStationLCD.getInstance();
    private boolean userMode;

    private SpeedController leftDrive = new Jaguar(IODefines.LEFT_DRIVE);
    private SpeedController rightDrive = new Jaguar(IODefines.RIGHT_DRIVE);
    private Encoder leftDriveEncoder = new Encoder(IODefines.LEFT_DRIVE_ENCODER_A,
            IODefines.LEFT_DRIVE_ENCODER_B);
    private Encoder rightDriveEncoder = new Encoder(IODefines.RIGHT_DRIVE_ENCODER_A,
            IODefines.RIGHT_DRIVE_ENCODER_B);
    private RobotDrive drives = new RobotDrive(leftDrive, rightDrive);

    public DrivePlatform(Joystick _joy){
        m_thread = new DrivesThread(this);
        joy = _joy;
    }

    private class DrivesThread extends Thread {

        private DrivePlatform drives;
        private boolean m_run = true;

        DrivesThread(DrivePlatform _drives) {
            drives = _drives;
        }

        public void run() {
            while (m_run) {
                drives.run();

                try {
                    Thread.sleep(Constants.Drives.loopTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }


    private void run() {
        if (userMode) {
            drives.arcadeDrive(joy);
        }
    }

    public void start() {
        //pid.enable();
        leftDriveEncoder.start();
        leftDriveEncoder.reset();
        leftDriveEncoder.setReverseDirection(true);
        leftDriveEncoder.setDistancePerPulse(Constants.Drives.distancePerPulse);

        rightDriveEncoder.start();
        rightDriveEncoder.reset();
        rightDriveEncoder.setDistancePerPulse(Constants.Drives.distancePerPulse);

        m_thread.start();
    }

    public void goToScoringRack() {
        double curve = 0;
        double speed = -0.5;

        while(leftDriveEncoder.getDistance() < Constants.Drives.distanceToScoringRack)
        {
            //curve = encoder2.getDistance()/encoder1.getDistance();
            drives.drive(speed,curve);
            myStationLCD.println(DriverStationLCD.Line.kUser2,
                    1, "lEnc1Tks=" + leftDriveEncoder.get());
            myStationLCD.println(DriverStationLCD.Line.kUser3,
                    1, "lDist=" + leftDriveEncoder.getDistance());
            myStationLCD.println(DriverStationLCD.Line.kUser4,
                    1, "rEnc1Tks=" + rightDriveEncoder.get());
            myStationLCD.println(DriverStationLCD.Line.kUser5,
                    1, "rDist=" + rightDriveEncoder.getDistance());
            myStationLCD.updateLCD();
        }
        drives.stopMotor();
    }

    public void setUserCommandMode() {
        userMode = true;
    }

    public void disable() {
        leftDrive.disable();
        rightDrive.disable();
    }
}
