package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Shooter;

public class AutoShootStart extends Command{
     Shooter shooter;
    public AutoShootStart(Shooter shooter){
        this.shooter = shooter;
        addRequirements(shooter);
    }

     @Override
    public void initialize() {
        shooter.run();
    }
    @Override
    public boolean isFinished(){
        return true;
    }
}

