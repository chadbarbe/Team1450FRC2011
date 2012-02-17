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
    private String name;
    private boolean notifyOnRisingEdge = true;
    private boolean notifyOnFallingEdge = false;
    private long samplePeriodInMs = Constants.LimitSwitches.loopTime;
    
    private DigitalInputListener digiInListener;
    private boolean isHigh = false;
    
    private class DigitalInputLoop implements Runnable {
        public void run() {
            if(!isHigh && !input.get()) {
                isHigh = true;
                if (notifyOnRisingEdge) digiInListener.notifyClients();
                System.out.println("InputWentHigh: " + name);
            }
            else if(isHigh && input.get() )
            {
                isHigh = false;
                if (notifyOnFallingEdge) digiInListener.notifyClients();
                System.out.println("InputWentLow: " + name);
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
    }

    public void start()
    {
        Threading.runInLoop(Constants.LimitSwitches.loopTime, new DigitalInputLoop(), name);
    }

    private void notifyClients() {
        myNotify.digitalNotify(input);
    }
    
    public void setTriggerEdges(boolean high, boolean low) {
        notifyOnRisingEdge = high;
        notifyOnFallingEdge = low;
    }

    public void setSamplePeriod(long period) {
        samplePeriodInMs = period;
    }

}
