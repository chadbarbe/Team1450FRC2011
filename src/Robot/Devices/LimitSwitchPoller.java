/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot.Devices;

import Robot2011.IODefines;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author gferencz
 */
public class LimitSwitchPoller {

    private Thread m_thread;
    private LimitSwitchPoller poller;

    private Elevator elevator;
    private Wrist wrist;

    private DigitalInput elevatorLimitUp = new DigitalInput(IODefines.ELEVATOR_LIMIT_UP);
    private DigitalInput elevatorLimitDown = new DigitalInput(IODefines.ELEVATOR_LIMIT_DOWN);
    private DigitalInput wristLimitUp = new DigitalInput(IODefines.WRIST_LIMIT_UP);
    private DigitalInput wristLimitDown = new DigitalInput(IODefines.WRIST_LIMIT_DOWN);

    public LimitSwitchPoller(Elevator _elevator, Wrist _wrist){
        m_thread = new LimitSwitchThread(this);
        elevator = _elevator;
        wrist = _wrist;
    }

    private class LimitSwitchThread extends Thread {

        private boolean m_run = true;

        LimitSwitchThread(LimitSwitchPoller _poller) {
            poller = _poller;
        }

        public void run() {
            while (m_run) {
                poller.run();
            }
        }
    }

    private void run() {
        if (elevatorLimitUp.get()) {
            elevator.atUpperLimit();
        }
        if (elevatorLimitDown.get()) {
            elevator.atLowerLimit();
        }
        if (wristLimitUp.get()) {
            wrist.atUpperLimit();
        }
        if (wristLimitDown.get()) {
            wrist.atLowerLimit();
        }

    }

    public void start() {
        m_thread.start();
    }

}
