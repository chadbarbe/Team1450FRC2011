/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RobotMain;

import Robot.Utils.Joysticks;

/**
 *
 * @author chad
 */
public class IODefines {
    //Digitals Outs
    public static final int TONGUE_RELAY = 1;
    public static final int CAMERA_XY_SERVO = 1;
    public static final int CAMERA_Z_SERVO = 2;
    public static final int TRIGGER_RELAY = 3;

    //Digital Ins
    public static final int LEFT_DRIVE_ENCODER_A = 13;
    public static final int LEFT_DRIVE_ENCODER_B = 14;
    public static final int RIGHT_DRIVE_ENCODER_A = 11;
    public static final int RIGHT_DRIVE_ENCODER_B = 12;
    public static final int BALL_READY_SWITCH_DI = 10;
    public static final int SHOOTER_PHOTO_INTERUPTER = 9;

    //PWMs
    public static final int LEFT_DRIVE = 1;
    public static final int RIGHT_DRIVE = 2;
    public static final int SHOOTER_DRIVE = 3;
    public static final int PICKUP_SHUTE_MOTOR = 4;
    public static final int SHOOTER_ANGLE_MOTOR = 5;
    public static final int SHOULDER_MOTOR = 6;

    //Analog Ins
    // <NONE>

    //Solendoids
    // <NONE>

    // Joystick Defines
    public static final ButtonMapping TRIGGER_BUTTON = new ButtonMapping(Joysticks.left(),1);
    public static final ButtonMapping TONGUE_BUTTON = new ButtonMapping(Joysticks.right(),3);
    public static final ButtonMapping PICKUP_RAMP_BUTTON = new ButtonMapping(Joysticks.left(),3);

}
