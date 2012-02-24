/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RobotMain;

import Robot.Utils.Joysticks;
import RobotOld.ButtonMapping;

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
    public static final int SHOOTER_PHOTO_INTERUPTER = 5;
    public static final int BALL_READY_SWITCH_DI = 6;
    public static final int WAIST_ENCODER = 7;

    //PWMs
    public static final int RIGHT_DRIVE = 1;
    public static final int LEFT_DRIVE = 2;
    public static final int SHOOTER_MOTOR = 3;
    public static final int WAIST_MOTOR = 4;
    public static final int SHOOTER_ANGLE_MOTOR = 5;
    public static final int PICKUP_SHUTE_MOTOR = 6;
    public static final int CAMERA_Z_SERVO = 7;

    //Analog Ins
    public static final int SHOOTER_ANGLE_POT = 1;

    //Solendoids
    // <NONE>

    // Joystick Defines
    public static final ButtonMapping TRIGGER_BUTTON = new ButtonMapping(Joysticks.left(),1);
    public static final ButtonMapping TONGUE_BUTTON = new ButtonMapping(Joysticks.right(),3);
    public static final ButtonMapping PICKUP_RAMP_BUTTON = new ButtonMapping(Joysticks.left(),3);

}
