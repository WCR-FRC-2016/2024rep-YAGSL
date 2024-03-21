package frc.robot.subsystems.Climber;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CollectorConstants;

public class Climber extends SubsystemBase {
    DigitalInput pieceSensor;

    private CANSparkMax leftMotorController;
    private CANSparkMax rightMotorController;

    public Climber() {
        leftMotorController = new CANSparkMax(Constants.ClimberConstants.leftCanId, MotorType.kBrushed);
        rightMotorController = new CANSparkMax(Constants.ClimberConstants.rightCanId, MotorType.kBrushed);

        leftMotorController.restoreFactoryDefaults();
        rightMotorController.restoreFactoryDefaults();

        leftMotorController.setIdleMode(IdleMode.kBrake);
        rightMotorController.setIdleMode(IdleMode.kBrake);

        leftMotorController.setSmartCurrentLimit(40); // Need to verify
        rightMotorController.setSmartCurrentLimit(40);

        leftMotorController.setInverted(true); // Need to verify
        rightMotorController.follow(leftMotorController, true);

        leftMotorController.burnFlash();
        rightMotorController.burnFlash();
    }

    public void drive(double magnitude) {
        leftMotorController.set(magnitude * CollectorConstants.runSpeed);
    }

}
