package frc.robot.leds.driver;

import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class StripTestDriver implements ILedStateDriver {
    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        leds.StripA.fillAllRGB(255, 0, 0);
        leds.StripB.fillAllRGB(0, 255, 0);
        leds.StripC.fillAllRGB(0, 0, 255);
        leds.StripD.fillAllRGBGradient(0, 0, 0, 255, 255, 255);
        //leds.fillAllRGB(255, 255, 255);
    }
}
