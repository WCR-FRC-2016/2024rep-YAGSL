package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Collector;

public class AutoFeed extends Command {
    Collector collector;
    public AutoFeed(Collector collector){
        this.collector = collector;
        addRequirements(collector);
    }

     @Override
    public void initialize() {
        collector.run();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}