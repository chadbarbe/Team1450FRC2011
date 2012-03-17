import edu.wpi.first.wpilibj.networking.NetworkTable;

import java.awt.*;

/**
 */
public class Target {
    public int posX;
    public int posY;
    public double distance;
    public double angleX;
    public double offsetY;
    public long timestamp;

    public Target(NetworkTable table) {
        try {
            posX = table.getInt("posX");
            posY = table.getInt("posY");
            distance = table.getDouble("distance");
            angleX = table.getDouble("angleX");
            offsetY = table.getDouble("offsetY");
            timestamp = table.getInt("timestamp");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    protected Target() {
    }

    public static class NullTarget extends Target {
        public NullTarget() {
            posX=-1;
            posY=-1;
            distance=0;
            angleX=0;
            offsetY=0;
            timestamp=0;
        }
    }

    public void putData(NetworkTable networkTable) {
        networkTable.beginTransaction();
        networkTable.putInt("timestamp", (int) timestamp);
        networkTable.putInt("posX", posX);
        networkTable.putInt("posY", posY);
        networkTable.putDouble("distance", distance);
        networkTable.putDouble("angleX", angleX);
        networkTable.putDouble("offsetY", offsetY);
        networkTable.endTransaction();
    }
}
