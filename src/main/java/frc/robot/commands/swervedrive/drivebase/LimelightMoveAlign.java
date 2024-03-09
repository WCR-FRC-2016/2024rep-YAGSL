package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.utilities.LimelightUtility;

public class LimelightMoveAlign extends Command {
    private SwerveSubsystem driveBase;
    private double desiredAngle;
    private double currentAngle;
    private static final double desiredDistanceZ = -1;
    private double actualDistanceZ;
    private double turnMagnitude = 1;
    public LimelightMoveAlign(SwerveSubsystem swerve){
        driveBase = swerve;
        addRequirements(driveBase);

    }

    @Override
    public void initialize() {
        
        // NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(botpose);
        // desiredAngle = botpose[4];
        // double tx = getTx();
        // desiredAngle = driveBase.getHeading().getDegrees() - tx;
    }

    @Override
    public void execute() {
        var botpose = LimelightUtility.getBotPos();

        // Check connection to Network table
        if (botpose.length == 0 ){ 
            driveBase.drive(0, 0, driveBase.getHeading().getRadians());
            return;
        }

        // Check if able to see April Tag
        if (botpose[0] == 0 && botpose[1] == 0 && botpose[2] == 0){
            driveBase.drive(0, 0, driveBase.getHeading().getRadians() + Math.toRadians(25) * turnMagnitude);
            return;
        }

        actualDistanceZ = botpose[2];
        double distanceToMove = -1 * (actualDistanceZ - desiredDistanceZ);       
        desiredAngle = driveBase.getHeading().getDegrees() - getTx();
        
        //currentAngle = botpose[4];
        driveBase.drive(0, distanceToMove * 0.4,  Math.toRadians(desiredAngle)); 
        currentAngle = getTx();
        turnMagnitude = currentAngle != 0.0d ? currentAngle/Math.abs(currentAngle) * -1.0d :1.0d;
        //NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(botpose);
        
        //System.out.println(currentAngle);

    }

    @Override 
    public boolean isFinished(){
        //return  Math.abs( currentAngle) <= 0.1d;
        return false;
    }

    private double getTx(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    }
}
