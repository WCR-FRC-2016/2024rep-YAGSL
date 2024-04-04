package frc.robot.leds.driver;

import frc.robot.leds.ILedStateDriver;
import frc.robot.leds.LedInfo;
import frc.robot.leds.LedWrappr;

public final class SampleDriver implements ILedStateDriver {
    private int test_value = 255;

    @Override
    public void setLEDS(LedWrappr leds, LedInfo led_info) {
        // This is where the code that sets the led colors goes,
        // the LedWrapper class contains some useful functions for handling multiple
        // LEDs at once, or individual ones
        //
        // If you want to completely ignore this class, you can directly access the
        // AddressableLEDBuffer by stating:
        //       var <buffer name> = leds.LedBuffer
        // where <buffer name> can be whatever you want to call it.
        // From there, you can simple ball <buffer name>.setRGB(...) or whatever methods it has,
        // but I highly recommend using leds.<name>(...) instead.
        //
        // For information on what LedWrapper can do, ctrl+click LedWrapper or open LedWrapper.java
        //
        //
        // There is also LedInfo, which gives some simple things like the elapsed time so you can
        // do some fancy things using math (like Math.sin() for "waves" and such)

        // You can remove this if you want to, this is here as a sample
        leds.fillAllRGB(255, 255, 255); // Set the entire strip to be white
        leds.fillRangeRGB(10, 20, 255, 0, 0); // Set the 10th LED to the 20th LED to be red
        leds.setRGB(0, 0, 0, 0); // Turn off the first LED on the strip

        var buffer = leds.LedBuffer;
        buffer.setRGB(1, 255, 0, 255); // Set the second LED on the strip to be Magenta (using raw buffer access)
    
        // An example of the time variable in use.
        //  . The time is in seconds, so 0.5 is half a second, 1 is 1 second, etc...
        int clamped_time = ((int) Math.floor(led_info.Time * 10)) % led_info.LedCount;
        buffer.setRGB(clamped_time, 0, test_value, 0);
    }
}
