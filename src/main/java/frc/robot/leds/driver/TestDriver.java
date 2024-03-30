package frc.robot.leds.driver;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;

public final class TestDriver implements ILedStateDriver {
    @Override
    public void setLEDS(AddressableLEDBuffer buffer, LedInfo led_info) {
        System.out.println("UPDATE LEDS");
        for (var i = 0; i < led_info.LedCount; i++)
            buffer.setRGB(i, 255, 0, 255);
    }
}