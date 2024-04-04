package frc.robot.leds;

import java.util.HashMap;

import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.leds.driver.AutoAlignGradientDriver;
import frc.robot.leds.driver.ManualDriver;
import frc.robot.leds.driver.NoLimelightDriver;
import frc.robot.leds.driver.PieceCollectedDriver;
import frc.robot.leds.driver.RestingDriver;
import frc.robot.leds.driver.SolidColorDriver;
import frc.robot.leds.driver.StripTestDriver;

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

// Why is there a priority system, and how do I use it?
//
// The priority system is in place so that certain "high-priority" states (like a piece being collected)
// are not overridden by something we dont care about (like setting all the LEDs to blue for aestetics)
//
// Here is how the priority system works:
//  . Check if the priority is GREATER THAN OR EQUAL (>=) to the current priority
//      : If it is then set the current priority to it and use that new state
//      : If it isnt then dont do anything, something more important is being displayed
// . If the state doesn't exist, it resets everything to a default state and resets the priority to 0
//
// Using setState(name) will attempt to set the current led state using a priority of 0.
// Using setStateWithPriority(name, priority) will attempt to set the current led state using the specified priority
//  . This call should always have an accompaning resetState(name) call so the priority gets reset properly
// Using resetState(name) will attempt to set the current state to a default state (if the name is the same as the currently active state)
//  . This will only work if the current state has the same name as the provided state name
//  . This will reset the LedManager into a default state where no led drivers are run

public final class LedManager {
    // It kind of fits the Subsystem paradigm, but im making it a singleton
    private static LedManager instance = null;

    // Old LED info: 99 + 73; // Long LED panel, Shorter LED panel

    // Constants
    //private static final int LED_COUNT = 36 * 4;
    private static final int LED_COUNT = ((37 * 2) + 1) * 2; // 2 sides, 1 hidden LED, 37 leds per strip (2 per side)
    private static final int LED_PORT  = 1;

    // Runtime Variables
    private final AddressableLED       leds;
    private final AddressableLEDBuffer led_buffer;
    private final LedWrappr            led_wrapper;
    private final LedInfo              led_info;

    private long previous_time = 0; // In microseconds

    private String          current_state_name;
    private ILedStateDriver current_state_driver;
    private int             current_state_priority;

    private HashMap<String, ILedStateDriver> led_drivers = new HashMap<>();

    public static void initialize() {
        // Ensure this singleton hasn't been initialized
        if (instance != null)
            return;

        instance = new LedManager();
        instance.registerDrivers();
    }

    // Set the current LED Driver state (what LEDs are being shown)
    // 
    // This uses a priority of 0. If the priority is not 0 then the current
    // LED state will not be overridden!
    public static void setState(String state_name) { 
        if (instance == null)
            return;

        // Ensure there currently isn't a higher-priority LED state running.
        if (instance.current_state_priority > 0)
            return;
        
        instance.current_state_name     = state_name;
        instance.current_state_driver   = instance.led_drivers.getOrDefault(state_name, null);
        instance.current_state_priority = 0;

        if (instance.current_state_driver == null) {
            System.out.println("Led Driver with name [" + state_name + "] was not found, maybe it hasn't been registered?");

            // Ensure that the current state is completely reset
            clearState();
        }
    }

    public static void setStateWithPriority(String state, int priority) {
        if (instance == null)
            return;

        // Verify that this priority is at least equal to the current priority (return if not)
        if (priority < instance.current_state_priority)
            return;

        instance.current_state_name     = state;
        instance.current_state_driver   = instance.led_drivers.getOrDefault(state, null);
        instance.current_state_priority = priority;

        if (instance.current_state_driver == null) {
            System.out.println("Led Driver with name [" + state + "] was not found, maybe it hasn't been registered?");

            // Ensure that the current state is completely reset
            clearState();
        }
    }

    // This one may be named confusingly. Basically, if the current active state is equal to the state
    // passed in, it will set the state to null and set the priority to 0. THIS IS REQUIRED IF YOU USE
    // setStateWithPriority() as it will actually clear the priority!
    //
    // If the current state is not this state, it will just ignore it since it either has already been
    // called, or something with a higher priority is currently being run (and this shouldn't reset that)
    public static void resetState(String state) {
        if (instance ==null)
            return;

        // Verify that the current state is the same state as the one being passed in (if not, return)
        if (!instance.current_state_name.equals(state))
            return;

        // Reset the state to be the default, uninitialzied state.
        clearState();
    }

