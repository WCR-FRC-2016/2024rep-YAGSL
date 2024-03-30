package frc.robot.subsystems.Climber;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.CollectorConstants;

public class Climber extends SubsystemBase {
    DigitalInput highLeftClimberSensor;
    DigitalInput highRightClimberSensor;
    DigitalInput lowLeftClimberSensor;
    DigitalInput lowRightClimberSensor;

    private CANSparkMax leftMotorController;
    private CANSparkMax rightMotorController;



    public Climber() {
         leftMotorController = new CANSparkMax(Constants.ClimberConstants.leftCanId, MotorType.kBrushed);
         rightMotorController = new CANSparkMax(Constants.ClimberConstants.rightCanId, MotorType.kBrushed);

         leftMotorController.restoreFactoryDefaults();
         rightMotorController.restoreFactoryDefaults();

         leftMotorController.setIdleMode(IdleMode.kBrake);
         rightMotorController.setIdleMode(IdleMode.kBrake);

         rightMotorController.setSmartCurrentLimit(40); // Need to verify
         rightMotorController.setSmartCurrentLimit(40);

         leftMotorController.setInverted(true); // Need to verify
         rightMotorController.follow(leftMotorController, true);

         leftMotorController.burnFlash();
         rightMotorController.burnFlash();
         highLeftClimberSensor = new DigitalInput(2);
         highRightClimberSensor = new DigitalInput(3);
         lowLeftClimberSensor = new DigitalInput(4);
         lowRightClimberSensor = new DigitalInput(5);
    }
    public boolean climberLeftMaxHeight(){
        return highLeftClimberSensor.get();
    }

     public boolean climberLeftMinHeight(){
        return lowLeftClimberSensor.get();
    }

    public boolean climberRightMaxHeight(){
        return highRightClimberSensor.get();
    }

     public boolean climberRightMinHeight(){
        return lowRightClimberSensor.get();
    }

    public void driveLeft(Double magnitude) {
        leftMotorController.set(magnitude * ClimberConstants.runSpeed);
    }

    public void driveRight(Double magnitude) {
        rightMotorController.set(magnitude * ClimberConstants.runSpeed);
    }

}
