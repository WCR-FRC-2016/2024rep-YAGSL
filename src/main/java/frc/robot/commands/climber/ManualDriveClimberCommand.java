package frc.robot.commands.climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber.Climber;

public class ManualDriveClimberCommand extends Command {
    Climber climber;
    DoubleSupplier input;

    public ManualDriveClimberCommand(Climber climber, DoubleSupplier input) {
        this.climber = climber;
        this.input = input;
        addRequirements(climber);

    }
    @Override
    public void initialize(){
        
    }
    @Override
    public void execute() {
        if (climber.climberMaxHeight()) {
            climber.drive(Math.min(input.getAsDouble() * 2, 0));
        }
        if (climber.climberMinHeight()) {
            climber.drive(Math.max(input.getAsDouble() * 2, 0));
        }
    }
}