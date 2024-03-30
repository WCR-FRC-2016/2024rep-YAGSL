package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.utilities.LimelightUtility;

public class TargetSpeakerCommand extends Command {
    Arm arm;
    private static final double speakerBaseOffset = 0.991;
private static final double distanceToAngleFactorClose = 0.062;
    private static final double distanceToAngleFactorMedium = 0.044;
    private static final double mediumDistance = 2;
    private static final double distanceToAngleFactorFar = 0.025;
    private static final double farDistance = 2.7;
    private static final double horizontalDistanceOffsetFactor = 0.020;

    public TargetSpeakerCommand(Arm arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    // Moving println to initialize so it only runs once
    @Override
    public void initialize() {
        System.out.println("!!!target speaker command!!!");
        var botpose = LimelightUtility.getBotPos();
        var distanceToAprilTag = Math.abs(botpose[2]);
        
        var distanceToAprilTagHorizontal = Math.abs(botpose[0]);
        var horizontalDistanceOffset = distanceToAprilTagHorizontal / 2.0f * horizontalDistanceOffsetFactor;
        System.out.println(horizontalDistanceOffset);
    }

    @Override
    public void execute() {
        // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!target speaker command");
        double distanceToAngleFactorActual = 0.040;
        var botpose = LimelightUtility.getBotPos();
        var distanceToAprilTag = Math.abs(botpose[2]);
        if (botpose.length == 0) {
            System.out.println("couldn't find the apriltag");
        }
        if (distanceToAprilTag < mediumDistance) {
            System.out.println("close");
        }
        if (distanceToAprilTag >= mediumDistance && distanceToAprilTag < farDistance) {
            System.out.println("medium");
        }
        if (distanceToAprilTag >= farDistance) {
            System.out.println("far");
        }
        if (distanceToAprilTag < mediumDistance) {
            distanceToAngleFactorActual = distanceToAngleFactorClose;
            // System.out.println("close");

        }
        if (distanceToAprilTag >= mediumDistance && distanceToAprilTag < farDistance) {
            distanceToAngleFactorActual = distanceToAngleFactorMedium;
            // System.out.println("medium");

        }
        if (distanceToAprilTag >= farDistance) {
            distanceToAngleFactorActual = distanceToAngleFactorFar;
            // System.out.println("far");

        }
        var distanceToAprilTagHorizontal = Math.abs(botpose[0]);
        var horizontalDistanceOffset = distanceToAprilTagHorizontal / 2.0f * horizontalDistanceOffsetFactor;
        // ledManagerSubsystem.setState(6);

        var desiredAngle = Constants.RobotDemensions.ArmDipLimit
                - ((distanceToAprilTag - speakerBaseOffset) * distanceToAngleFactorActual + horizontalDistanceOffset);
        // System.out.println(horizontalDistanceOffset);
        
        arm.setAngle(desiredAngle);
    }

    @Override
    public void end(boolean interrupted) {
        // arm.targetHell();
    }

}