    // This simple sets the current state to be null/"Invalid", and resets the priority.
    // This shouldn't really be used anywhere but inside of here, but incase something messes up
    // there is a way to externally reset the current LED state.
    public static void clearState() {
        if (instance == null)
            return;

        instance.current_state_driver   = null;
        instance.current_state_name     = "Invalid";
        instance.current_state_priority = 0;
    }

    public static void ledPeriodic() { instance.periodic(); }

    private LedManager() {
        leds        = new AddressableLED(LED_PORT);
        led_buffer  = new AddressableLEDBuffer(LED_COUNT);
        led_wrapper = new LedWrappr(led_buffer);
        led_info    = new LedInfo();

        led_info.LedCount = LED_COUNT;
        led_info.Time     = 0.0f;

        current_state_name     = "Invalid";
        current_state_driver   = null;
        current_state_priority = 0;

        // Ensure ALL leds are in a known state (all white)
        for (var i = 0; i < LED_COUNT; i++)
            led_buffer.setRGB(i, 255, 255, 255);

        leds.setLength(LED_COUNT);
        leds.setData(led_buffer);
        leds.start();

        // Set the previous time to the current time (in microseconds)
        previous_time = WPIUtilJNI.now();
    }

    private void periodic() {
        var current_time = WPIUtilJNI.now();             // Get the current time (in microseconds)
        float delta_time = current_time - previous_time; // Calculate the time elapsed since this was last called (in microseconds)
        delta_time *= 1e-6f;                             // Convert this time to seconds (from microseconds) 
        previous_time = current_time;

        led_info.Time += delta_time;
        led_info.DeltaTime = delta_time;
        
        if (current_state_driver == null)
            return;

        // Error handling, prevent bad LED states from crashing the robot.
        try {
            current_state_driver.setLEDS(led_wrapper, led_info);
            leds.setData(led_buffer);
        } catch (RuntimeException e) {
            System.out.println("An error occured when trying to run an LED Driver [" + current_state_name + "]: \n" + e.getMessage());

            current_state_driver   = null;
            current_state_name     = "Invalid";
            current_state_priority = 0;

            // Set all LEDs to red to indicate "BAD - Something went wrong!!!"
            for (var i = 0; i < LED_COUNT; i++)
                led_buffer.setRGB(i, 255, 0, 255);
        }
    }

    // Function used to add new LED states to the LedManager. Read inside to see how it works
    private void registerDrivers() {
        // This is how you actually "register" a driver to be used by the LedManager.
        //  . The first part (the string) is the name used to call this driver.
        //      : This is done in setState(name), where name is the value you put in here.
        //  . The second part is an instance of the driver class you created in a separate file
        //
        //led_drivers.put("SampleDriver", new SampleDriver());

        led_drivers.put("Resting",       new RestingDriver());
        led_drivers.put("OdometryReset", new SolidColorDriver(255, 0, 255));

        led_drivers.put("AmpAlignLimelightVisible",   new AutoAlignGradientDriver());

        led_drivers.put("TopOfClimber", new SolidColorDriver(255, 255, 0));
        led_drivers.put("BottomOfClimber", new SolidColorDriver(0, 200, 255));

        led_drivers.put("ShootOnSight", new AutoAlignGradientDriver());

        led_drivers.put("CollectingNoPiece", new SolidColorDriver(255, 255, 0));
        led_drivers.put("PieceCollected",    new PieceCollectedDriver());

        led_drivers.put("NoLimelight", new NoLimelightDriver());

        // Other, non-good ones
        led_drivers.put("ManualDrive", new ManualDriver());
        led_drivers.put("StripTest",   new StripTestDriver());
        led_drivers.put("NoNetworkTable",   new SolidColorDriver(255, 255, 255));

        // This is the default driver, set it to whatever its actually supposed to be
        setState("Resting");
    }

}
