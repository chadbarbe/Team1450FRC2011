package Robot.Commands;

import Robot.Commands.Ramp.RampOffCommand;
import Robot.Commands.Ramp.ReverseRamp;
import Robot.Commands.Tongue.ReverseTongue;
import Robot.Commands.Tongue.StopTongue;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 */
public class AutonomousFeedBallsCommandGroup extends CommandGroup {
    public AutonomousFeedBallsCommandGroup() {
        // move ramp and pickup backwards
        addParallel(new ReverseTongue());
        addParallel(new ReverseRamp());
        // wait for timeout
        addSequential(new WaitCommand(5));
        // stop all motors
        addParallel(new StopTongue());
        addParallel(new RampOffCommand());
    }
}
