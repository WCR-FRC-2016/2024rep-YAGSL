package frc.robot.leds.driver;

import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class NoLimelightDriver implements ILedStateDriver {
    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        leds.fillAllRGB(255, 0, 0);

        for (var i = 0; i < led_info.LedCount; i++) {
            if (i % 3 == 0)
                leds.setRGB(i, 0, 255, 0);
        }
    }
}
