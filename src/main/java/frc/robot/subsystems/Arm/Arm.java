package frc.robot.subsystems.Arm;

import com.ctre.phoenix6.hardware.CANcoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.LedManager.LedManagerSubsystem;
import frc.robot.utilities.LimelightUtility;

public class Arm  extends SubsystemBase{
    private CANcoder currentAngleEncoder = new CANcoder(36);// come back and assign the actual id
    LedManagerSubsystem ledManager;

    public Arm(LedManagerSubsystem ledManager){
        this.ledManager = ledManager;
    }

    public double getActualAngle(){
       return currentAngleEncoder.getPosition().getValue(); 
    }

    public double getDesiredAngle(){
        var botpose = LimelightUtility.getBotPos();
        // Take distance from robot to speaker
        // tall is always 6' 10" subtracted by hight of shooter
        // angle = atan(distance - any offsets, height - arm height)
        return Math.atan(Constants.FieldDemensions.SpeakerHeight - armHeight() / botpose[2] );
    }

    private double armHeight(){
        //take arm length (Hypotonuse)
        //take arm angle from CANcoder
        //find Height with sin()
        return Math.sin(Math.toRadians(currentAngleEncoder.getPosition().getValueAsDouble())) * Constants.RobotDemensions.ArmLength;
    }

    public void run(){
        ledManager.setState(2);       
    }

    public void stop(){
        ledManager.setState(0);
    }

}
