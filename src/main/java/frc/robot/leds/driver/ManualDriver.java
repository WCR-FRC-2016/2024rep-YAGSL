package frc.robot.leds.driver;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class ManualDriver implements ILedStateDriver {
    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        leds.fillAllRGB(255, 0, 255);
    }    
}
