/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RobotOld.Devices;

import RobotMain.Constants;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author gferencz
 */
public class DrivePlatform {

    private DriverStationLCD myStationLCD = DriverStationLCD.getInstance();

    private SpeedController leftDrive = new Jaguar(IODefines.LEFT_DRIVE);
    private SpeedController rightDrive = new Jaguar(IODefines.RIGHT_DRIVE);
    private Encoder leftDriveEncoder = new Encoder(IODefines.LEFT_DRIVE_ENCODER_A,
            IODefines.LEFT_DRIVE_ENCODER_B);
    private Encoder rightDriveEncoder = new Encoder(IODefines.RIGHT_DRIVE_ENCODER_A,
            IODefines.RIGHT_DRIVE_ENCODER_B);
    private RobotDrive drives = new RobotDrive(leftDrive, rightDrive);
    
    private double leftFiltered = 0;
    private double rightFiltered = 0;

    public DrivePlatform(){        
    }

    public void backOffScoringRack() {
        double curve = 0;
        double speed = 0.5;

        while(leftDriveEncoder.getDistance() > Constants.Drives.distanceBackFromScoringRack)
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

    public void initialize() {
        leftDriveEncoder.start();
        leftDriveEncoder.reset();
        leftDriveEncoder.setReverseDirection(true);
        leftDriveEncoder.setDistancePerPulse(Constants.Drives.distancePerPulse);

        rightDriveEncoder.start();
        rightDriveEncoder.reset();
        rightDriveEncoder.setDistancePerPulse(Constants.Drives.distancePerPulse);
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

    public void disable() {
        leftDrive.disable();
        rightDrive.disable();
    }
    
    
    public void arcadeDrive(double moveValue, double rotateValue) {
        // local variables to hold the computed PWM values for the motors
        double leftMotorSpeed;
        double rightMotorSpeed;

        moveValue = limit(moveValue);
        rotateValue = limit(rotateValue);

       
        // square the inputs (while preserving the sign) to increase fine control while permitting full power
        if (moveValue >= 0.0) {
            moveValue = (moveValue * moveValue);
        } else {
            moveValue = -(moveValue * moveValue);
        }
        if (rotateValue >= 0.0) {
            rotateValue = (rotateValue * rotateValue);
        } else {
            rotateValue = -(rotateValue * rotateValue);
        }

        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        
        
        leftFiltered += (leftMotorSpeed - leftFiltered) * Constants.Drives.kFilter;
        rightFiltered += (rightMotorSpeed - rightFiltered) * Constants.Drives.kFilter;
        drives.setLeftRightMotorOutputs(-leftFiltered, rightFiltered);
    }
    
        /**
     * Limit motor values to the -1.0 to +1.0 range.
     */
    protected static double limit(double num) {
        if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
    }
}
