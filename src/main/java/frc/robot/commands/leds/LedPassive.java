package frc.robot.commands.leds;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LedManager.LedManagerSubsystem;

public class LedPassive extends Command {
    LedManagerSubsystem ledManager;
    public LedPassive(LedManagerSubsystem ledManager){
        this.ledManager = ledManager;
        addRequirements(ledManager);
    }
    @Override
    public void execute() {
        ledManager.setState(2);
    }
}
