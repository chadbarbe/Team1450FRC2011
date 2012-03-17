import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networking.NetworkListener;
import edu.wpi.first.wpilibj.networking.NetworkTable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class RPMWidget extends StaticWidget {

    private static int minimumDisplayRefresh = 250; // ms
    private long lastTimestamp = 0;
    private List<Double> rpmQueue = new ArrayList<Double>();

    @Override
    public void init() {
        final JLabel label = new JLabel();
        add(label);
        setVisible(true);
        NetworkTable rpmTable = NetworkTable.getTable("RPM");
        rpmTable.addListener("rpm", new NetworkListener() {
            @Override
            public void valueChanged(String s, Object o) {
                long timestamp = System.currentTimeMillis();
                double rpm = (Double)o;
                if ((timestamp - lastTimestamp) > minimumDisplayRefresh) {
                    double aveRpm = rpm;
                    for (Double aDouble : rpmQueue) {
                        aveRpm += aDouble;
                    }
                    aveRpm /= rpmQueue.size();
                    rpmQueue.clear();
                    String txt = String.format("RPM=%.2f",aveRpm);
                    label.setText(txt);
                    lastTimestamp = timestamp;
                } else {
                    rpmQueue.add(rpm);
                }
            }

            @Override
            public void valueConfirmed(String s, Object o) {
            }
        });
    }

    @Override
    public void propertyChanged(Property property) {
    }
}
