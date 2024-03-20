package frc.robot.commands.arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.swervedrive.drivebase.LimelightAmpAlign;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.subsystems.LedManager.LedManagerSubsystem;

public class AmpAngleCommand extends Command {
  Arm arm;
  LedManagerSubsystem ledManagerSubsystem;
  double angle;
    public AmpAngleCommand (Arm arm, double angle, LedManagerSubsystem ledManagerSubsystem){
        this.arm = arm; 
        this.angle = angle;
        this.ledManagerSubsystem = ledManagerSubsystem;
        addRequirements(arm);
    }
    @Override
    public void initialize(){
       arm.setAngle(angle);
    }
    @Override
    public void execute(){
        ledManagerSubsystem.setState(5);
    }
    @Override 
    public boolean isFinished(){
        return arm.atDesiredAngle();
    }
}

