//package RobotOld.Devices;
//
//import Robot.Utils.Threading;
//import RobotOld.ButtonMapping;
//import RobotMain.Constants;
//import RobotMain.IODefines;
//import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Victor;
//import edu.wpi.first.wpilibj.buttons.JoystickButton;
//
///**
// * The pickup ramp
// */
//public class PickupRamp  extends AbstractRobotDevice {
//
//    private final Victor pickupRampMotor = new Victor(IODefines.PICKUP_SHUTE_MOTOR);
//    private final JoystickButton button = IODefines.PICKUP_RAMP_BUTTON;
//
//    public void disable() {
//        pickupRampMotor.disable();
//    }
//
//    public void initialize() {
//        Threading.runInLoop(Constants.LimitSwitches.loopTime, new PickupRampLoop(), "PickupRamp");
//    }
//
//    private class PickupRampLoop implements Runnable {
//        public void run() {
//            if (button.get()) {
//                pickupRampMotor.set(1.0);
//            } else {
//                pickupRampMotor.set(0.0);
//            }
//        }
//    }
//}
