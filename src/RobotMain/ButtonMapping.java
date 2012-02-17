package RobotMain;

import edu.wpi.first.wpilibj.Joystick;

/**
 * A button mapping
 */
public class ButtonMapping {
    public final Joystick joystick;
    public final int button;

    public ButtonMapping(Joystick joystick, int button) {
        this.button = button;
        this.joystick = joystick;
    }
}
