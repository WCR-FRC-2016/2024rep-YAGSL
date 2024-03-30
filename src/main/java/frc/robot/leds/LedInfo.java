package frc.robot.leds;

public final class LedInfo {
    public float Time      = 0.0f; // Total time that has elapsed since the robot has turned on (in seconds)
    public float DeltaTime = 0.0f; // The amount of time that has elapsed since this driver was last called (in seconds)
    public int   LedCount  = 0;    // The number of LED's that are on the strip
}
