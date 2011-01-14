/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;

public class Kicker {
    public static class KickerState
    {
        public final int value;
        static final int idle_val = 0;
        static final int arming_val = 1;
        static final int latching_val = 2;
        static final int armed_val = 3;
        static final int resettingArmCyls_val = 4;
        static final int firing_val = 3;

        public static final KickerState idle = new KickerState(idle_val);
        public static final KickerState arming = new KickerState(arming_val);
        public static final KickerState latching = new KickerState(latching_val);
        public static final KickerState armed = new KickerState(armed_val);
        public static final KickerState resettingArmCyls = new KickerState(resettingArmCyls_val);
        public static final KickerState firing = new KickerState(firing_val);


        private KickerState(int value) {
            this.value = value;
        }
    }

    public static class AnkleState
    {
        public final int value;
        static final int idle_val = 0;
        static final int moving_val = 1;

        public static final AnkleState idle = new AnkleState(idle_val);
        public static final AnkleState moving = new AnkleState(moving_val);

        private AnkleState(int value) {
            this.value = value;
        }
    }

    private Solenoid ankleUp, ankleDown, currentAnkleActuator;
    private Solenoid LoadKicker, clearKickPath, kickerLatch, kickerRelease;

    //Solenoid bumper ports
    private int m_ankleUpPort=4;
    private int m_ankleDownPort=3;
    private int m_LoadKickerPort=2;
    private int m_clearKickPathPort=1;
    private int m_kickerLatchPort=6;
    private int m_kickerReleasePort = 5;

    //Digital sidecar ports
    private int m_armLimitSwitchPort = 1;

    private double armResetTime = 1.0;
    private double latchingTime = 1.0;
    private double reArmDelay = 0.5;
    private double ankleMoveTime = 1.0;
    private Joystick m_controlpad;
    private KickerState currentState;
    private AnkleState ankleState;
    private Timer stateTimer, ankleTimer;
    private DigitalInput armLimitSwitch;

    public Kicker(Joystick control)
    {
        ankleUp = new Solenoid(m_ankleUpPort);
        ankleDown = new Solenoid(m_ankleDownPort);
        LoadKicker = new Solenoid(m_LoadKickerPort);
        clearKickPath = new Solenoid(m_clearKickPathPort);
        kickerLatch = new Solenoid(m_kickerLatchPort);
        kickerRelease = new Solenoid(m_kickerReleasePort);
        ankleState = AnkleState.idle;
        m_controlpad = control;
        stateTimer = new Timer();
        ankleTimer = new Timer();

        armLimitSwitch = new DigitalInput(m_armLimitSwitchPort);

        if(!armLimitSwitch.get())
            currentState = KickerState.armed;
        else
            currentState = KickerState.idle;
    }

    public void Arm()
    {
        currentState = KickerState.arming;
        System.out.println("[Kicker] Arming");
        clearKickPath.set(false);
        kickerLatch.set(false);
        LoadKicker.set(true);
        kickerRelease.set(true);
        stateTimer.reset();
        stateTimer.start();
    }

    public void Fire()
    {
        if(currentState == KickerState.armed)
        {
            kickerRelease.set(true);
            stateTimer.reset();
            stateTimer.start();
            currentState = KickerState.firing;

            System.out.println("[Kicker] Firing");
        }
    }

    private void CheckIntermediateStates()
    {
        ArmingProcedure();
        
        FiringProcedure();

        AnkleMovingProcedure();
    }

    private void ArmingProcedure()
    {
        if(currentState == KickerState.arming)
        {
            if(!armLimitSwitch.get()) // pressed
            {
                LoadKicker.set(false);
                kickerRelease.set(false);
                currentState = KickerState.latching;
                System.out.println("[Kicker] Latching");

                System.out.println("Arm time: " + stateTimer.get());

                kickerLatch.set(true);
            }
        }
        else if (currentState == KickerState.latching)
        {
            double runningTime = stateTimer.get();
            System.out.println("[Kicker] timer: " + runningTime);
            if(runningTime >= latchingTime)
            {
                kickerLatch.set(false);
                currentState = KickerState.resettingArmCyls;
                System.out.println("[Kicker] Resetting Arming Cylinders");

                clearKickPath.set(true);
                stateTimer.reset();
            }
        }
        else if(currentState == KickerState.resettingArmCyls)
        {
            double runningTime = stateTimer.get();
            if(runningTime >= armResetTime)
            {
                clearKickPath.set(false);
                stateTimer.stop();
                stateTimer.reset();
                currentState = KickerState.armed;
                System.out.println("[Kicker] Armed");
            }
        }
    }

    private void FiringProcedure()
    {
        if(currentState == KickerState.firing)
        {
            double runningTime = stateTimer.get();
            if(runningTime >= reArmDelay)
            {
                kickerRelease.set(false);
                stateTimer.stop();
                stateTimer.reset();
                currentState = KickerState.idle;
                System.out.println("[Kicker] Fired");
            }
        }
    }

    private void AnkleMovingProcedure()
    {
        if((ankleState == AnkleState.moving) && (ankleTimer.get() >= ankleMoveTime))
        {
            currentAnkleActuator.set(false);
            ankleState = AnkleState.idle;
            ankleTimer.stop();
        }
    }

    private void MoveAnkle(Solenoid ankleSolenoid)
    {
       if(ankleState == AnkleState.idle)
       {
           currentAnkleActuator = ankleSolenoid;
           currentAnkleActuator.set(true);
           ankleTimer.reset();
           ankleTimer.start();
           ankleState = AnkleState.moving;
       }
    }
    public void Run()
    {
        //ManualControl();
        System.out.println("0 Kick limit: " + armLimitSwitch.get() + " kickerState: " + currentState.value);
        CheckIntermediateStates();
        System.out.println("1 Kick limit: " + armLimitSwitch.get() + " kickerState: " + currentState.value);

        if(currentState == KickerState.idle)
        {
            Arm();
        }
        else if(m_controlpad.getTrigger())
        {
            Fire();
        }
        System.out.println("2 Kick limit: " + armLimitSwitch.get() + " kickerState: " + currentState.value);

        if(m_controlpad.getRawButton(2))
        {
            MoveAnkle(ankleDown);
        }
        else if(m_controlpad.getRawButton(3))
        {
            MoveAnkle(ankleUp);
        }
    }

    private void ManualControl()
    {
       if(m_controlpad.getRawButton(2))
       {
           ankleUp.set(true);
       }
       else if(m_controlpad.getRawButton(3))
       {
           ankleDown.set(true);
       }
       else if(m_controlpad.getRawButton(4))
       {
           LoadKicker.set(true);
       }
       else if(m_controlpad.getRawButton(5))
       {
           clearKickPath.set(true);
       }
       else if(m_controlpad.getRawButton(6))
       {
           kickerLatch.set(true);
       }
       else if(m_controlpad.getRawButton(7))
       {
           kickerRelease.set(true);
       }
       else
       {
           ankleUp.set(false);
           ankleDown.set(false);
           LoadKicker.set(false);
           clearKickPath.set(false);
           kickerLatch.set(false);
           kickerRelease.set(false);
       }
    }

    public void Reset()
    {
        if(!armLimitSwitch.get())
            currentState = KickerState.armed;
        else
            currentState = KickerState.idle;

        System.out.println("[Reset] kickerState: " + currentState.value);

        LoadKicker.set(false);
        kickerRelease.set(false);
        clearKickPath.set(true);
        kickerLatch.set(true);
    }
}
