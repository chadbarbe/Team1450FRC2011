package RobotMain;

import Robot.Commands.Camera.CameraPickupCommand;
import Robot.Commands.Camera.CameraTargetCommand;
import Robot.Commands.Arm.MoveArm;
import Robot.Commands.Winch.WinchRecoilCommand;
import Robot.Commands.Winch.WinchUnleashCommand;
import Robot.Commands.Feeder.FeederFeedCommand;
import Robot.Commands.Feeder.FeederUnfeedCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OperatorInterface {
    // The right joystick is the driving joystick
    Joystick rightJoystick = new Joystick(1);
    // The left joystick is the shooting joystick
    Joystick leftJoystick = new Joystick(2);

    // The tongue button runs the tongue motor on
    //Button tongueButton = new JoystickButton(rightJoystick,3);
    Button winchRecoilButton = new JoystickButton(rightJoystick,3);

    // the driver uses the ramp motor to pick up balls
    //Button driverRampButton = new JoystickButton(rightJoystick,5);
    Button winchUnleashButton = new JoystickButton(rightJoystick,5);

    // the shooter uses the ramp motor to deliver balls to the trigger
    Button shooterRampButton = new JoystickButton(leftJoystick,5);

    // shoot the ball
    Button triggerButton = new JoystickButton(leftJoystick,1);
    
    Button cameraPickupButton = new JoystickButton(rightJoystick,9);
    Button cameraTargetButton = new JoystickButton(leftJoystick,9);

    Button shooterArcIncrementButton = new JoystickButton(leftJoystick,6);
    Button shooterArcDecrementButton = new JoystickButton(leftJoystick,7);
    
    Button feedButton = new JoystickButton(leftJoystick, 3);
    Button unfeedButton = new JoystickButton(leftJoystick, 2);

    public OperatorInterface() {
        winchRecoilButton.whileHeld(new WinchRecoilCommand());
        winchUnleashButton.whileHeld(new WinchUnleashCommand());
        cameraPickupButton.whenPressed(new CameraPickupCommand());
        cameraTargetButton.whenPressed(new CameraTargetCommand());
        feedButton.whileHeld(new FeederFeedCommand());
        feedButton.whileHeld(new FeederUnfeedCommand());
    }

    public double getDriveRotation() {
        return -leftJoystick.getX();
    }

    public double getDriveThrottle() {
        return leftJoystick.getY();
    }

    public double getShooterThrottle() {
        return (-leftJoystick.getZ() + 1.0) / 2.0;
    }
    
    public double getShooter2Throttle() {
        return (-rightJoystick.getZ() + 1.0) / 2.0;
    }
    
    public double getArmRotation() {
        return rightJoystick.getY();
    }
}


