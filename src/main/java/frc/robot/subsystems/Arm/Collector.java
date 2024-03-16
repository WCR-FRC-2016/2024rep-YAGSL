package frc.robot.subsystems.Arm;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CollectorConstants;
import frc.robot.subsystems.LedManager.LedManagerSubsystem;

public class Collector  extends SubsystemBase { 
    // LedManagerSubsystem ledManager;
    DigitalInput pieceSensor;
    // DutyCycleEncoder pieceSensor;
    private CANSparkMax collectorMotorController;

    public Collector(LedManagerSubsystem ledManager){
        collectorMotorController = new CANSparkMax(Constants.CollectorConstants.canId, MotorType.kBrushed);

        // this.ledManager = ledManager;
        collectorMotorController.restoreFactoryDefaults();
        collectorMotorController.setIdleMode(IdleMode.kCoast);
        collectorMotorController.setSmartCurrentLimit(Constants.CollectorConstants.currentLimit);
        collectorMotorController.setInverted(true);
        collectorMotorController.burnFlash();

        // pieceSensor = new DutyCycleEncoder(0);
        pieceSensor = new DigitalInput(0);
    }
     
    public boolean hasPiece(){
        return !pieceSensor.get();
    }

    public void printSensorValue(){
        System.out.println("Raw Value: " + pieceSensor.get());
    }

    public void run(){
        // ledManager.setState(2);   
        collectorMotorController.set(CollectorConstants.runSpeed);    
    }

    public void stop(){
        // ledManager.setState(0);
        collectorMotorController.set(0.0);
    }
    
}

