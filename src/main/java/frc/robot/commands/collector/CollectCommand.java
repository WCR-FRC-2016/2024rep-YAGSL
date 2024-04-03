package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.leds.LedManager;
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
        LedManager.setStateWithPriority("CollectingNoPiece", 1);
        arm.targetHell();
    }

    @Override
    public void execute() {

        if (collector.hasPiece() == false) {
            collector.run();
        } else {
            collector.stop();
            LedManager.setStateWithPriority("PieceCollected", 2);
        }
        
    }

    @Override
    public boolean isFinished() {
        return collector.hasPiece();

    }

    @Override
    public void end(boolean interrupted) {
        collector.stop();
        LedManager.resetState("CollectingNoPiece");
        LedManager.setState("Resting");

        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
            new WaitCommand(2.0f),
            new InstantCommand(() -> { 
                LedManager.resetState("PieceCollected"); 
                LedManager.setState("Resting");
            })
        ));
    }
}
