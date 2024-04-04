package frc.robot.commands.climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.leds.LedManager;
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
        double adjustedInputLeft = 0;
        double adjustedInputRight = 0;

        if(!climber.climberLeftMaxHeight()){           
            adjustedInputLeft = Math.min(input.getAsDouble(), 0);
        }

        else if(!climber.climberLeftMinHeight()){
            adjustedInputLeft = Math.max(input.getAsDouble(), 0);
        }

        else{
            adjustedInputLeft = input.getAsDouble();            
        }

        if(!climber.climberRightMaxHeight()){
            adjustedInputRight = Math.min(input.getAsDouble(), 0);
        }

        else if(!climber.climberRightMinHeight()){
            adjustedInputRight = Math.max(input.getAsDouble(), 0);
        }
        else{    
            adjustedInputRight = input.getAsDouble();
        }

        if(!climber.climberLeftMaxHeight() && !climber.climberRightMaxHeight()){
            LedManager.setState("TopOfClimber");
        }

        else if(!climber.climberLeftMinHeight() && !climber.climberRightMinHeight()){
            LedManager.setState("BottomOfClimber");
        }

        else{
            LedManager.setState("Resting");  
        }

        // System.out.println("lmax: " + climber.climberLeftMaxHeight() + " lmin: " + climber.climberLeftMinHeight() + " rmax: " + climber.climberRightMaxHeight() + " rmin: " + climber.climberRightMinHeight());
        
        // if (climber.climberMinHeight() == true) {
        //     adjustedInput = Math.min(input.getAsDouble(), 0);
        //     // climber.drive(Math.min(input.getAsDouble(), 0));
        // }
        // else if (climber.climberMaxHeight() == true) {
        //     adjustedInput = Math.max(input.getAsDouble(), 0);
        //     // climber.drive(Math.max(input.getAsDouble(), 0));
        // }
        // else{

            // climber.drive(input.getAsDouble());
        // }

        climber.driveLeft(adjustedInputLeft);
        climber.driveRight(adjustedInputRight);
    }
}