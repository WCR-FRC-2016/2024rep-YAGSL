package frc.robot.commands.arm;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Arm;

public class ManualDriveArm extends Command {
    Arm arm;
    DoubleSupplier input;
    public ManualDriveArm(Arm arm, DoubleSupplier input){
        this.arm = arm;
        this.input = input;
        addRequirements(arm);
    }

    @Override
    public void execute() {
       arm.drive(input.getAsDouble() * 2);
    }

}