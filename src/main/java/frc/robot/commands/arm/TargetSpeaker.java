package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Arm;

public class TargetSpeaker extends Command {
    Arm arm;
    public TargetSpeaker(Arm arm){
        this.arm = arm;
    }

    @Override
    public void execute() {
       // arm.targetSpeaker();
    }

    @Override
    public void end(boolean interrupted) {
        arm.targetHell();
    }
    
}
