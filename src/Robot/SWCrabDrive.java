/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Developer
 */
public class SWCrabDrive {
    private SpeedController steeringMotor;
    private AnalogChannel steeringPot;
    private Joystick stick;
    private SpeedController driveMotorLF, driveMotorLR;
    private SpeedController driveMotorRF, driveMotorRR;
    private PIDController steeringPID;

    //private DigitalInput buttons[];
    private Relay relays[];
    private AnalogChannel setPot;

    private double kp = -0.01, ki = 0.0, kd = 0.0;
    private double inputMin = 300, inputMax = 700;
    private double setpoint = 500.0;
    public SWCrabDrive(SpeedController steeringMotor,
                       AnalogChannel steeringPot,
                       Joystick stick,
                       SpeedController driveMotorLF,
                       SpeedController driveMotorLR,
                       SpeedController driveMotorRF,
                       SpeedController driveMotorRR) {
        this.steeringMotor = steeringMotor;
        this.steeringPot = steeringPot;
        this.stick = stick;
        this.driveMotorLF = driveMotorLF;
        this.driveMotorLR = driveMotorLR;
        this.driveMotorRF = driveMotorRF;
        this.driveMotorRR = driveMotorRR;
        steeringPID = new PIDController(kp, ki, kd,
                                        steeringPot, steeringMotor);
        steeringPID.setInputRange(inputMin, inputMax);
        steeringPID.setSetpoint(setpoint);

        //buttons = new DigitalInput[5];
        //for(int i = 0; i < 5; i++) {
        //    buttons[i] = new DigitalInput(i+10);
        //}

        relays = new Relay[7];
        for(int i = 0; i < relays.length; i++) {
            relays[i] = new Relay(i+2);
        }

        setPot = new AnalogChannel(7);
    }

    public void setLEDs(int b) {
        b = Math.max(Math.min(b,255), 0);
        for (int i = 0; i < relays.length; i++) {
            if ((b&0x80) != 0) relays[i].set(Relay.Value.kForward);
            else relays[i].set(Relay.Value.kOff);
            b <<= 1;
        }
    }

    public void drive(double leftPower, double rightPower) {
        driveMotorLF.set(leftPower);
        driveMotorLR.set(leftPower);
        driveMotorRF.set(rightPower);
        driveMotorRR.set(rightPower);
    }

    public double currentAngle() {
        return normAngle(pot2angle(steeringPot.pidGet()));
    }

    public static double pot2angle(double pot) {
        return (500-pot)*90.0/175.0;
    }
    public static double angle2pot(double angle) {
        return -angle/90.0*175.0+500;
    }
    public static double normAngle(double angle) {
        if (angle > 90) angle -= 180;
        if (angle < -90) angle += 180;
        return angle;
    }
    public static double clampSetpoint(double setpoint) {
        return Math.min(700, Math.max(300, setpoint));
    }

    public void update() {
        /*
        System.out.println();
        */
        String msg = "";
        //for (int i = 0; i < buttons.length; i++) {
        //    msg += " "+(buttons[i].get() ? 1 : 0);
        //}
        msg += " "+setPot.pidGet();
        msg += " sp:"+steeringPID.getSetpoint();
        msg += " p:"+steeringPot.pidGet();
        msg += " e:"+steeringPID.getError();
        msg += " d:"+steeringPID.get();
        msg += " j<:"+stick.getDirectionDegrees();
        msg += " |j|:"+stick.getMagnitude();
        double angle = normAngle(stick.getDirectionDegrees());

        if (stick.getRawButton(8)) steeringMotor.set(0.75);
        else if (stick.getRawButton(9)) steeringMotor.set(-0.75);
        else if (stick.getRawButton(1)) {
            if (currentAngle() >= 0) {
                steeringPID.setSetpoint(angle2pot(90.0));
            }
            else {
                steeringPID.setSetpoint(angle2pot(-90.0));
            }
            steeringPID.enable();
        }
        else {
            if (stick.getMagnitude() > .1) {
                steeringPID.setSetpoint(clampSetpoint(angle2pot(angle)));
                if (Math.abs(steeringPID.getSetpoint() - steeringPot.pidGet()) > 10) {
                    steeringPID.enable();
                }
                else {
                    steeringMotor.set(0.0);
                    steeringPID.disable();
                }
            }
            else {
                steeringMotor.set(0.0);
                steeringPID.disable();
            }
        }

        if (stick.getRawButton(4) && Math.abs(currentAngle()) < 20)
        {
            drive(-1.0, 1.0);
        }
        else if (stick.getRawButton(5) && Math.abs(currentAngle()) < 20)
        {
            drive(1.0, -1.0);
        }
        else if (stick.getRawButton(1)) {
            double x = stick.getX();
            if (x > 1.0) x = 1.0;
            else if (x < -1.0) x = -1.0;
            if (currentAngle() >= 0) {
                drive(x, x);
            }
            else {
                drive(-x, -x);
            }
        }
        else
        {
            double mag = stick.getMagnitude();
            if (mag > 1.0) mag = 1.0;
            if (stick.getY() < 0) mag *= -1;
            drive(mag, mag);
        }

/*
        if (!buttons[1].get()) {
            double angle = normAngle(stick.getDirectionDegrees());
            if (stick.getMagnitude() > .1 && !buttons[0].get()) {
                steeringPID.setSetpoint(clampSetpoint(angle2pot(angle)));
                if (Math.abs(steeringPID.getSetpoint() - steeringPot.pidGet()) > 10) {
                    steeringPID.enable();
                }
                else {
                    steeringPID.disable();
                }
            }
            else {
                steeringPID.disable();
            }
        }
        else {
            steeringPID.setSetpoint(clampSetpoint(setPot.pidGet()));
        }

        if (!buttons[2].get()) {
            if (stick.getRawButton(4) && Math.abs(currentAngle()) < 10) {
                drive(-1.0, 1.0);
            }
            else if (stick.getRawButton(5) && Math.abs(currentAngle()) < 10) {
                drive(1.0, -1.0);
            }
            else {
                double mag = stick.getMagnitude();
                if (mag > 1.0) mag = 1.0;
                if (stick.getY() < 0) mag *= -1;
                drive(mag, mag);
            }
        }
        else {
            drive(0.0, 0.0);
        }
 */
        setLEDs((int)(steeringPot.pidGet()-500)*2+128);
        
        System.out.println(msg);
    }
}
