package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.subsystems.Arm.Collector;

public class CollectCommand extends Command {
    Collector collector;
    Arm arm;

    public CollectCommand(Collector collector, Arm arm) {
        this.arm = arm;
        this.collector = collector;
        addRequirements(collector, arm);

    }

    @Override
    public void initialize() {
        arm.targetHell();
    }

    @Override
    public void execute() {

        if (collector.hasPiece() == false) {
            collector.run();
        } else {
            collector.stop();
        }
        
    }

    @Override
    public boolean isFinished() {
        return collector.hasPiece();

    }

    @Override
    public void end(boolean interrupted) {
        collector.stop();
    }

}
