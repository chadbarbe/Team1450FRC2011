package Robot.Utils;

import RobotMain.IODefines;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 * Measures the rotation speed of the shooter and reports it to the driver
 * station in RPM.
 * 
 */
public class ShooterSpeedSensor {
    
    private DigitalInput digitalInput;
    private long lastTimestamp = 0;
    private static final long MS_PER_MIN = 1000 * 60;
    private static final long TIMEOUT_IN_SECONDS = 10L;
    private static final long TIMEOUT_IN_MS = TIMEOUT_IN_SECONDS * 1000;
    private double rpm = 0;
    
    private Thread mThread = new Thread() {

        public void run() {
            while (true) {
                
                digitalInput.waitForInterrupt(TIMEOUT_IN_SECONDS);
                final long currentTimeMillis = System.currentTimeMillis();
                // todo: how to check if this was an interrupt or a timeout?
                if (Math.abs(currentTimeMillis - lastTimestamp - TIMEOUT_IN_MS) > 500L) {
                    // this was a timeout
                } else {
                    interruptHandler(currentTimeMillis); 
                }
                lastTimestamp = currentTimeMillis;
            }
        }
    };
    
    public ShooterSpeedSensor() {
        digitalInput = new DigitalInput(IODefines.SHOOTER_PHOTO_INTERUPTER);
        digitalInput.setUpSourceEdge(false, true);
        digitalInput.enableInterrupts();
        mThread.start();
    }
    
    private void interruptHandler(long currentTimeMillis) {
        if (lastTimestamp != currentTimeMillis) {
            if (lastTimestamp != 0.0) {
                // this is NOT the first reading     
                long periodInMs = currentTimeMillis - lastTimestamp;
                double rpm = MS_PER_MIN / (double)periodInMs;
                updateRpm(rpm);
            }
        }
    }

    private void updateRpm(double _rpm) {
        rpm = _rpm;
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, "RPM=" + rpm);
        // todo: notify listeners
    }
}
