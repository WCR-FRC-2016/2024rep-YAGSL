package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class ResetOdometryCommand extends Command {
    SwerveSubsystem driveBase;

    public ResetOdometryCommand(SwerveSubsystem driveBase) {
        this.driveBase = driveBase;
        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        driveBase.resetOdometry(null);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
