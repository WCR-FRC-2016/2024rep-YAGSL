package frc.robot.commands.arm;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.subsystems.LedManager.LedManagerSubsystem;
import frc.robot.utilities.LimelightUtility;

public class TargetSpeaker extends Command {
    Arm arm;
    LedManagerSubsystem ledManagerSubsystem;
    private static final double speakerBaseOffset = 0.991;
    private static final double distanceToAngleFactorClose = 0.040;
    private static final double distanceToAngleFactorMedium = 0.032;
        private static final double mediumDistance = 2.5;
    private static final double distanceToAngleFactorFar = 0.027;
        private static final double farDistance = 3;
    private static final double horizontalDistanceOffsetFactor = 0.005;


    public TargetSpeaker(Arm arm, LedManagerSubsystem ledManagerSubsystem){
        this.arm = arm;
        this.ledManagerSubsystem = ledManagerSubsystem;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        double distanceToAngleFactorActual = 0.040;

        var botpose = LimelightUtility.getBotPos();

        var distanceToAprilTag = Math.abs(botpose[2]);
        if(distanceToAprilTag < mediumDistance){
            distanceToAngleFactorActual = distanceToAngleFactorClose;

        }
        if(distanceToAprilTag >= mediumDistance && distanceToAprilTag < farDistance){
            distanceToAngleFactorActual = distanceToAngleFactorMedium;

        }
        if(distanceToAprilTag >= farDistance){
            distanceToAngleFactorActual = distanceToAngleFactorFar;

        }
       var distanceToAprilTagHorizontal = Math.abs(botpose[0]);
    var horizontalDistanceOffset = distanceToAprilTagHorizontal/2.0f * horizontalDistanceOffsetFactor;
        //ledManagerSubsystem.setState(6);

        
        var desiredAngle = Constants.RobotDemensions.ArmDipLimit - ((distanceToAprilTag - speakerBaseOffset) * distanceToAngleFactorActual + horizontalDistanceOffset);

        arm.setAngle(desiredAngle);
    }

    @Override
    public void end(boolean interrupted) {
        // arm.targetHell();
    }

    
}
