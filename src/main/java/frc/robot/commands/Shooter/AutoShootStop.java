package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Collector;
import frc.robot.subsystems.Arm.Shooter;


    public class AutoShootStop extends Command{
     Shooter shooter;
     Collector collector;
    public AutoShootStop (Shooter shooter, Collector collector){
        this.shooter = shooter;
        this.collector = collector;
        addRequirements(shooter);
    }

     @Override
    public void initialize() {
        shooter.stop();
        collector.stop();
    }
    @Override
    public boolean isFinished(){
        return true;
    }
}

