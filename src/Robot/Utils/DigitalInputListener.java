/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot.Utils;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author chad
 */
public class DigitalInputListener {
    private DigitalInput input;
    private DigitalInputNotify myNotify;
    private Thread m_task;

    private class DigitalInputListenerThread extends Thread {

        private DigitalInputListener digiInListener;
        private DigitalInput input;
        private boolean m_run = true;

        DigitalInputListenerThread(DigitalInputListener _digiInListener, DigitalInput _input) {
            digiInListener = _digiInListener;
            input = _input;
        }

        public void run() {
            input.requestInterrupts();
            while (m_run) {
                input.waitForInterrupt(300);
                digiInListener.notifyClients();
            }
        }
    }

    DigitalInputListener(DigitalInput _input,
                         DigitalInputNotify _myNotify)
    {
        input = _input;
        myNotify = _myNotify;
        m_task = new DigitalInputListenerThread(this, input);
    }

    public void start()
    {
        m_task.start();
    }

    private void notifyClients() {
        myNotify.digitalNotify();
    }

}
