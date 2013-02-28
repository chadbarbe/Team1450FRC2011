/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RobotMain;

import Robot.Utils.Joysticks;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 *
 * @author chad
 */
public class IODefines {
    //Relays
    public static final int TONGUE_RELAY = 3;
    public static final int TRIGGER_RELAY = 5;

    //Digital Ins
    public static final int RIGHT_DRIVE_ENCODER_A = 1;
    public static final int RIGHT_DRIVE_ENCODER_B = 2;
    public static final int LEFT_DRIVE_ENCODER_A = 3;
    public static final int LEFT_DRIVE_ENCODER_B = 4;
    public static final int FEEDER_LIMIT_SWITCH = 8;
    public static final int BALL_READY_SWITCH_DI = 6;
    public static final int WAIST_ENCODER = 7;
    public static final int SHOOTER_SPEED_ENCODER = 12;
    
    
    //PWMs
    public static final int RIGHT_DRIVE = 1;
    public static final int LEFT_DRIVE = 2;
    public static final int SHOOTER_MOTOR = 3;
    public static final int SHOOTER_MOTOR2 = 4;
    public static final int ARM_MOTOR = 5;
    public static final int WINCH_MOTOR = 6;
    public static final int FEEDER_MOTOR = 7/*4*/;  //feeder
    public static final int CAMERA_Z_SERVO = 10/*7*/;

    //Analog Ins
    public static final int SHOOTER_ANGLE_POT = 1;

    //Solendoids
    // <NONE>

    // Joystick Defines
    public static final JoystickButton TRIGGER_BUTTON = new JoystickButton(Joysticks.left(),1);
    public static final JoystickButton TONGUE_BUTTON = new JoystickButton(Joysticks.right(),3);
    public static final JoystickButton PICKUP_RAMP_BUTTON = new JoystickButton(Joysticks.left(),3);

}
