/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot.Utils;
import RobotMain.Constants;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author chad
 */
public class DigitalInputListener {
    private DigitalInput input;
    private DigitalInputNotify myNotify;
    private Thread m_task;
    private String name;

    private class DigitalInputListenerThread extends Thread {

        private DigitalInputListener digiInListener;
        private DigitalInput input;
        private boolean m_run = true;
        private boolean isHigh = false;

        DigitalInputListenerThread(DigitalInputListener _digiInListener, DigitalInput _input) {
            digiInListener = _digiInListener;
            input = _input;
        }

        public void run() {
            while (m_run) {
               if(!isHigh && !input.get()) {
                   isHigh = true;
                   digiInListener.notifyClients();
                   System.out.println("InputWentHigh: " + name);
               }
               else if(isHigh && input.get() )
               {
                   isHigh = false;
                   System.out.println("InputWentLow: " + name);
               }

                try {
                Thread.sleep(Constants.LimitSwitches.loopTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public DigitalInputListener(DigitalInput _input,
                         DigitalInputNotify _myNotify,
                         String _name)
    {
        input = _input;
        myNotify = _myNotify;
        name = _name;
        m_task = new DigitalInputListenerThread(this, input);
    }

    public void start()
    {
        m_task.start();
    }

    private void notifyClients() {
        myNotify.digitalNotify(input);
    }

}
