package Robot.Commands;

import Robot.Commands.Ramp.RampOnCommand;
import Robot.Commands.Shooter.RampUpShooterForPowerCommand;
import Robot.Commands.Trigger.PullTriggerCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousJustShootCommandGroup extends CommandGroup {
    public AutonomousJustShootCommandGroup() {
        // ramp up shooter to speed
        addSequential(new RampUpShooterForPowerCommand(0.5));
        // then shoot all the balls
        addParallel(new RampOnCommand());
        addParallel(new PullTriggerCommand());
    }
}
