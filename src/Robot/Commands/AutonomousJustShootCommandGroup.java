package Robot.Commands;

import Robot.Commands.Ramp.RampOffCommand;
import Robot.Commands.Ramp.RampOnCommand;
import Robot.Commands.Shooter.DefaultShooterCommand;
import Robot.Commands.Shooter.RampUpShooterForPowerCommand;
import Robot.Commands.Trigger.PullTriggerCommand;
import Robot.Commands.Trigger.StopTriggerCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonomousJustShootCommandGroup extends CommandGroup {
    public AutonomousJustShootCommandGroup() {
        // ramp up shooter to speed
        addSequential(new RampUpShooterForPowerCommand(0.75));
        // then shoot all the balls
        addParallel(new PullTriggerCommand());
        addSequential(new WaitCommand(4));
        addParallel(new RampOnCommand());
        // turn off after 5 seconds
        addSequential(new WaitCommand(5));
        addParallel(new RampOffCommand());
        addParallel(new StopTriggerCommand());
        addParallel(new DefaultShooterCommand());
    }
}
