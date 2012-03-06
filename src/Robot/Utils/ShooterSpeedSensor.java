package Robot.Utils;

import RobotMain.IODefines;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboardNamedData;

/**
 * Measures the rotation speed of the shooter and reports it to the driver
 * station in RPM.
 * 
 */
public class ShooterSpeedSensor {

    private long lastTimestamp = 0;
    private static final long MS_PER_MIN = 1000 * 60;
    private double rpm = 0;
    
    public ShooterSpeedSensor() {
        DigitalInput digitalInput = new DigitalInput(IODefines.SHOOTER_PHOTO_INTERUPTER);
        DigitalInputListener digitalInputListener = new DigitalInputListener(
                digitalInput, new SpeedSensorRisingEdgeListener(), "ShooterSpeedSensor");
        digitalInputListener.setTriggerEdges(true, true);
        digitalInputListener.setSamplePeriod(25L);
        digitalInputListener.start();
    }
    
    public double rpm() {
        return rpm;
    }
    
    public void reset() {
        lastTimestamp = 0;
        rpm = 0;
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
        SmartDashboard.putDouble("RPM",rpm);
        System.out.println("RPM=" + rpm);
    }

    private class SpeedSensorRisingEdgeListener implements DigitalInputNotify {

        public void digitalNotify(DigitalInput changedInput) {
            //no-op
            if (changedInput.get()) {
                final long currentTimeMillis = System.currentTimeMillis();
                interruptHandler(currentTimeMillis);
                lastTimestamp = currentTimeMillis;
            }
        }
    }
}
