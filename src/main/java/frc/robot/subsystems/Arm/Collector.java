package frc.robot.subsystems.Arm;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CollectorConstants;
import frc.robot.subsystems.LedManager.LedManagerSubsystem;
import edu.wpi.first.wpilibj.PWM;

public class Collector  extends SubsystemBase { 
    // LedManagerSubsystem ledManager;
    //DigitalInput pieceSensor;
    DutyCycleEncoder pieceSensor;
    private CANSparkMax collectorMotorController;

    public Collector(LedManagerSubsystem ledManager){
        collectorMotorController = new CANSparkMax(Constants.CollectorConstants.canId, MotorType.kBrushed);

        // this.ledManager = ledManager;
        collectorMotorController.restoreFactoryDefaults();
        collectorMotorController.setIdleMode(IdleMode.kCoast);
        collectorMotorController.setSmartCurrentLimit(Constants.CollectorConstants.currentLimit);
        collectorMotorController.setInverted(true);
        collectorMotorController.burnFlash();

        pieceSensor = new DutyCycleEncoder(1);
        //pieceSensor = new DigitalInput(0);
    }
     
    public boolean hasPiece(){

        if (pieceSensor.getDistance() < 0.5){
        return true; //pieceSensor.get();
        }

        else{
            return false;
        }
    }

    public void printSensorValue(){

        System.out.println("Connected: " + pieceSensor.isConnected());

        System.out.println("Raw Value: " + pieceSensor.get());

        System.out.println("Distance:  " + pieceSensor.getDistance());

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

