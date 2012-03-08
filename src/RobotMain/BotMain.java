/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package RobotMain;

import Robot.Commands.AutonomousJustShootCommandGroup;
import Robot.Commands.AutonomousShootBallCommandGroup;
import Robot.Commands.AutonomousTurnAndShootCommandGroup;
import Robot.Commands.CommandBase;
import Robot.Commands.Waist.OperatorControlShooterCommand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BotMain extends IterativeRobot {

    private Command autoCommand;
    private SendableChooser autoChooser;

    public void robotInit() {
        CommandBase.init();
        NetworkTable.initialize();

        autoChooser = new SendableChooser();
        autoChooser.addDefault("Just Shoot", new AutonomousJustShootCommandGroup());
        autoChooser.addObject("Shoot At Target", new AutonomousShootBallCommandGroup());
        autoChooser.addObject("Turn And Shoot", new AutonomousTurnAndShootCommandGroup());
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        autoCommand = (Command) autoChooser.getSelected();
        autoCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        autoCommand.cancel();
        new OperatorControlShooterCommand().start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * Initialization of disabled code.
     */
    public void disabledInit() {
    }
}
