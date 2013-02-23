package RobotOld.Devices;

import Robot.Utils.Joysticks;
import Robot.Utils.ShooterSpeedSensor;
import Robot.Utils.Threading;
import RobotMain.Constants;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
//import edu.wpi.first.wpilibj.Victor;

/**
 * The shooter contains a motor, a speed sensor, and an angle motor.
 */
public class Shooter extends AbstractRobotDevice {
    
    SpeedController shooterMotor = new Jaguar(IODefines.SHOOTER_MOTOR);
    ShooterSpeedSensor shooterSpeedSensor = new ShooterSpeedSensor();
    Joystick joystick = Joysticks.left();
    
    //Victor angleMotor = new Victor(IODefines.SHOOTER_ANGLE_MOTOR);

    public void initialize() {
        Threading.runInLoop(Constants.LimitSwitches.loopTime, new VelocityControlLoop(), "Shooter");
        shooterSpeedSensor.reset();
    }

    public void disable() {
        shooterMotor.disable();
        //anglhueMotor.disable();
    }

    private double getUserInput() {
        return (joystick.getZ()- 1) / 2;
    }

    private class VelocityControlLoop implements Runnable {
        public void run() {
            shooterMotor.set(getUserInput());
            //angleMotor.set(joystick.getY()/2);
        }
    }
}
