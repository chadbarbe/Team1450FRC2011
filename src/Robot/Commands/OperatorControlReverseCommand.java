package Robot.Commands;

import Robot.Commands.Ramp.ReverseRamp;
import Robot.Commands.Tongue.ReverseTongue;
import Robot.Commands.Trigger.ReverseTrigger;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 */
public class OperatorControlReverseCommand extends CommandGroup {

    public OperatorControlReverseCommand() {
        addParallel(new ReverseTongue());
        addParallel(new ReverseRamp());
        addParallel(new ReverseTrigger());
        setInterruptible(true);
    }
}
