// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;
import swervelib.parser.PIDFConfig;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be
 * declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
  public static final Matter CHASSIS = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME = 0.13; // s, 20ms + 110ms sprk max velocity lag

  public static final class Auton {

    public static final PIDFConfig TranslationPID = new PIDFConfig(0.7, 0, 0);
    public static final PIDFConfig angleAutoPID = new PIDFConfig(0.4, 0, 0.01);

    public static final double MAX_ACCELERATION = 2;

    // The default command to run incase an invalid command (that doesn't exist) is
    // selected
    // THIS IS CASE SENSITIVE
    public static final String DEFAULT_AUTO_NAME = "DriveDefault";
    // In order to add more commands, just put the PathPlanner name as an index in
    // this array,
    // it will get handled from there. USE THE DEFAULT DRIVEBASE STATION.
    // THIS IS CASE SENSITIVE
    public static final String[] AUTO_NAMES = {
        "BasicBlue",
        "BasicRed",
        "TwoShootBlue1",
        "TwoShootRed1",
        "TwoShootBlue2",
        "TwoShootRed2",
        "ThreeShootBlue1",
        "ThreeShootRed1",
        "DelayedShootAndMoveBlue1"
    };
  }

  public static final class Drivebase {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static class OperatorConstants {

    // Joystick Deadband
    public static final double LEFT_X_DEADBAND = 0.1;
    public static final double LEFT_Y_DEADBAND = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT = 6;

  }

  public static class RobotDemensions {
    public static final double ArmLength = 21.5;
    public static final double ArmHeightLimit = 0.46;
    public static final double ArmDipLimit = 0.789;
  }

  public static class FieldDemensions {
    public static final double SpeakerHeight = 82;
  }

  public static class CollectorConstants {
    public static final int canId = 9;
    public static final int currentLimit = 30;
    public static final double runSpeed = 0.8d;
  }

  public static class ShooterConstants {
    public static final int upperCanId = 13;
    public static final int lowerCanId = 6;

    public static final int upperCurrentLimit = 40;
    public static final int lowerCurrentLimit = 40;

    public static final double flywheelRunSpeed = 1d;
  }

  public static class ClimberConstants {
    public static final int masterCanId = 8; // Needs to be set
    public static final int slaveCanId = 11; // Needs to be set
    public static final double runSpeed = 0.3d;
  }
}
