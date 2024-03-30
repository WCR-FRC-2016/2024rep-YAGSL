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
        double adjustedInput = 0;

        // if (climber.climberMinHeight() == true) {
        //     adjustedInput = Math.min(input.getAsDouble(), 0);
        //     // climber.drive(Math.min(input.getAsDouble(), 0));
        // }
        // else if (climber.climberMaxHeight() == true) {
        //     adjustedInput = Math.max(input.getAsDouble(), 0);
        //     // climber.drive(Math.max(input.getAsDouble(), 0));
        // }
        // else{
            adjustedInput = input.getAsDouble();
            // climber.drive(input.getAsDouble());
        // }

        climber.drive(adjustedInput);
    }
}