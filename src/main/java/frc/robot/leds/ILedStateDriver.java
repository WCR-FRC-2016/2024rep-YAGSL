package frc.robot.leds;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public interface ILedStateDriver {
    public void setLEDS(AddressableLEDBuffer buffer, LedInfo led_info);
}
