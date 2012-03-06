package Robot.Commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class TrackTargetCommand extends CommandGroup {
    public TrackTargetCommand() {
        setInterruptible(false);
        // wait for new target
        addSequential(new WaitForTargetCommand());
        // get angle, distance, offset
        // turn waist by angle and update shooter for distance/offset
        addParallel(new TurnWaistToTargetCommand());
        addParallel(new RampUpShooterForTargetCommand());
    }
}
