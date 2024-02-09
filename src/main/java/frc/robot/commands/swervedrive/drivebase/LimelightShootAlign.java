package frc.robot.commands.swervedrive.drivebase;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class LimelightShootAlign extends Command {
    private SwerveSubsystem driveBase;
    private double desiredAngle;
   // private double[] botpose = new double[8];
    private double currentAngle;
    public LimelightShootAlign(SwerveSubsystem swerve){
        driveBase = swerve;
        addRequirements(driveBase);

    }

    @Override
    public void initialize() {
        
        //NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(botpose);
        //desiredAngle = botpose[4];
        // double tx = getTx();
        // desiredAngle = driveBase.getHeading().getDegrees() - tx;
    }

    @Override
    public void execute() {
        desiredAngle = driveBase.getHeading().getDegrees() - getTx();
        driveBase.drive(0, 0, Math.toRadians(desiredAngle));
        currentAngle = getTx();
        //NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(botpose);
        //currentAngle = botpose[4];
        System.out.println(currentAngle);
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
