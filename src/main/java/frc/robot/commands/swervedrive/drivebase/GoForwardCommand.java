package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class GoForwardCommand extends Command {
    private SwerveSubsystem driveBase;
    private int timerCounter = 50;
    private int unlazyEndline;

    public GoForwardCommand(SwerveSubsystem swerve) {
        driveBase = swerve;
        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        driveBase.drive(-0.20, 0, driveBase.getHeading().getRadians());
        unlazyEndline = 0;
        System.out.println("started moving");
    }

    @Override
    public void execute() {
        unlazyEndline++;
    }

    @Override
    public boolean isFinished() {
        return unlazyEndline >= timerCounter;
    }
    
    @Override
    public void end(boolean interrupted) {
        System.out.println("FinishedMoving");
        driveBase.drive(0, 0, driveBase.getHeading().getRadians());
    }

}
