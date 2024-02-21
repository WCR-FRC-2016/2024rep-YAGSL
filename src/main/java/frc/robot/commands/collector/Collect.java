package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Collector;

public class Collect extends Command {
    Collector collector;
    public Collect(Collector collector){
        this.collector = collector;
    }

    @Override
    public void execute() {
        if(collector.hasPiece() != true){
            collector.run();
            }
            else{
              collector.stop();
            }
    }

    @Override
    public void end(boolean interrupted) {
        collector.stop();
    }
    
}
