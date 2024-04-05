package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;

public class ControllerRumble extends Command {
    XboxController xboxController;
    double input;
    public ControllerRumble(XboxController xboxController, double input){
        this.xboxController = xboxController;
        this.input = input;
    }
    @Override
    public void execute() {
        xboxController.setRumble(RumbleType.kBothRumble, input);
    }

    @Override
    public void end(boolean interrupted) {
        xboxController.setRumble(RumbleType.kBothRumble, 0);
    }
}
