/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Developer
 */
public class Autonomous
{
    public static class AutonState
    {
        public final int value;
        static final int ready_val = 0;
        static final int driveLeg1_val = 1;
        static final int kick1_val = 2;
        static final int driveLeg2_val = 3;
        static final int kick2_val = 4;
        static final int driveLeg3_val = 5;
        static final int kick3_val = 6;
        static final int done_val = 7;

        public static final AutonState ready = new AutonState(ready_val);
        public static final AutonState driveLeg1 = new AutonState(driveLeg1_val);
        public static final AutonState kick1 = new AutonState(kick1_val);
        public static final AutonState driveleg2 = new AutonState(driveLeg2_val);
        public static final AutonState kick2 = new AutonState(kick2_val);
        public static final AutonState driveLeg3 = new AutonState(driveLeg3_val);
        public static final AutonState kick3 = new AutonState(kick3_val);
        public static final AutonState done = new AutonState(done_val);

        private AutonState(int value) {
            this.value = value;
        }

    }

    private AutonState state;
    private double leg1Time;
    private double leg2Time;
    private double leg3Time;
    private double kickWait;
    private double driveSpeed;
    private Timer stepTime;
    private SWCrabDrive myDrives;
    private Kicker myKick;
    private int autonZone;

    public Autonomous(SWCrabDrive drives, Kicker kick)
    {
        state = AutonState.ready;

        leg1Time = 2.0;
        leg2Time = 1.0;
        leg3Time = leg2Time;
        kickWait = 4.0;
        driveSpeed = 0.8;
        autonZone=1;
    }

    public void run()
    {
        if(state == AutonState.ready)
        {
            state = AutonState.driveLeg1;
            myDrives.drive(driveSpeed, driveSpeed);
            stepTime.start();
        }
        else if(state == AutonState.driveLeg1)
        {
            if(stepTime.get() >= leg1Time)
            {
                state = AutonState.kick1;
                myDrives.drive(0, 0);
                stepTime.reset();
                myKick.Fire();

                if(autonZone == 1)
                    state = AutonState.done;
            }
        }
        else if(state == AutonState.kick1)
        {
            if(stepTime.get() >= kickWait)
            {
                state = AutonState.driveleg2;
                myDrives.drive(driveSpeed, driveSpeed);
                stepTime.reset();
            }
        }
        else if(state == AutonState.driveleg2)
        {
            if(stepTime.get() >= leg2Time)
            {
                state = AutonState.kick2;
                myDrives.drive(0, 0);
                stepTime.reset();
                myKick.Fire();

                if(autonZone == 2)
                    state = AutonState.done;
            }
        }
        else if(state == AutonState.kick2)
        {
            if(stepTime.get() >= kickWait)
            {
                state = AutonState.driveLeg3;
                myDrives.drive(driveSpeed, driveSpeed);
                stepTime.reset();
            }
        }
        else if(state == AutonState.driveLeg3)
        {
            if(stepTime.get() >= leg3Time)
            {
                state = AutonState.kick2;
                myDrives.drive(0, 0);
                stepTime.reset();
                myKick.Fire();
            }
        }
        else if(state == AutonState.kick3)
        {
            if(stepTime.get() >= kickWait)
            {
                state = AutonState.done;
                myDrives.drive(0, 0);
                stepTime.reset();
            }
        }
    }
    public void reset()
    {
        state = AutonState.ready;
        autonZone= 1;
    }

}
