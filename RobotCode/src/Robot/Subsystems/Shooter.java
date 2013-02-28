package Robot.Subsystems;

import Robot.Commands.Shooter.DefaultShooterCommand;
import Robot.Utils.ShooterSpeedSensor;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Shooter extends Subsystem {

    private Jaguar shooterMotor = new Jaguar(IODefines.SHOOTER_MOTOR);
    private Jaguar shooterMotor2 = new Jaguar(IODefines.SHOOTER_MOTOR2);
    private ShooterSpeedSensor shooterSpeedSensor = new ShooterSpeedSensor();
    //private DigitalInput feederSwitch = new 

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
        shooterMotor2.set(0.0);
    }

    public void throttle(double throttle) {
        //shooterMotor.
        shooterMotor.set(-throttle);
        shooterMotor2.set(-throttle*0.8);
    }
    
    public void throttle2(double throttle) {
       // shooterMotor.set(throttle/0.8);
       // shooterMotor2.set(throttle);
    }

}
