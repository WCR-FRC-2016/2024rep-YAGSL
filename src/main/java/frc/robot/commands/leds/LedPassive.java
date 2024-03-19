package frc.robot.commands.leds;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.subsystems.Arm.Collector;
import frc.robot.subsystems.LedManager.LedManagerSubsystem;

public class LedPassive extends Command {
    LedManagerSubsystem ledManager;
    Collector collector;
    public LedPassive(LedManagerSubsystem ledManager){
        this.ledManager = ledManager;
        this.collector = collector;
        addRequirements(ledManager);
    }
    @Override
    public void execute() {
        if (collector.hasPiece() == true) {
            ledManager.setState(3);
        }
    }
}
