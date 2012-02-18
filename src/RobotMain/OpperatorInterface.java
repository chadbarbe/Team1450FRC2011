package RobotMain;

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
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    Joystick rightJoystick = new Joystick(1);
    Joystick leftJoystick = new Joystick(2);
    // Button button = new JoystickButton(stick, buttonNumber);
    Button tongueButton = new JoystickButton(rightJoystick,3);
    // the driver uses the ramp motor to pick up balls
    Button driverRampButton = new JoystickButton(rightJoystick,5);
    // the shooter uses the ramp motor to deliver balls to the trigger
    Button shooterRampButton = new JoystickButton(leftJoystick,5);

    public OpperatorInterface() {
        tongueButton.whileHeld(new PickupWithTongue());
        driverRampButton.whileHeld(new RampOnCommand());
        shooterRampButton.whileHeld(new RampOnCommand());
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

}


