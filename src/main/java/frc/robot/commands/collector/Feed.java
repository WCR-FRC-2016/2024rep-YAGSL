package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Collector;

public class Feed extends Command {
    Collector collector;
    public Feed(Collector collector){
        this.collector = collector;
    }

     @Override
    public void execute() {
        collector.run();
    }

    @Override
    public void end(boolean interrupted) {
        collector.stop();
    }
    
}