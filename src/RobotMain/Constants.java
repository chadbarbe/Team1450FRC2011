/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RobotMain;

/**
 *
 * @author chad
 */
public class Constants {

    public class Drives {
        // Private constants
        private static final double inchesPerWheelRotation = 20.25;
        private static final double encoderTicksPerRev = 250.0;
        private static final double encoderGearTeeth = 12.0;
        private static final double wheelGearTeeth = 26.0;
        private static final double startToDriverStation = 216.4;
        private static final double scoringPegLength = 19.5;
        private static final double robotPegClearance = 10;
        

        // Public constants
        public static final double distancePerPulse = (inchesPerWheelRotation /
                encoderTicksPerRev) * (encoderGearTeeth/wheelGearTeeth);
        public static final double distancePerPulse_DEBUG_BOT = (inchesPerWheelRotation /
                encoderTicksPerRev);
        public static final double distanceToScoringRack = (startToDriverStation - 
                (scoringPegLength + robotPegClearance));
        public static final long loopTime = 50;
        public static final double distanceBackFromScoringRack = distanceToScoringRack - 36;
        public static final double kFilter = 0.3;
    }

    public class Solenoid {
        public static final long loopTime = 50;
    }

    public class LimitSwitches
    {
        public static final long loopTime = 50;
    }

    public class MiniBotDeployment
    {
        public static final long loopTime = 50;
        public static final double speed = 0.5;
        public static final long enableAfterTime = 105000;
    }

}

