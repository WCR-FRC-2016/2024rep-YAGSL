package frc.robot.leds;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.leds.driver.ManualDriver;
import frc.robot.leds.driver.RestingDriver;
import frc.robot.leds.driver.TestDriver;

// 99 LEDS on strip

public final class LedManager {
    // It kind of fits the Subsystem paradigm, but im making it a singleton
    private static LedManager instance = null;

    // Constants
    private static final int LED_COUNT = 99;
    private static final int LED_PORT = 1;

    // Runtime Variables
    private final AddressableLED       leds;
    private final AddressableLEDBuffer led_buffer;
    private final LedInfo              led_info;

    private int current_state = 0;

    private ArrayList<ILedStateDriver> led_drivers = new ArrayList<ILedStateDriver>();

    public static void initialize() {
        // Ensure this singleton hasn't been initialized
        if (instance != null)
            return;

        instance = new LedManager();
    }

    public static void setState(int state) { 
        if (instance == null)
            return;
        
        instance.current_state = state; 
    }

    public static void ledPeriodic() { instance.periodic(); }

    private LedManager() {
        leds       = new AddressableLED       (LED_PORT);
        led_buffer = new AddressableLEDBuffer (LED_COUNT);
        led_info   = new LedInfo();

        led_info.LedCount = LED_COUNT;
        led_info.Time     = 0.0f;

        current_state = 0;

        // Ensure ALL leds are in a known state (all white)
        for (var i = 0; i < LED_COUNT; i++)
            led_buffer.setRGB(i, 255, 255, 255);

        leds.setLength(LED_COUNT);
        leds.setData(led_buffer);
        leds.start();

        registerDrivers();
    }

    private void periodic() {
        led_info.Time += 0.02f;
        
        if (current_state < 0 || current_state >= led_drivers.size())
            return;

        ILedStateDriver driver = led_drivers.get(current_state);
        driver.setLEDS(led_buffer, led_info);
        leds.setData(led_buffer);
    }

    private void registerDrivers() {
        led_drivers.add(new RestingDriver());
        led_drivers.add(new ManualDriver());
        //led_drivers.add(new TestDriver());
    }
}
