package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.Shooter;


    public class AutoShootStop extends Command{
     Shooter shooter;
    public AutoShootStop (Shooter shooter){
        this.shooter = shooter;
        addRequirements(shooter);
    }

     @Override
    public void initialize() {
        shooter.stop();
    }
    @Override
    public boolean isFinished(){
        return true;
    }
}

