package frc.robot.leds.driver;

import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class PieceCollectedDriver implements ILedStateDriver {
    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        var piece = (int) Math.min(Math.max((int) Math.round((Math.sin(led_info.Time * 5f * Math.PI) + 1f) * 255), 0), 255);

        leds.fillAllHSV(124 / 2, (int)(255.0f * 0.53f), piece);
        //leds.fillAllRGB(0, piece, Math.max((int)(piece - 128), 0));
    }
}
