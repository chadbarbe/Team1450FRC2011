/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Utils;

import RobotMain.IODefines;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author Programer
 */
public class BallReadySwitch {
    private final DigitalInput digitalInput;
    
    public interface BallReadyListener {
        public void ballReady(boolean ready);
    }
    private class NullBallReadyListener implements BallReadyListener {

        public void ballReady(boolean ready) {
            //no-op
        }
    }
    
    private DigitalInputListener digitalInputListener;
    private BallReadyListener ballReadyListener = new NullBallReadyListener();
    
    public BallReadySwitch() {
        digitalInput = new DigitalInput(IODefines.BALL_READY_SWITCH_DI);
        DigitalInputNotify diListener = new DigitalInputNotify() {

            public void digitalNotify(DigitalInput changedInput) {
                ballReadyListener.ballReady(isBallReady());
            }
        };
        
        digitalInputListener = new DigitalInputListener(digitalInput, diListener, "ballReadySwitch");
        digitalInputListener.start();
    }
    
    public boolean isBallReady() {
        return !digitalInput.get();
    }
    
    public void setBallReadyListener(BallReadyListener _ballReadyListener) {
        ballReadyListener = _ballReadyListener;
    }
}
