///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package RobotOld.Devices;
//
//import Robot.Utils.DrivePIDOutput;
//import RobotMain.Constants;
//import RobotMain.IODefines;
//import edu.wpi.first.wpilibj.*;
//
///**
// *
// * @author chad
// */
//public class Wrist {
//
//    private Thread m_thread;
//    private Joystick stick;
//    private PIDController pid;
//    private double autonomousTarget;
//
//    private DigitalInput wristLimitUp = new DigitalInput(IODefines.WRIST_LIMIT_UP);
//    private DigitalInput wristLimitDown = new DigitalInput(IODefines.WRIST_LIMIT_DOWN);
//    private AnalogChannel pot = new AnalogChannel(IODefines.WRIST_POT);
//  //  private SpeedController wristDrive = new Jaguar(IODefines.WRIST_DRIVE);
//
//    private DrivePIDOutput wristPIDOutput = new DrivePIDOutput(wristDrive, wristLimitUp, wristLimitDown, false, "Wrist");
//
//    public Wrist(Joystick _stick) {
//        stick = _stick;
//
//        pid = new PIDController(0.005, 0, 0, pot, wristPIDOutput);
//        pid.setSetpoint(Constants.Wrist.initialPosition);
//        pid.setOutputRange(-0.35, 0.35);
//        m_thread = new WristThread(this);
//    }
//
//    private class WristThread extends Thread {
//
//        private Wrist wrist;
//        private boolean m_run = true;
//
//        WristThread(Wrist _wrist) {
//            wrist = _wrist;
//        }
//
//        public void run() {
//            while (m_run) {
//                wrist.run();
//
//                try {
//                    Thread.sleep(Constants.Wrist.loopTime);
//                } catch (InterruptedException e) {
//                }
//            }
//        }
//    }
//
//    private double getPot() {
//        return pot.getAverageVoltage();
//    }
//
//    public void start() {
//        setAutonomousTarget(Constants.Wrist.initialPosition);
//        pot.resetAccumulator();
//        m_thread.start();
//        pid.enable();
//        
//    }
//
//    public void setAutonomousTarget(double position) {
//        if (position < Constants.Wrist.lowerLimitPotVal) {
//            autonomousTarget = Constants.Wrist.lowerLimitPotVal;
//        }
//        else if (position > Constants.Wrist.upperLimitPotVal) {
//            autonomousTarget = Constants.Wrist.upperLimitPotVal;
//        }
//        else {
//            autonomousTarget = position;
//        }
//    }
//
//    public void atUpperLimit() {
//        //boolean currentDirection;
//        //currentDirection = sign(wristDrive.get());
//        System.out.println("Wrist Upper Limit.");
//    }
//
//    public void atLowerLimit() {
//        System.out.println("Wrist Lower Limit.");
//    }
//
//    private void run() {
//        double driveTarget;
//        if (DriverStation.getInstance().isAutonomous()) {
//            driveTarget = autonomousTarget;
//        }
//        else {
//            driveTarget = (getUserInput() * Constants.Wrist.potRange +
//                    Constants.Wrist.lowerLimitPotVal);
//        }
//        
//        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1,
//                "Wrist Target = " + driveTarget);
//        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1,
//                "Wrist Value = " + pot.getAverageValue());
//        DriverStationLCD.getInstance().updateLCD();
//        pid.setSetpoint(driveTarget);
//    }
//
//    private double getUserInput() {
//        //return (stick.getAxis(Joystick.AxisType.kY) + 1) / 2;
//        return (1 - stick.getZ()) / 2;
//    }
//
//    public void disable() {
//        wristDrive.disable();
//    }
//}
