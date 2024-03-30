package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Arm;

public class AmpAngleCommand extends Command {
    Arm arm;
    double angle;

    public AmpAngleCommand(Arm arm, double angle) {
        this.arm = arm;
        this.angle = angle;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.setAngle(angle);
    }

    @Override
    public void execute() {
        //ledManagerSubsystem.setState(5);
    }

    @Override
    public boolean isFinished() {
        return arm.atDesiredAngle();
    }
}
