package frc.robot.subsystems.Arm;
import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty.Type;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Arm  extends SubsystemBase{

CANSparkMax masterMotorController = new CANSparkMax(7, MotorType.kBrushless);
CANSparkMax slaveMotorController = new CANSparkMax(12, MotorType.kBrushless);
SparkPIDController pidController;
AbsoluteEncoder encoder;

    public Arm(){

        masterMotorController.restoreFactoryDefaults();
        slaveMotorController.restoreFactoryDefaults();

        masterMotorController.setIdleMode(IdleMode.kBrake);
        slaveMotorController.setIdleMode(IdleMode.kBrake);

        masterMotorController.setSmartCurrentLimit(40);
        slaveMotorController.setSmartCurrentLimit(40);

        pidController = masterMotorController.getPIDController();

        encoder = masterMotorController.getAbsoluteEncoder(com.revrobotics.SparkAbsoluteEncoder.Type.kDutyCycle);

        pidController.setP(5);
        pidController.setI(1e-4);
        pidController.setD(0.75);
        pidController.setIZone(0);
        pidController.setFF(0);
        pidController.setOutputRange(-1, 1.0);
        pidController.setFeedbackDevice(encoder);

        masterMotorController.setInverted(true);
        slaveMotorController.follow(masterMotorController, true);

        masterMotorController.burnFlash();
        slaveMotorController.burnFlash();

        //setAngle(0.5);
        setAngle(1.0);
        //0.789
        //0.46

        
    }

        public void setAngle(double desiredAngle){
            desiredAngle = Math.max(Math.min(0.789, desiredAngle), 0.46);
            pidController.setReference(desiredAngle, ControlType.kPosition);
        }

    // public double getDesiredAngle(){
    //     var botpose = LimelightUtility.getBotPos();
    //     // Take distance from robot to speaker
    //     // tall is always 6' 10" subtracted by hight of shooter
    //     // angle = atan(distance - any offsets, height - arm height)
    //     return Math.atan(Constants.FieldDemensions.SpeakerHeight - armHeight() / botpose[2] );
    // }

    // private double armHeight(){
    //     //take arm length (Hypotonuse)
    //     //take arm angle from CANcoder
    //     //find Height with sin()
    //     return Math.sin(Math.toRadians(currentAngleEncoder.getPosition().getValueAsDouble())) * Constants.RobotDemensions.ArmLength;
    // }


}
