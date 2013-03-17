package RobotOld.Devices;

/**
 * A base class for all robot devices
 */
public abstract class AbstractRobotDevice {
    /**
     * Disable the device.  Called when robot should be stopped.
     */
    public void disable() {}

    /**
     * The device is in operator control mode.
     */
    public void operatorControl() {}

    /**
     * The robot has been initialized.
     */
    public void initialize() {}
}
