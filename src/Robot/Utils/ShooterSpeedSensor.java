package Robot.Utils;

import RobotMain.IODefines;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Measures the rotation speed of the shooter and reports it to the driver
 * station in RPM.
 * 
 */
public class ShooterSpeedSensor {

    private long lastTimestamp = 0;
    private static final long MS_PER_MIN = 1000 * 60;
    private static final long MS_PER_SECOND = 1000;
    private double rpm = 0;
    private Counter counter = new Counter(IODefines.SHOOTER_PHOTO_INTERUPTER);
    private int lastCount = 0;
    
    public ShooterSpeedSensor() {
        counter.setUpSourceEdge(true,false);
        counter.start();
        Threading.runInLoop(25L,new Runnable() {
            public void run() {
                int count = counter.get();
                long timestamp = System.currentTimeMillis();
                if (lastCount != count) {
                    System.out.println("Count = " + (count - lastCount));
                    double rev_per_ms = (double) (count - lastCount) / (double) (timestamp - lastTimestamp);
                    updateRpm(rev_per_ms * MS_PER_SECOND);
                    System.out.println("--------");
                    lastTimestamp = timestamp;
                    lastCount = count;
                }
            }
        }, "RPM");
    }
    
    public double rpm() {
        return rpm;
    }
    
    public void reset() {
        lastTimestamp = 0;
        rpm = 0;
        counter.reset();
    }
    
    private void updateRpm(double _rpm) {
        rpm = _rpm;
        NetworkTable.getTable("RPM").putDouble("rpm",_rpm);
        System.out.println("RPM=" + rpm);
    }
}
