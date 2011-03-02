/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot2011;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 * @author gferencz
 */
public class AutoDrive {
    private RobotDrive drives;
    private Encoder encoder1;
    private Encoder encoder2;
    private double curve = 0;
    private double speed = 0.5;

    AutoDrive(RobotDrive _drives, Encoder _encoderLeft, Encoder _encoderRight) {
        drives = _drives;
        encoder1 = _encoderLeft;
        encoder2 = _encoderRight;
    }

    public void gotoScoringRack() {

        encoder1.start();
        encoder1.reset();
        encoder1.setReverseDirection(true);
        encoder1.setDistancePerPulse(Constants.Drives.distancePerPulse_DEBUG_BOT);

        encoder2.start();
        encoder2.reset();
        encoder2.setDistancePerPulse(Constants.Drives.distancePerPulse_DEBUG_BOT);

        while(encoder1.getDistance() < Constants.Drives.distanceToScoringRack)
        {
            //curve = encoder2.getDistance()/encoder1.getDistance();
            drives.drive(speed,curve);
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2,
                    1, "Enc1Tks=" + encoder1.get());
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3,
                    1, "Dist=" + encoder1.getDistance());
            DriverStationLCD.getInstance().updateLCD();
        }
        drives.stopMotor();
        encoder1.stop();
        encoder1.reset();
        encoder2.stop();
        encoder2.reset();
    }

}
