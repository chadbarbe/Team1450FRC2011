package Robot.Subsystems;

import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class Arm extends Subsystem {

    private Victor armMotor = new Victor(IODefines.ARM_MOTOR);

    protected void initDefaultCommand() {
    }

    public void operatorControl(double armPower) {
        armPower = -armPower;
        if (armPower < 0) {
            armPower /= 2;
        } else {
//            armPower /= 6;
        }
        armMotor.set(armPower);
    }

    public void off() {
        armMotor.set(0.0);
    }
}
