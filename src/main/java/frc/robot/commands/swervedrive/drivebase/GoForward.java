package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class GoForward extends Command {
    private SwerveSubsystem driveBase;

    public GoForward(SwerveSubsystem swerve){
        driveBase = swerve;
        addRequirements(driveBase);
    }

    @Override
    public void initialize () {
        driveBase.drive(0, 1, driveBase.getHeading().getRadians());
    }
     
}
