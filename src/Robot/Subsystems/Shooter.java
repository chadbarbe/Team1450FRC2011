package Robot.Subsystems;

import Robot.Commands.DefaultShooterCommand;
import Robot.Utils.ShooterSpeedSensor;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Shooter extends Subsystem {

    private Jaguar shooterMotor = new Jaguar(IODefines.SHOULDER_MOTOR);
    private Victor armMotor = new Victor(IODefines.SHOOTER_ANGLE_MOTOR);
    private ShooterSpeedSensor shooterSpeedSensor = new ShooterSpeedSensor();
    private Relay triggerRelay = new Relay(IODefines.TRIGGER_MOTOR);

    protected void initDefaultCommand() {
        setDefaultCommand(new DefaultShooterCommand());
    }

    public void resetSpeedSensor() {
        shooterSpeedSensor.reset();
    }
    
    public void dontSpin() {
        shooterMotor.set(0.0);
    }

    public void throttle(double throttle) {
        shooterMotor.set(throttle);
    }
}
