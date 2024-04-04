package frc.robot.leds.driver;

import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class SolidColorDriver implements ILedStateDriver {
    int r, g, b;

    public SolidColorDriver(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        leds.fillAllRGB(r, g, b);
    }
}
