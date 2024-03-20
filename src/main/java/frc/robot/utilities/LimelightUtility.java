package frc.robot.utilities;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class LimelightUtility {
    public static double[] getBotPos(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose_targetspace").getDoubleArray(new double[0]);
    }

    // MAKE SURE YOU USE getTeamAprilTagID() if using relative ID for teams!!!
    //  . I think passing 0 will reset it, but I cant be 100% sure. Its not listed anywhere
    public static void setTargetedAprilTag(int id) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("priorityid").setInteger(id);
    }

    // Converts a red-field oriented april tag ID to a blue team IF AND ONLY IF your alliance is BLUE
    public static int getTeamAprilTagID(int red_id) {
        var alliance = DriverStation.getAlliance();

        if (!alliance.isEmpty() && alliance.get().equals(Alliance.Blue)) {
            switch(red_id) {
                case 3:
                    return 8;
                case 4:
                    return 7;
                case 5:
                    return 6;
                case 9:
                    return 2;
                case 10:
                    return 1;
                case 11:
                    return 16;
                case 13:
                    return 14;
                case 12:
                    return 15;
                // Pass through the ID, it is invalid
                default:
                    return red_id;
            }
        }

        return red_id;
    }
}
