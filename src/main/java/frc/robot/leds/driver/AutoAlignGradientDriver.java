package frc.robot.leds.driver;

import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class AutoAlignGradientDriver implements ILedStateDriver {
    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        leds.StripA.fillAllRGBGradient(255, 0, 0, 0, 0, 255);
        leds.StripB.fillAllRGBGradient(0, 0, 255, 255, 0, 0);
        leds.StripC.fillAllRGBGradient(255, 0, 0, 0, 0, 255);
        leds.StripD.fillAllRGBGradient(0, 0, 255, 255, 255, 0);

        
    }
}