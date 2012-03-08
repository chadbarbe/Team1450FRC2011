package Robot.Commands;

import Robot.Commands.Camera.WaitForTargetCommand;
import Robot.Commands.Ramp.RampOnCommand;
import Robot.Commands.Shooter.RampUpShooterForTargetCommand;
import Robot.Commands.Trigger.PullTriggerCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousShootBallCommandGroup extends CommandGroup {
    public AutonomousShootBallCommandGroup() {
        // get target from camera
        WaitForTargetCommand waitForTargetCommand = new WaitForTargetCommand();
        addSequential(waitForTargetCommand);
        // todo: turn to face target instead?
        // set shooter speed based on target distance
        addSequential(new RampUpShooterForTargetCommand(waitForTargetCommand));
        // pull trigger
        addParallel(new PullTriggerCommand());
        // & turn on ramp
        addParallel(new RampOnCommand());
    }
}
