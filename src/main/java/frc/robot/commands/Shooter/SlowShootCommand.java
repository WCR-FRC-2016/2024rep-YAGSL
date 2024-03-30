package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Shooter;

public class SlowShootCommand extends Command {
    Shooter shooter;

    public SlowShootCommand(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.run(0.4);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }

}