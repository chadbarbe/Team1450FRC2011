package Robot.Commands;

import Robot.Commands.Ramp.RampOnCommand;
import Robot.Commands.Shooter.RampUpShooterForTargetCommand;
import Robot.Commands.Trigger.PullTriggerCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousTurnAndShootCommandGroup extends CommandGroup {
    public AutonomousTurnAndShootCommandGroup() {
        // turn to face target
        FaceTargetCommand faceTargetCommand = new FaceTargetCommand();
        addSequential(faceTargetCommand);
        // set shooter speed based on target distance
        addSequential(new RampUpShooterForTargetCommand(faceTargetCommand));
        // pull trigger
        addParallel(new PullTriggerCommand());
        // & turn on ramp
        addParallel(new RampOnCommand());
    }
}
