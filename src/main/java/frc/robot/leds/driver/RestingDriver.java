package frc.robot.leds.driver;

import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class RestingDriver implements ILedStateDriver {
    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        //leds.fillAllRGB(255, 0, 0);

        //leds.fillAllRGBGradient(255, 0, 0, 0, 0, 255);

        var offset_factor = 180 / led_info.LedCount;
        //int led_color_offset = (int) Math.floor(led_info.Time * 20.0f);
        int led_color_offset = (int) Math.floor(led_info.Time * 10);

        for (var i = 0; i < led_info.LedCount; i++) {
            final var value = ((led_color_offset + i) * offset_factor) % 180; 
            leds.LedBuffer.setHSV(i, value, 255, 128);
        }
    }
    
}
