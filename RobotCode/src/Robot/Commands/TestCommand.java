/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Commands;
import Robot.Utils.Threading;

/**
 *
 * @author parallels
 */
public class TestCommand extends CommandBase {
    
    private boolean isDone = false;

    
    public TestCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("**** INITITALIZE TEST COMMAND");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        System.out.println("**** EXECUTE TEST COMMAND");
        shooter.throttle(-0.75);
        Threading.sleep(2000);
        
        for (int x=0;x<4;x++){
            Threading.sleep(2000);
            feeder.feed();
        }
        shooter.throttle(0);
        isDone = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
