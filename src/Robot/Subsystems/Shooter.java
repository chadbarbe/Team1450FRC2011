package Robot.Subsystems;

import Robot.Commands.Shooter.DefaultShooterCommand;
import Robot.Utils.ShooterSpeedSensor;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    /**
     *
     * @param throttle where throttle is between 0 and 1
     */
    public void throttle(double throttle) {
        // shooter runs in reverse direction
        shooterMotor.set(-throttle);
    }

    public void operatorControlThrottle(double throttle) {
        // if throttle is very small then make it off
        if (throttle < 0.1) {
            shooterMotor.set(0.0);
            return;
        }
        // make sure that throttle is between 0 and 1
        if (throttle < 0) throttle = 0;
        if (throttle > 1) throttle = 1;
        throttle = throttle / 2.0;
        throttle = .5 + throttle;
        System.out.println("Shooter throttle = " + throttle);
        throttle(throttle);
    }
    public double rpm() {
        return shooterSpeedSensor.rpm();
    }

}
