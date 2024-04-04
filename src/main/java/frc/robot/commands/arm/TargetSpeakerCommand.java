package frc.robot.commands.arm;

import java.lang.reflect.Array;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.leds.LedManager;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.utilities.LimelightUtility;


public class TargetSpeakerCommand extends Command {
    Arm arm;
    private static final double speakerBaseOffset = 0.991;
private static final double distanceToAngleFactorClose = 0.068;
    private static final double distanceToAngleFactorMedium = 0.047;
    private static final double mediumDistance = 2;
    private static final double distanceToAngleFactorFar = 0.025;
    private static final double farDistance = 2.7;
    private static final double horizontalDistanceOffsetFactorClose = 0.015;
     private static final double mediumHorizontalDistance = 2.7;
    private static final double horizontalDistanceOffsetFactorMedium = 0.023;
    private static double [] averageReadsArrayZ = new double [10];
    private static double [] averageReadsArrayX = new double [10];
    private int aprilTagReadIndex = 0;


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
        if(Math.abs(distanceToAprilTagHorizontal) < 1){
            var horizontalDistanceOffset = distanceToAprilTagHorizontal / 2.0f * horizontalDistanceOffsetFactorClose;
        }
        else{
            var horizontalDistanceOffset = distanceToAprilTagHorizontal / 2.0f * horizontalDistanceOffsetFactorMedium;
        }

    }

    @Override
    public void execute() {
        // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!target speaker command");
        double distanceToAngleFactorActual = 0.040;
        var botpose = LimelightUtility.getBotPos();

        var distanceToAprilTag = Math.abs(botpose[2]);
        var distanceToAprilTagHorizontal = Math.abs(botpose[0]);;

        boolean checkSight = botpose[2] < 0;

        double horizontalDistanceOffset;

        if(checkSight){
            LedManager.setState("Resting");

            averageReadsArrayZ[aprilTagReadIndex] = distanceToAprilTag;
            averageReadsArrayX[aprilTagReadIndex] = distanceToAprilTagHorizontal;
            aprilTagReadIndex = (aprilTagReadIndex + 1)%averageReadsArrayZ.length;
        } else {
            LedManager.setState("NoLimelight");
        }
        
        double averageDistanceToAprilTagZ = averageArray(averageReadsArrayZ);
        double averageDistanceToAprilTagX = averageArray(averageReadsArrayX);

        // if (botpose.length == 0) {
        //     System.out.println("couldn't find the apriltag");
        // }
        // if (distanceToAprilTag < mediumDistance) {
        //     System.out.println("close");
        // }
        // if (distanceToAprilTag >= mediumDistance && distanceToAprilTag < farDistance) {
        //     System.out.println("medium");
        // }
        // if (distanceToAprilTag >= farDistance) {
        //     System.out.println("far");
        // }
        if (averageDistanceToAprilTagZ < mediumDistance) {
            distanceToAngleFactorActual = distanceToAngleFactorClose;
            // System.out.println("close");

        }
        if (averageDistanceToAprilTagZ >= mediumDistance && distanceToAprilTag < farDistance) {
            distanceToAngleFactorActual = distanceToAngleFactorMedium;
            // System.out.println("medium");

        }
        if (averageDistanceToAprilTagZ >= farDistance) {
            distanceToAngleFactorActual = distanceToAngleFactorFar;
            // System.out.println("far");

        }

        if(Math.abs(averageDistanceToAprilTagX) < mediumHorizontalDistance){
            horizontalDistanceOffset = averageDistanceToAprilTagX / 2.0f * horizontalDistanceOffsetFactorClose;
        }
        else{
            horizontalDistanceOffset = averageDistanceToAprilTagX / 2.0f * horizontalDistanceOffsetFactorMedium;
        }

        // ledManagerSubsystem.setState(6);

        // var desiredAngle = Constants.RobotDemensions.ArmDipLimit
        //         - ((distanceToAprilTag - speakerBaseOffset) * distanceToAngleFactorActual + horizontalDistanceOffset);
        // System.out.println(horizontalDistanceOffset);
         var desiredAngle = Constants.RobotDemensions.ArmDipLimit
                - ((averageArray(averageReadsArrayZ) - speakerBaseOffset) * distanceToAngleFactorActual + horizontalDistanceOffset);
        
        System.out.println(desiredAngle);
        arm.setAngle(desiredAngle);
    }

    @Override
    public void end(boolean interrupted) {
        // arm.targetHell();
    }

    private double averageArray(double [] array){
        double sum = 0;
        double previousValue = 0;
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
            if(array[i] == 0){
                sum += previousValue;
            }
            else{
                sum += array[i];
                previousValue = array[i];
            }
            
        }
        System.out.println();
        return sum/array.length;
    }

}
