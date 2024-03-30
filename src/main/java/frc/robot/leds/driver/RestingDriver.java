package frc.robot.leds.driver;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;

public final class RestingDriver implements ILedStateDriver {
    @Override
    public void setLEDS(AddressableLEDBuffer buffer, LedInfo led_info) {
        var offset_factor = 180 / led_info.LedCount;
        int led_color_offset = (int) Math.floor(led_info.Time * 20.0f);
        
        for (var i = 0; i < led_info.LedCount; i++) {
            final var value = ((led_color_offset + i) * offset_factor) % 180; 
            buffer.setHSV(i, value, 255, 128);
        }
    }
    
}
