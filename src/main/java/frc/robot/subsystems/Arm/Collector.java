package frc.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.LedManager.LedManagerSubsystem;

public class Collector  extends SubsystemBase { 
    LedManagerSubsystem ledManager;
    DigitalInput pieceSensor;
    public Collector(LedManagerSubsystem ledManager){
        this.ledManager = ledManager;
        pieceSensor = new DigitalInput(0);
    }
     
    public boolean hasPiece(){
        return pieceSensor.get();
    }

    public void run(){
        ledManager.setState(2);
    }

    public void stop(){
        ledManager.setState(0);
    }
    
}

