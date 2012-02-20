package RobotMain;

import Robot.Commands.Camera.CameraPickupCommand;
import Robot.Commands.Camera.CameraTargetCommand;
import Robot.Commands.Shooter.ShootBallCommand;
import Robot.Commands.Tongue.PickupWithTongue;
import Robot.Commands.Ramp.RampOnCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OpperatorInterface {
    // The right joystick is the driving joystick
    Joystick rightJoystick = new Joystick(1);
    // The left joystick is the shooting joystick
    Joystick leftJoystick = new Joystick(2);

    // Button button = new JoystickButton(stick, buttonNumber);

    // The tongue button runs the tongue motor on
    Button tongueButton = new JoystickButton(rightJoystick,3);

    // the driver uses the ramp motor to pick up balls
    Button driverRampButton = new JoystickButton(rightJoystick,5);

    // the shooter uses the ramp motor to deliver balls to the trigger
    Button shooterRampButton = new JoystickButton(leftJoystick,5);

    // shoot the ball
    Button triggerButton = new JoystickButton(leftJoystick,1);
    
    Button cameraPickupButton = new JoystickButton(rightJoystick,9);
    Button cameraTargetButton = new JoystickButton(leftJoystick,9);

    public OpperatorInterface() {
        tongueButton.whileHeld(new PickupWithTongue());
        driverRampButton.whileHeld(new RampOnCommand());
        shooterRampButton.whileHeld(new RampOnCommand());
        triggerButton.whileHeld(new ShootBallCommand());
        cameraPickupButton.whenPressed(new CameraPickupCommand());
        cameraTargetButton.whenPressed(new CameraTargetCommand());
    }

    public double getDriveRotation() {
        return rightJoystick.getX();
    }

    public double getDriveThrottle() {
        return rightJoystick.getY();
    }

    public double getShooterThrottle() {
        return (leftJoystick.getZ() + 1.0) / 2.0;
    }
    
    public double getShoulderRotation() {
        return leftJoystick.getX();
    }

    public double getArc() {
        return leftJoystick.getY();
    }
}


