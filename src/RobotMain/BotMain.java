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
import Robot.Commands.Shooter.DefaultShooterCommand;
import Robot.Commands.Waist.OperatorControlShooterCommand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        CommandBase.shooter.dontSpin();

        autoCommand = new AutonomousJustShootCommandGroup();
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Just Shoot", new AutonomousJustShootCommandGroup());
        autoChooser.addObject("Do Nothing", new DefaultShooterCommand());
        autoChooser.addObject("Shoot At Target", new AutonomousShootBallCommandGroup());
        autoChooser.addObject("Turn And Shoot", new AutonomousTurnAndShootCommandGroup());
        SmartDashboard.putData("Autonomous Chooser", autoChooser);
    }

    public void autonomousInit() {
//        schedule the autonomous command (example)
//        autoCommand = (Command) autoChooser.getSelected();
        System.out.println("Auto Command: " + autoCommand);
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

    public void disabledPeriodic() {
    }

    /**
     * Initialization of disabled code.
     */
    public void disabledInit() {
    }
}
