package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class LimelightAmpAlign extends Command {
    private SwerveSubsystem driveBase;
    private double desiredAngle;
    private double currentAngle;
    private static final double desiredDistanceZ = -0.25;
    private double actualDistanceZ;
    private double actualDistanceX;
    private double turnMagnitude = 1;
    private boolean closeToTarget = false;
    public LimelightAmpAlign(SwerveSubsystem swerve){
        driveBase = swerve;
        addRequirements(driveBase);

    }

    @Override
    public void initialize() {
        closeToTarget = false;
        //NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(botpose);
        //desiredAngle = botpose[4];
        // double tx = getTx();
        // desiredAngle = driveBase.getHeading().getDegrees() - tx;
    }

    @Override
    public void execute() {

        var botpose = getBotPos();

        // Check connection to Network table
        if (botpose.length == 0 ){ 
            driveBase.drive(0, 0, driveBase.getHeading().getRadians());
            return;
        }

        //Check if able to see April Tag and if close to target stop moving and if not seek
        if (botpose[0] == 0 && botpose[1] == 0 && botpose[2] == 0){
            if(closeToTarget == true){
                driveBase.drive(0, 0, driveBase.getHeading().getRadians());
            }
            else{
                driveBase.drive(0, 0, driveBase.getHeading().getRadians() + Math.toRadians(25) * turnMagnitude);
            }
            return;
        }

        System.out.println(botpose[2]);
        actualDistanceX = botpose[0];
        actualDistanceZ = botpose[2];
        double distanceToMoveY = -1 * (actualDistanceZ - desiredDistanceZ);
        double distanceToMoveX = -1 * (actualDistanceX);
        desiredAngle = driveBase.getHeading().getDegrees() - getTx();
        //currentAngle = botpose[4];
        driveBase.drive(distanceToMoveX * 0.3, distanceToMoveY * 0.3,  Math.toRadians(desiredAngle)); 
        currentAngle = getTx();
        turnMagnitude = currentAngle != 0.0d ? currentAngle/Math.abs(currentAngle) * -1.0d :1.0d;
        //NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(botpose);
        
        //System.out.println(currentAngle);
        
        if (Math.abs(distanceToMoveY) <= 0.3){
            closeToTarget = true;
        }
    }

    @Override 
    public boolean isFinished(){
        //return  Math.abs( currentAngle) <= 0.1d;
        return false;
    }

    private double getTx(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    }

    private double[] getBotPos(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose_targetspace").getDoubleArray(new double[0]);
    }
}
