/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Subsystems;

import Robot.Commands.Arm.MoveArm;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 * @author keenan
 */
public class Arm extends Subsystem {
    
    private Jaguar armMotor = new Jaguar(IODefines.ARM_MOTOR);
    
    public Arm() {
    }
        
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveArm());
    }
    
    public void turnArm(double rotation) {
        armMotor.set(rotation);
    }
    
    public void off()  {
        armMotor.set(0.0);
    }
}
