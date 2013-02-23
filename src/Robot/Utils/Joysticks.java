package Robot.Utils;

import edu.wpi.first.wpilibj.Joystick;

/**
 */
public class Joysticks {
    private static final Joystick right = new Joystick(1);
    private static final Joystick left = new Joystick(2);
    /** originally switched around*/
    public static Joystick right() {
        return left;
    }
    
    public static Joystick left() {
        return right;
    }
}
/** originally switched around*/