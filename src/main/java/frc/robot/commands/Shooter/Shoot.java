package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Shooter;

public class Shoot extends Command {
    Shooter shooter;
    public Shoot(Shooter shooter){
        this.shooter = shooter;
    }

     @Override
    public void execute() {
        shooter.run();
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
    
}