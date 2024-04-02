package frc.robot.leds.driver;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public final class LedTest {
    public static AddressableLEDBuffer LedBuffer;
    public static int ledNum = 100;
    public LedTest(AddressableLEDBuffer buffer) {
        LedBuffer = buffer;    
    }

    public static void setRGB(int led, int r, int g, int b) { LedBuffer.setRGB(led, r, g, b); }
    public static void setHSV(int led, int h, int s, int v) { LedBuffer.setHSV(led, h, s, v); }
    
    public static void ledFill(int R, int G, int B){
        for(int i = 0; i < ledNum; i++){
            setRGB(i, R, G, G);
        }
    }
}
