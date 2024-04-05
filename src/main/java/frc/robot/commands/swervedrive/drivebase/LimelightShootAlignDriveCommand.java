package frc.robot.commands.swervedrive.drivebase;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.leds.LedManager;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class LimelightShootAlignDriveCommand extends Command {
    private SwerveSubsystem driveBase;
    private double desiredAngle;
    // private double[] botpose = new double[8];
    private double currentAngle;

    private final DoubleSupplier drive_x_supplier;
    private final DoubleSupplier drive_y_supplier;
    private final DoubleSupplier drive_rot_supplier;

    public LimelightShootAlignDriveCommand(SwerveSubsystem swerve, DoubleSupplier drive_x, DoubleSupplier drive_y, DoubleSupplier drive_rot) {
        driveBase = swerve;

        drive_x_supplier   = drive_x;
        drive_y_supplier   = drive_y;
        drive_rot_supplier = drive_rot;

        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        // NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(botpose);
        // desiredAngle = botpose[4];
        // double tx = getTx();
        // desiredAngle = driveBase.getHeading().getDegrees() - tx;
        System.out.println("TargetSpeakerCommand End");
    }

    @Override
    public void execute() {
        var tx = getTx();

        if (getTv())
            desiredAngle = driveBase.getHeading().getDegrees() - tx;
        else
            desiredAngle = driveBase.getHeading().getDegrees();
        currentAngle = tx;
        driveBase.drive(MathUtil.applyDeadband(drive_x_supplier.getAsDouble(), 0.25f) / 2.0f, MathUtil.applyDeadband(drive_y_supplier.getAsDouble(), 0.25f) / 2.0f, Math.toRadians(desiredAngle));

        if (getTv())
            LedManager.setState("ManualDrive");
        else
            LedManager.setState("NoLimelight");

        // if (!getTv()){
        // driveBase.drive(0, 0, driveBase.getHeading().getRadians() +
        // Math.toRadians(25) );
        // return;
        // }

        //var tx = getTx();
        // Still give the user control
        // if (!getTv()) {
        //     driveBase.driveCommand(drive_x_supplier, drive_y_supplier, drive_rot_supplier);
        //     LedManager.setState("NoLimelight");
        // } {
        //     LedManager.setState("Resting");
        //     desiredAngle = driveBase.getHeading().getDegrees() - tx;
        //     currentAngle = tx;
        // }
    }

    @Override 
    public boolean isFinished(){

        //if(getTv() && Math.abs(getTx()) < 1) {
        //   return true;
        //}

        return false;

    }

    private double getTx() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    }

    private boolean getTv() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0.0) > 0;
    }
    
}
