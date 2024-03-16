package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.utilities.LimelightUtility;

public class TargetSpeaker extends Command {
    Arm arm;
    private static final double speakerBaseOffset = 0.991;
    private static final double distanceToAngleFactor = 0.035;

    public TargetSpeaker(Arm arm){
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        var botpose = LimelightUtility.getBotPos();

        var distanceToAprilTag = Math.abs(botpose[2]);
        
        var desiredAngle = 0.789 - ((distanceToAprilTag - speakerBaseOffset) * distanceToAngleFactor);

        arm.setAngle(desiredAngle);
    }

    @Override
    public void end(boolean interrupted) {
        // arm.targetHell();
    }
    
}
