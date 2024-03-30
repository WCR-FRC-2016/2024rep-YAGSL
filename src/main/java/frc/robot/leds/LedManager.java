package frc.robot.leds;

import java.util.HashMap;

import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.leds.driver.ManualDriver;
import frc.robot.leds.driver.RestingDriver;

// 99 LEDS on strip

// How do I add a new "state" to the LedManager?
//
// LedManager states are controlled by what I call "drivers", they basically tell the LEDs what
// color/state they are supposed to be in. Each driver is created in a separate file, with a method
// that is called every Robot Periodic. If an invalid driver is specified, the LEDs will retain their
// last valid state. Currently, nothing is logged or outputted when an invalid state is set, so be 
// careful (im too tired to add it right now).
//
// Here is the basic flow behind adding a new LED state:
//  . Create a new file under the driver directory
//  . "implements" the "ILedStateDriver.java" interface and override the "setLEDs(...)" method
//  . Write your fancy LED code in the file, using the setLEDs method to change the led colors
//  . Come back to this file, and go to the very bottom (in a methon named "registerDrivers()")
//  . Call "led_drivers.put("<Driver Name Goes Here>, new <Name of the file you created>()")"
//  . Profit.
//
// All LED drivers are "named", and this UNIQUE name is used when calling setState() 
// (which can now be done anywhere using LedManager.setState(name) method since this is a "singleton")
//  . Calling this method sets that driver to be the "active" led state driver, the leds will run the
//  code specified in that file.
// BE CAREFUL, if this method is called with a name that is not registered, the LEDs will appear to
// "freeze". This is because the LEDs are no longer getting updated since there isn't a driver
// to actually run them (this is done to prevent the robot from just crashing)

public final class LedManager {
    // It kind of fits the Subsystem paradigm, but im making it a singleton
    private static LedManager instance = null;

    // Constants
    private static final int LED_COUNT = 99;
    private static final int LED_PORT  = 1;

    // Runtime Variables
    private final AddressableLED       leds;
    private final AddressableLEDBuffer led_buffer;
    private final LedWrappr            led_wrapper;
    private final LedInfo              led_info;

    private long previous_time = 0; // In microseconds?

    // TODO: Priority system so that certain ones don't get overridden 
    private String          current_state_name;
    private ILedStateDriver current_state_driver;

    //private ArrayList<ILedStateDriver> led_drivers = new ArrayList<ILedStateDriver>();
    private HashMap<String, ILedStateDriver> led_drivers = new HashMap<>();

    public static void initialize() {
        // Ensure this singleton hasn't been initialized
        if (instance != null)
            return;

        instance = new LedManager();
    }

    public static void setState(String state_name) { 
        if (instance == null)
            return;
        
        instance.current_state_name   = state_name;
        instance.current_state_driver = instance.led_drivers.getOrDefault(state_name, null);
    }

    public static void ledPeriodic() { instance.periodic(); }

    private LedManager() {
        leds        = new AddressableLED(LED_PORT);
        led_buffer  = new AddressableLEDBuffer(LED_COUNT);
        led_wrapper = new LedWrappr(led_buffer);
        led_info    = new LedInfo();

        led_info.LedCount = LED_COUNT;
        led_info.Time     = 0.0f;

        current_state_name   = "Unnamed";
        current_state_driver = null;

        // Ensure ALL leds are in a known state (all white)
        for (var i = 0; i < LED_COUNT; i++)
            led_buffer.setRGB(i, 255, 255, 255);

        leds.setLength(LED_COUNT);
        leds.setData(led_buffer);
        leds.start();

        registerDrivers();

        // Im assuming this is in microseconds?
        previous_time = WPIUtilJNI.now();
    }

    private void periodic() {
        var current_time = WPIUtilJNI.now();             // Get the current time (in microseconds?)
        float delta_time = current_time - previous_time; // Calculate the time elapsed since this was last called (in microseconds)
        delta_time /= 1e-6f;                             // Convert this time to seconds (from microseconds?) 

        led_info.Time += delta_time;
        led_info.DeltaTime = delta_time;
        
        if (current_state_driver == null)
            return;

        current_state_driver.setLEDS(led_wrapper, led_info);
        leds.setData(led_buffer);
    }

    private void registerDrivers() {
        // This is how you actually "register" a driver to be used by the LedManager.
        //  . The first part (the string) is the name used to call this driver.
        //      : This is done in setState(name), where name is the value you put in here.
        //  . The second part is an instance of the driver class you created in a separate file
        //
        //led_drivers.put("SampleDriver", new SampleDriver());

        led_drivers.put("Resting",     new RestingDriver());
        led_drivers.put("ManualDrive", new ManualDriver());

        // This is the default driver, set it to whatever its actually supposed to be
        setState("Resting");
    }
}
