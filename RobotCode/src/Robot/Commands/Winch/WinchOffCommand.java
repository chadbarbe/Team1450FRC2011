/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Commands.Winch;

import Robot.Commands.CommandBase;

/**
 *
 * @author keenan
 */
public class WinchOffCommand extends CommandBase {

    public WinchOffCommand() {
        requires(winch);
    }

    protected void initialize() {
    }

    protected void execute() {
        winch.off();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
