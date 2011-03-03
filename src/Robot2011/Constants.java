/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot2011;

/**
 *
 * @author chad
 */
public class Constants {
    //public static final double elevatorDistancePerPulse = Elevator.distancePerPulse;
    //public static final double elevatorDistanceToTop = Elevator.distanceToTop;
    //public static final double driveDistancePerPulse = Drives.distancePerPulse;

    public class Elevator {
        // Private constants
        private static final double inchesPerShaftRotation = 0.5;
        private static final double encoderTicksPerRev = 360.0;
        private static final double encoderGearTeeth = 16.0;
        private static final double shaftGearTeeth = 21.0;

        // Public constants
        public static final double distanceToTop = 42.0;
        public static final double lowerLimit = 0;
        public static final double upperLimit = distanceToTop;
        public static final double initialPosition = 0;
        public static final double distancePerPulse = (inchesPerShaftRotation /
                encoderTicksPerRev) * (encoderGearTeeth/shaftGearTeeth);
        public static final double scoringPosition = 10.0;
        public static final long loopTime = 50;
    }

    public class Drives {
        // Private constants
        private static final double inchesPerWheelRotation = 20.25;
        private static final double encoderTicksPerRev = 250.0;
        private static final double encoderGearTeeth = 12.0;
        private static final double wheelGearTeeth = 26.0;

        // Public constants
        public static final double distancePerPulse = (inchesPerWheelRotation /
                encoderTicksPerRev) * (encoderGearTeeth/wheelGearTeeth);
        public static final double distancePerPulse_DEBUG_BOT = (inchesPerWheelRotation /
                encoderTicksPerRev);
        public static final double distanceToScoringRack = 200;
        public static final long loopTime = 50;

        public static final double maxVelocity = 75;
    }

    public class Wrist {
        public static final double lowerLimitPotVal = 215;
        public static final double upperLimitPotVal = 770;
        public static final double initialPosition = 750;
        public static final double straightPosition = 379;
        public static final double scoringPosition = 280;
        public static final double upPosition = 600;
        public static final double potRange = upperLimitPotVal - lowerLimitPotVal;
        public static final long loopTime = 50;
    }

    public class Solenoid {
        public static final long loopTime = 50;
    }

    public class LimitSwitches
    {
        public static final long loopTime = 50;
    }

}

