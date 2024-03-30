package frc.robot.leds.driver;

import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class PieceCollectedDriver implements ILedStateDriver {
    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        var piece = (int) Math.min(Math.max((int) Math.round(Math.sin(led_info.Time * 2.0f * Math.PI) * 255), 0), 255);

        leds.fillAllRGB(piece, piece, piece);
    }
}
