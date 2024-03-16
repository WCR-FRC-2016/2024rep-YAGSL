package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Collector;
import frc.robot.subsystems.Arm.Shooter;

public class Spit extends Command {
    Shooter shooter;
    Collector collector;
    public Spit(Shooter shooter, Collector collector){
        this.shooter = shooter;
        this.collector = collector;
        addRequirements(shooter, collector);
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