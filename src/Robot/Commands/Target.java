package Robot.Commands;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;

/**
 */
public class Target {
    public double distanceZ;
    public double angleX;
    public double offsetY;

    public static Target getTarget(NetworkTable table) throws NetworkTableKeyNotDefined {
        Target target = new Target();
        target.distanceZ = table.getDouble("distance");
        target.angleX = table.getDouble("angleX");
        return target;
    }
}
