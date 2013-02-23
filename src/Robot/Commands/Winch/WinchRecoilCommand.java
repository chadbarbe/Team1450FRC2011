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
public class WinchRecoilCommand extends CommandBase {

    public WinchRecoilCommand() {
        requires(winch);
    }

    protected void initialize() {
    }

    protected void execute() {
        winch.recoil();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
