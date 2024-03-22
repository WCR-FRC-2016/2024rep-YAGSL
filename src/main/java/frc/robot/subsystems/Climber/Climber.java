package frc.robot.subsystems.Climber;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CollectorConstants;

public class Climber extends SubsystemBase {
    DigitalInput highClimberSensor;
    DigitalInput lowClimberSensor;

    private CANSparkMax masterMotorController;
    private CANSparkMax slaveMotorController;



    public Climber() {
         masterMotorController = new CANSparkMax(Constants.ClimberConstants.masterCanId, MotorType.kBrushed);
         slaveMotorController = new CANSparkMax(Constants.ClimberConstants.slaveCanId, MotorType.kBrushed);

         masterMotorController.restoreFactoryDefaults();
         slaveMotorController.restoreFactoryDefaults();

         masterMotorController.setIdleMode(IdleMode.kBrake);
         slaveMotorController.setIdleMode(IdleMode.kBrake);

         slaveMotorController.setSmartCurrentLimit(40); // Need to verify
         slaveMotorController.setSmartCurrentLimit(40);

         masterMotorController.setInverted(true); // Need to verify
         slaveMotorController.follow(masterMotorController, true);

         masterMotorController.burnFlash();
         slaveMotorController.burnFlash();
         highClimberSensor = new DigitalInput(2);
         lowClimberSensor = new DigitalInput(3);
    }
    public boolean climberMaxHeight(){
        return highClimberSensor.get();
    }

     public boolean climberMinHeight(){
        return lowClimberSensor.get();
    }

    public void drive(Double magnitude) {
        masterMotorController.set(magnitude * CollectorConstants.runSpeed);
    }

}
