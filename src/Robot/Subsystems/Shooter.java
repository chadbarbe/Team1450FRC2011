package Robot.Subsystems;

import Robot.Commands.Shooter.DefaultShooterCommand;
import Robot.Utils.ShooterSpeedSensor;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Shooter extends Subsystem {

    private Jaguar shooterMotor = new Jaguar(IODefines.SHOOTER_MOTOR);
    private ShooterSpeedSensor shooterSpeedSensor = new ShooterSpeedSensor();


    public Shooter() {
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
        // shooter runs in reverse direction
        shooterMotor.set(-throttle);
    }

}
