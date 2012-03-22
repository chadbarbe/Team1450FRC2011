package Robot.Commands;

import Robot.Commands.Ramp.RampOffCommand;
import Robot.Commands.Ramp.RampOnCommand;
import Robot.Commands.Shooter.DefaultShooterCommand;
import Robot.Commands.Shooter.RampUpShooterForPowerCommand;
import Robot.Commands.Trigger.PullTriggerCommand;
import Robot.Commands.Trigger.StopTriggerCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * This autonomous mode will shoot the stored balls at a pre-set speed.
 * This expects two balls to be loaded into the robot.  One ball will be in
 * the tigger position (touching the trigger but not the shooter and not the ramp).
 * The second ball will be in the ramp but not touching the trigger.
 */
public class AutonomousJustShootCommandGroup extends CommandGroup {
    private final int RAMP_UP_TIMEOUT = 4; // seconds
    private final double SHOOTER_SPEED = 0.7;
    public AutonomousJustShootCommandGroup() {
        // ramp up shooter to speed (4 secs)
        addSequential(new RampUpShooterForPowerCommand(SHOOTER_SPEED, RAMP_UP_TIMEOUT));
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
