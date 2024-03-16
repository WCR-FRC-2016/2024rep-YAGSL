package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Collector;
import frc.robot.subsystems.Arm.Shooter;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class Spit extends Command {
    Shooter shooter;
    Collector collector;
    SwerveSubsystem drivebase;
    public Spit(Shooter shooter, Collector collector, SwerveSubsystem drivebase){
        this.shooter = shooter;
        this.collector = collector;
        this.drivebase = drivebase;
        addRequirements(shooter, collector,drivebase);

    }

     @Override
    public void execute() {
        shooter.run(0.25);
        collector.run();
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        collector.stop();
    }
    
}