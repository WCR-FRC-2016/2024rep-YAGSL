package frc.robot.subsystems.Arm;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    private final CANSparkMax topMotorController;
    private final CANSparkMax bottomMotorController;

    public Shooter() {
        topMotorController    = new CANSparkMax(Constants.ShooterConstants.upperCanId, MotorType.kBrushless);
        bottomMotorController = new CANSparkMax(Constants.ShooterConstants.lowerCanId, MotorType.kBrushless);
    
        topMotorController.restoreFactoryDefaults();
        bottomMotorController.restoreFactoryDefaults();

        topMotorController.setSmartCurrentLimit(Constants.ShooterConstants.upperCurrentLimit);
        bottomMotorController.setSmartCurrentLimit(Constants.ShooterConstants.lowerCurrentLimit);

        topMotorController.setIdleMode(IdleMode.kCoast);
        bottomMotorController.setIdleMode(IdleMode.kCoast);

        topMotorController.setInverted(true);
        bottomMotorController.setInverted(true);

        topMotorController.setOpenLoopRampRate(0.5);
        bottomMotorController.setOpenLoopRampRate(0.5);

        topMotorController.burnFlash();
        bottomMotorController.burnFlash();
    }

    public void run() {
        run(1);
    }
    
    public void run(double factor){
        topMotorController.set(Constants.ShooterConstants.flywheelRunSpeed * factor);
        bottomMotorController.set(Constants.ShooterConstants.flywheelRunSpeed * factor);
    }
    

    public void stop() {
        topMotorController.set(0.0f);
        bottomMotorController.set(0.0f);
    }
}
