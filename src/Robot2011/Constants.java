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
    public static final double elevatorDistancePerPulse = Elevator.distancePerPulse;
    public static final double elevatorDistanceToTop = Elevator.distanceToTop;
    public static final double driveDistancePerPulse = Drives.distancePerPulse;

    class Elevator {
        private static final double inchesPerShaftRotation = 0.5;
        private static final double encoderTicksPerRev = 360.0;
        private static final double encoderGearTeeth = 16.0;
        private static final double shaftGearTeeth = 21.0;
        public static final double distancePerPulse = (inchesPerShaftRotation /
                encoderTicksPerRev) * (encoderGearTeeth/shaftGearTeeth);

        public static final double distanceToTop = 42.0;
    }

    class Drives {
        private static final double inchesPerWheelRotation = 20.25;
        private static final double encoderTicksPerRev = 250.0;
        private static final double encoderGearTeeth = 12.0;
        private static final double wheelGearTeeth = 36.0;
        public static final double distancePerPulse = (inchesPerWheelRotation /
                encoderTicksPerRev) * (encoderGearTeeth/wheelGearTeeth);
    }

    class Wrist {
    
    }

}

