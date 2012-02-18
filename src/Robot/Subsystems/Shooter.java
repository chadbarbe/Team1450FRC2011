package Robot.Subsystems;

import Robot.Commands.Shooter.DefaultShooterCommand;
import Robot.Utils.ShooterSpeedSensor;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Shooter extends Subsystem {

    private Jaguar shooterMotor = new Jaguar(IODefines.SHOOTER_MOTOR);
    private Victor armMotor = new Victor(IODefines.SHOOTER_ANGLE_MOTOR);
    private ShooterSpeedSensor shooterSpeedSensor = new ShooterSpeedSensor();
    private Relay triggerRelay = new Relay(IODefines.TRIGGER_RELAY);

    public Shooter() {
        triggerRelay.setDirection(Relay.Direction.kForward);
    }

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

    public void triggerOn() {
        triggerRelay.set(Relay.Value.kOn);
    }
    
    public void triggerOff() {
        triggerRelay.set(Relay.Value.kOff);
    }
}
