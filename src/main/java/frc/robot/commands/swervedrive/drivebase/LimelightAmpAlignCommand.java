package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.utilities.LimelightUtility;

public class LimelightAmpAlignCommand extends Command {
    private SwerveSubsystem driveBase;
    private double desiredAngle;
    private double currentAngle;
    private static final double desiredDistanceZ = -1;
    private double actualDistanceZ;
    private double actualDistanceX;
    private double turnMagnitude = 1;
    private boolean closeToTarget = false;

    public LimelightAmpAlignCommand(SwerveSubsystem swerve) {
        driveBase = swerve;
        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        closeToTarget = false;
        System.out.println("***ran auto amp command***");
        // // NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(botpose);
        // // desiredAngle = botpose[4];
        // // double tx = getTx();
        // // desiredAngle = driveBase.getHeading().getDegrees() - tx;
    }

    @Override
    public void execute() {

        var botpose = LimelightUtility.getBotPos();

        // Check connection to Network table
        if (botpose.length == 0) {
            driveBase.drive(0, 0, driveBase.getHeading().getRadians());
            return;
        }
        if(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0.0) != 1){
            System.out.println("no traget");
            driveBase.drive(0, 0, driveBase.getHeading().getRadians());
            return;
        }

        // Check if able to see April Tag and if close to target stop moving and if not
        // seek
        // if (botpose[0] == 0 && botpose[1] == 0 && botpose[2] == 0) {
        //     if (closeToTarget == true) {
        //         driveBase.drive(0, 0, driveBase.getHeading().getRadians());
        //     }
        //     // else{
        //     // driveBase.drive(0, 0, driveBase.getHeading().getRadians() +
        //     // Math.toRadians(25) * turnMagnitude);
        //     // }
        //     return;
        // }

        System.out.println("distance: " + botpose[2] + " | angle: " + getTx());
        actualDistanceX = botpose[0];
        actualDistanceZ = botpose[2];
        double distanceToMoveY = (actualDistanceZ - desiredDistanceZ);
        double distanceToMoveX = -1 * (actualDistanceX);
        desiredAngle = driveBase.getHeading().getDegrees() - getTx();
        // currentAngle = botpose[4];
        driveBase.drive(-Math.max(distanceToMoveY * 0.6, 0.2), -Math.max(distanceToMoveX * 0.6, 0.2), Math.toRadians(desiredAngle));
        currentAngle = getTx();
        turnMagnitude = currentAngle != 0.0d ? currentAngle / Math.abs(currentAngle) * -1.0d : 1.0d;
        // NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(botpose);

        // System.out.println(currentAngle);

        // if (Math.abs(distanceToMoveY) <= 0.3) {
        //     closeToTarget = true;
        // }
    }

    @Override
    public boolean isFinished() {

        // return Math.abs( currentAngle) <= 0.1d;
        if (Math.abs(getTx()) <= 3d && Math.abs(desiredDistanceZ) - 0.25 <= Math.abs(getZpos()) && Math.abs(getZpos()) <= Math.abs(desiredDistanceZ) + 0.5) {
            return true;
        }
        return false;
    }

    public double getTx() {

        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    }

    private double getZpos() {
        var botpose = LimelightUtility.getBotPos();
        if (botpose.length == 0) {        
            return desiredDistanceZ;
        }
        return LimelightUtility.getBotPos()[2];
    }       
    
}
