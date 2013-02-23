/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Subsystems;

import Robot.Commands.Winch.WinchOffCommand;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 * @author keenan
 */
public class Winch extends Subsystem {
    
    private Jaguar winchMotor = new Jaguar(IODefines.WINCH_MOTOR);
    
    protected void initDefaultCommand() {
        setDefaultCommand(new WinchOffCommand());
    }
    
    public void recoil() {
        winchMotor.set(0.8);
    }
    
    public void unleash()  {
        winchMotor.set(-0.8);
    }
    
    public void off()  {
        winchMotor.set(0.0);
    }
}
