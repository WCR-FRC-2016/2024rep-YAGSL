package frc.robot.leds;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

// NOTE: The LedBuffer contained here is the same exact one that is found in LedManager!
//  . This means that updating this buffer also updates the one in LedManager (because they both "point" to the same object)!

// Quick little glossary incase you (the reader) get lost
//  . If you want, you can skip over this and come back when needed
//
// What is a method?
//
// A method (or as you may also know it, function) is a part of a class that contains code that can do
// things for the specific object. In the case of LedWrapper, an example of a method would be:
//       public void setRGB(int led, int r, int g, int b);
//
// Here is some information about the bits that make up this method:
//  . "public"  is saying that anyone can access this, even objects that aren't me
//  . "void"    is saying that this method doesn't return anything (like an int or boolean)
//  . "setRGB"  is the name of this method, you call it using "setRGB(values)"
//  . "int ..." is a "parameter", it basically gives data to the method that it can use inside of itself
//     : "int"  is the type of the object that must be passed in, which is an integer in this case
//     : "..."  is simply the variable name that the method itself uses, you can pass anything in (even a "raw" value like 10)

// What is this class though?
//
// LedWrapper is a simple wrapper around the AddressableLEDBuffer object provided by WPILIB.
// The goal of this class is to simplify the "drawing" of LEDs, making it easier to do things
// like:
//  . Setting all LEDs to a color
//  . Setting all LEDs in a range to a color
//  . Setting all LEDs in a range to a gradient of two colors
// All of this can be achieved by using some simple math (in most cases), or clever math if you're
// feeling fancy. However, this class is mainly here to make coloring the LEDs as simple as possible.

// How do I use this class?
//
// Using this class is pretty simple, actually! All you have to do is simple call one of the provided
// methods, pass in the parameters, and boom the LED(s) have been changed to what you wanted them to!
// If you're feeling extra fancy, you can even write to the AddressableLEDBuffer directly, but it
// shouldn't be necessary as this class has all of the methods AddressableLEDBuffer has when setting
// the state of individual LEDs.

// What does each method do?
//
//  . void setRGB(int led, int r, int g, int b) sets a single LED to a certain red (r), green (g), and blue (b) value.
//  These values are between 0 and 255, 255 meaning fully on for that channel, and 0 meaning off.
//
//  . void setHSV(int led, int h, int s, int v) sets a single LED to a certain hue (h), saturation (s), and brightness (v) value.
//  Since this isn't intuitive like RGB, here is what each value does
//     : Hue: basically the "color" the led will use. Must be between 0 and 180
//     : Saturation: How much "color" should be used. if at 0 it will be white, and if it 255 it will look like pure color
//     : Brightness: How much "light" does this led emit (basically). if 0 it will be off (or "black"), if at 255 it will be full brightness
//  If you want to look at a color picker to select HSV values, here is one I found from a quick google search:
//    . https://www.selecolor.com/en/hsv-color-picker/
//  (if the link is dead, or the website is no longer a hsv color picker, look away?)
//
//  . The rest are described where they are defined because I'm tired right now.

// TODO: Support multiple, "separate", led strips with the functions (besides using user-provided offsets)?
public final class LedWrappr {
    public final AddressableLEDBuffer LedBuffer;

    public void setRGB(int led, int r, int g, int b) { LedBuffer.setRGB(led, r, g, b); }
    public void setHSV(int led, int h, int s, int v) { LedBuffer.setHSV(led, h, s, v); }

    /**
     * A function to set the RGB values for a range of LEDs to a certain value. The
     * range end is exclusive, so if you were to fill the range 0 to 4, it would set
     * leds {0, 1, 2, 3} but not led 4, so its like a for loop.
     * @param start_led The led to begin filling
     * @param end_led The led to end filling with (does not get filled)
     * @param r The red component of the fill value.   [0-255]
     * @param g The green component of the fill value. [0-255]
     * @param b The blue component of the fill value.  [0-255]
     */
    public void fillRangeRGB(int start_led, int end_led, int r, int g, int b) {
        for (var i = start_led; i < end_led; i++)
            LedBuffer.setRGB(i, r, g, b);
    }
    // Sets a certain number of LEDs at a certain position to the specified color
    public void fillCountRGB(int start_led, int count, int r, int g, int b) { fillRangeRGB(start_led, start_led + count, r, g, b); }
    // Sets all of the LEDs on the strip to be the specific RGB value
    public void fillAllRGB(int r, int g, int b) { fillRangeRGB(0, LedBuffer.getLength(), r, g, b);}

    // Sets all of the LEDs in a range to be the gradient of two colors
    public void fillRangeRGBGradient(int start_led, int end_led, int start_r, int start_g, int start_b, int end_r, int end_g, int end_b) {
        int led_count = end_led - start_led;

        int red_per_led   = (end_r - start_r) / led_count;
        int green_per_led = (end_g - start_g) / led_count;
        int blue_per_led  = (end_b - start_b) / led_count;

        for (var i = start_led; i < end_led; i++)
            LedBuffer.setRGB(i, start_r + red_per_led, start_g + green_per_led, start_b + blue_per_led);
    }
    // Sets a certain number of LEDs at a certain position to be the gradient of two colors
    public void fillCountRGBGradient(int start_led, int count, int start_r, int start_g, int start_b, int end_r, int end_g, int end_b) { fillRangeRGBGradient(start_led, start_led + count, start_r, start_g, start_b, end_r, end_g, end_b); }
    // Sets all of the LEDs on the strip to be the gradient of two colors
    public void fillAllRGBGradient(int start_r, int start_g, int start_b, int end_r, int end_g, int end_b) { fillRangeRGBGradient(0, LedBuffer.getLength(), start_r, start_g, start_b, end_r, end_g, end_b); }

    // "Hidden" from things that aren't LedManager since you dont need to create multiple
    protected LedWrappr(AddressableLEDBuffer buffer) {
        LedBuffer = buffer;
    }
}
