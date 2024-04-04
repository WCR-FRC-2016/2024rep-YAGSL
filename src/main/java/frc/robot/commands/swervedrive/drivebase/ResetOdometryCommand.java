package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.leds.LedManager;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class ResetOdometryCommand extends Command {
    SwerveSubsystem driveBase;

    public ResetOdometryCommand(SwerveSubsystem driveBase) {
        this.driveBase = driveBase;
        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        System.out.println("OdometryReset");
        driveBase.resetOdometry(null);
        LedManager.setStateWithPriority("OdometryReset", 1);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
            new WaitCommand(2.0),
            new InstantCommand(() -> { LedManager.resetState("OdometryReset"); })
        ));
    }
}
