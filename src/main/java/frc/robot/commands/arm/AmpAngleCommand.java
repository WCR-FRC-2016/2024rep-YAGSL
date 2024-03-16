package frc.robot.commands.arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.swervedrive.drivebase.LimelightAmpAlign;
import frc.robot.subsystems.Arm.Arm;

public class AmpAngleCommand extends Command {
  Arm arm;
    public AmpAngleCommand (Arm arm){
        this.arm = arm;
    }
    @Override
    public void initialize(){
       arm.setAngle(0.7);
    }
    @Override 
    public boolean isFinished(){
        return arm.atDesiredAngle();
    }
}

