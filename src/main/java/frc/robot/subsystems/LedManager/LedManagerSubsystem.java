package frc.robot.subsystems.LedManager;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LedManagerSubsystem extends SubsystemBase{
    private static final int ledLength = 90;
    AddressableLED led = new AddressableLED(0);
    AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(ledLength);
    private int state = 0;

    public LedManagerSubsystem(){

        led.setLength(ledBuffer.getLength());
        led.setData(ledBuffer);
        led.start();
    
    }

    @Override
    public void periodic(){
        switch(state){

            case 2:
                ledBuffer.setRGB(1, 255, 0, 0);
                led.setData(ledBuffer);
                break;

            case 1:
                fill(255, 0, 0);
                break; 

            case 0:
                
            default:
                fill(0, 0, 0);
                break;

        }
    }

    private void fill(int r, int g, int b){

        for( int i = 0; i < ledLength; i++){
            ledBuffer.setRGB(i, r, g, b);
        }

        led.setData(ledBuffer);

    }

    public void setState(int s){
        state = s;
    }
}