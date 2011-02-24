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
    public static final double inchesPerShaftRotation = 0.5;
    public static final double encoderTicksPerRev = 360.0;
    public static final double encoderGearTeeth = 16.0;
    public static final double shaftGearTeeth = 21.0;
    public static final double elevatorDistancePerPulse = (inchesPerShaftRotation / encoderTicksPerRev) * (encoderGearTeeth/shaftGearTeeth);

}
