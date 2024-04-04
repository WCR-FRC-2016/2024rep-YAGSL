package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Collector;
import frc.robot.subsystems.Arm.Shooter;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class DumpCommand extends Command {
    Shooter shooter;
    Collector collector;
    SwerveSubsystem drivebase;

    public DumpCommand(Shooter shooter, Collector collector, SwerveSubsystem drivebase) {
        this.shooter = shooter;
        this.collector = collector;
        this.drivebase = drivebase;
        addRequirements(shooter, collector, drivebase);

    }

    @Override
    public void execute() {
        shooter.run(1);
        collector.run(1);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        collector.stop();
    }

}