package frc.robot.leds;

public final class LedStrip {
    private final LedWrappr owner;
    
    public final int LEDStart;
    public final int LEDCount;

    public void setRGB(int led, int r, int g, int b) { owner.setRGB(led + LEDStart, r, g, b); }
    public void setHSV(int led, int h, int s, int v) { owner.setHSV(led + LEDStart, h, s, v); }

    public void fillRangeRGB(int start_led, int end_led, int r, int g, int b) { owner.fillRangeRGB(start_led + LEDStart, end_led + LEDStart, r, g, b); }
    public void fillCountRGB(int start_led, int count, int r, int g, int b) { owner.fillRangeRGB(start_led + LEDStart, start_led + count + LEDStart, r, g, b); }
    public void fillAllRGB(int r, int g, int b) { owner.fillRangeRGB(LEDStart, LEDStart + LEDCount, r, g, b);}

    public void fillRangeRGBGradient(int start_led, int end_led, int start_r, int start_g, int start_b, int end_r, int end_g, int end_b) { owner.fillRangeRGBGradient(start_led + LEDStart, end_led + LEDStart, start_r, start_g, start_b, end_r, end_g, end_b);}
    public void fillCountRGBGradient(int start_led, int count, int start_r, int start_g, int start_b, int end_r, int end_g, int end_b) { owner.fillRangeRGBGradient(start_led + LEDStart, start_led + count + LEDStart, start_r, start_g, start_b, end_r, end_g, end_b); }
    public void fillAllRGBGradient(int start_r, int start_g, int start_b, int end_r, int end_g, int end_b) { owner.fillRangeRGBGradient(LEDStart, LEDStart + LEDCount, start_r, start_g, start_b, end_r, end_g, end_b); }

    protected LedStrip(LedWrappr owner, int start_index, int count) {
        this.owner = owner;
        LEDStart   = start_index;
        LEDCount   = count;
    }
}
