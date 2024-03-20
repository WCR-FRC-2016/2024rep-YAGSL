package frc.robot.commands.debug;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.subsystems.Arm.Collector;
import frc.robot.utilities.LimelightUtility;

public class DebugCommand extends Command {
    Collector collector;
    Arm arm;

    public DebugCommand(Collector collector, Arm arm) {
        this.arm = arm;
        this.collector = collector;
        addRequirements(arm, collector);

    }

    @Override
    public void execute() {
        var botpose = LimelightUtility.getBotPos();
        System.out.println(arm.getAngle());
        System.out.println(Math.abs(botpose[2]));
    }

    public double getTx() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
