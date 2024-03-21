package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Collector;
import frc.robot.subsystems.Arm.Shooter;

public class AutoShootStart extends Command{
     Shooter shooter;
     Collector collector;
    public AutoShootStart(Shooter shooter, Collector collector){
        this.shooter = shooter;
        this.collector = collector;
        addRequirements(shooter);
    }

     @Override
    public void initialize() {
        shooter.run();
        collector.run();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}

