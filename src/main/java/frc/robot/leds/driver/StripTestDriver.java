package frc.robot.leds.driver;

import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class StripTestDriver implements ILedStateDriver {
    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        leds.fillAllRGB(255, 255, 255);
        
    }
}
