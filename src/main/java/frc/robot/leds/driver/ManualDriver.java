package frc.robot.leds.driver;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;

public final class ManualDriver implements ILedStateDriver {
    @Override
    public void setLEDS(AddressableLEDBuffer buffer, LedInfo led_info) {
        for (var i = 0; i < led_info.LedCount; i++) {
            int led_value = 255;

            var sin_value = Math.sin((led_info.Time * 1.0f) + (i * 0.25f));
            sin_value += 1.0f;
            sin_value /= 2.0f;

            led_value *= sin_value;

            led_value = Math.min(led_value, 255);
            
            buffer.setRGB(i, led_value, 0, 0);
        }
    }    
}
