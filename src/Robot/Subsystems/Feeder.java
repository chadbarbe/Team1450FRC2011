/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Subsystems;

import Robot.Commands.Feeder.FeederOffCommand;
import Robot.Utils.Threading;
import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
/**
 *
 * @author keenan
 */
public class Feeder extends Subsystem {
    
    private Jaguar feederMotor = new Jaguar(IODefines.FEEDER_MOTOR);
    private DigitalInput feederLimit = new DigitalInput(IODefines.FEEDER_LIMIT_SWITCH);
    boolean feeding;
    
    protected void initDefaultCommand() {
        setDefaultCommand(new FeederOffCommand());
    }
    
    public void feed() {
        
        //feederLimit.
        feederMotor.set(-.7);
        System.out.println("Feeder Limit Switch: " + feederLimit.get());
        if (!feeding){
            feeding = true;
            feederMotor.set(-.7);
            while(feederLimit.get()){
                Threading.sleep(10);
            }
            
            while(!feederLimit.get()){
                Threading.sleep(10);
            }
            feederMotor.set(0);
            feeding = false;
        }
        
    }
    
    public void unfeed()  {
        feederMotor.set(.7);
    }
    
    private void off()  {
      //  feederMotor.set(0.0);
    }
}
