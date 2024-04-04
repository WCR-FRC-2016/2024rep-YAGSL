package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Shooter;

public class AutoShootCommand extends Command {
    Shooter shooter;

    public AutoShootCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.run();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}