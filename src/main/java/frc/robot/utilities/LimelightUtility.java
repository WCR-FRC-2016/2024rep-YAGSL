package frc.robot.utilities;

import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightUtility {
    public static double[] getBotPos(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose_targetspace").getDoubleArray(new double[0]);
    }
}
