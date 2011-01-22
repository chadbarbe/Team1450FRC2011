/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANJaguar;

/**
 *
 * @author Administrator
 */
public class PIDTuner {

    public static class MenuState {

        public final int value;
        static final int main_val = 0;
        static final int changeP_val = 1;
        static final int changeI_val = 2;
        static final int changePos1_val = 3;
        static final int changePos2_val = 4;
        public static final MenuState main = new MenuState(main_val);
        public static final MenuState changeP = new MenuState(changeP_val);
        public static final MenuState changeI = new MenuState(changeI_val);
        public static final MenuState changePos1 = new MenuState(changePos1_val);
        public static final MenuState changePos2 = new MenuState(changePos2_val);

        private MenuState(int value) {
            this.value = value;
        }
    }
    private int menuSize = 5;
    private double increment;
    private double pValue, iValue, pos1, pos2;
    private String[] menuOptions;
    private Joystick control;
    private boolean debounced;
    private CANJaguar jag;
    private MenuState state;
    private int menuCount;

    public PIDTuner(CANJaguar jaguar, Joystick stick) {
        menuOptions = new String[menuSize];
        control = stick;
        jag = jaguar;
        pos1 = 0.3;
        pos2 = 0.7;
        pValue = 4000;
        iValue = 0.0;
        increment = 1.0;
        menuCount = 0;

        menuOptions[0] = "1) Set P";
        menuOptions[1] = "2) Set I";
        menuOptions[2] = "3) Set position 1";
        menuOptions[3] = "4) Set Position 2";
        menuOptions[4] = "5) Toggle PID on/off";

        debounced = false;

        state = MenuState.main;
    }

    public void run() {
        menuCount++;

        if (state == MenuState.main) {
            if (menuCount >= 100) {
                menuCount = 0;
                System.out.println("*****************");
                for (int x = 0; x < menuSize; x++) {
                    System.out.println(menuOptions[x]);
                }
                System.out.println();
                System.out.println();
            }

            if ((menuCount % 10) == 0) {
                try {
                    System.out.println("Pot: " + jag.getPosition());
                } catch (edu.wpi.first.wpilibj.can.CANTimeoutException e) {
                }
            }
            CheckStateChange();
        } else if (state == MenuState.changeP) {
            if ((menuCount % 10) == 0) {
                System.out.println("P: " + pValue + "increment: " + increment);
            }
            pValue = ManipulateValue(pValue);

            if (control.getRawButton(10)) {
                UpdatePID();
            }
        } else if (state == MenuState.changeI) {
            if ((menuCount % 10) == 0) {
                System.out.println("I: " + iValue + "increment: " + increment);
            }
            pValue = ManipulateValue(iValue);

            if (control.getRawButton(10)) {
                UpdatePID();
            }
        } else if (state == MenuState.changePos1) {
            if ((menuCount % 10) == 0) {
                System.out.println("Pos 1: " + pos1 + "increment: " + increment);
            }
            pos1 = ManipulateValue(pos1);

            if (control.getRawButton(10)) {
                UpdatePos(pos1);
            }
        } else if (state == MenuState.changePos2) {
            if ((menuCount % 10) == 0) {
                System.out.println("Pos 2: " + pos2 + "increment: " + increment);
            }
            pos1 = ManipulateValue(pos2);

            if (control.getRawButton(10)) {
                UpdatePos(pos2);
            }
        }
    }

    public void CheckStateChange() {
        if (control.getRawButton(1)) {
            state = MenuState.changeP;
        } else if (control.getRawButton(2)) {
            state = MenuState.changeI;
        } else if (control.getRawButton(3)) {
            state = MenuState.changePos1;
        } else if (control.getRawButton(4)) {
            state = MenuState.changePos2;
        }

        debounced = false;
    }

    public double ManipulateValue(double value) {
        if (!(control.getRawButton(4) || control.getRawButton(2)
                || control.getRawButton(6) || control.getRawButton(5)
                || control.getRawButton(10))) {
            debounced = true;
        }

        if (debounced) {
            if (control.getRawButton(4)) //Menu Up
            {
                debounced = false;
                value = value + increment;
            } else if (control.getRawButton(2)) //Menu Down
            {
                debounced = false;
                value = value - increment;
            } else if (control.getRawButton(5)) //bigger increment
            {
                debounced = false;
                increment = increment * 10;
            } else if (control.getRawButton(6)) //smaller increment
            {
                debounced = false;
                increment = increment / 10;
            }
        }

        return (value);
    }

    public void UpdatePID() {
        try {
            jag.setPID(pValue, iValue, 0.0);
        } catch (edu.wpi.first.wpilibj.can.CANTimeoutException e) {
        }
        state = MenuState.main;
    }

    public void UpdatePos(double pos) {
        jag.set(pos);
        state = MenuState.main;
    }
}
