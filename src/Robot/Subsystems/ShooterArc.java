package Robot.Subsystems;

import RobotMain.IODefines;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class ShooterArc extends Subsystem {

    private Victor arcMotor = new Victor(IODefines.SHOOTER_ANGLE_MOTOR);
    // todo: pot and/or limit switches for shooter arc possible?
    private AnalogChannel pot = new AnalogChannel(IODefines.SHOOTER_ANGLE_POT);
    private static final double MAX_VOLTAGE = 5.0;

    protected void initDefaultCommand() {
    }

    public void setArcSpeed(double arc) {
        // use half power
        arcMotor.set(arc);
    }

    // todo: this should be a pid input
    public double getShooterAnglePercentage() {
        return pot.getAverageVoltage() / MAX_VOLTAGE;
    }
}
