// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.Auton;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Shooter.Dump;
import frc.robot.commands.Shooter.Shoot;
import frc.robot.commands.Shooter.Spit;
import frc.robot.commands.arm.AmpAngleCommand;
import frc.robot.commands.arm.ManualDriveArm;
import frc.robot.commands.arm.TargetSpeaker;
import frc.robot.commands.collector.Collect;
import frc.robot.commands.collector.Feed;
import frc.robot.commands.debug.Debug;
import frc.robot.commands.leds.LedPassive;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.commands.swervedrive.drivebase.GoForward;
import frc.robot.commands.swervedrive.drivebase.LimelightAmpAlign;
import frc.robot.commands.swervedrive.drivebase.LimelightMoveAlign;
import frc.robot.commands.swervedrive.drivebase.LimelightShootAlign;
import frc.robot.commands.swervedrive.drivebase.LimelightTrapAlign;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.subsystems.Arm.Collector;
import frc.robot.subsystems.Arm.Shooter;
import frc.robot.subsystems.LedManager.LedManagerSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;



import com.pathplanner.lib.auto.NamedCommands;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve/neo"));

  private final LedManagerSubsystem ledManager = new LedManagerSubsystem();
  public final Collector collector = new Collector(ledManager);
  private final Shooter shooter = new Shooter();
  private final Arm arm = new Arm();
  // CommandJoystick rotationController = new CommandJoystick(1);
  // Replace with CommandPS4Controller or CommandJoystick if needed

  // CommandJoystick driverController   = new CommandJoystick(3);//(OperatorConstants.DRIVER_CONTROLLER_PORT);
  XboxController driverXbox = new XboxController(0);
  XboxController manipulatorXbox = new XboxController(1);
  CommandXboxController driverXboxCommanded = new CommandXboxController(0);
  CommandXboxController mainpulatorXboxCommanded = new CommandXboxController(1);
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    NamedCommands.registerCommand("AutoShootAlign",new LimelightShootAlign(drivebase));
    // Configure the trigger bindings
    registerAutos();
    configureBindings();

    AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(drivebase,
                                                                   () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
                                                                                                OperatorConstants.LEFT_Y_DEADBAND),
                                                                   () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
                                                                                                OperatorConstants.LEFT_X_DEADBAND),
                                                                   () -> MathUtil.applyDeadband(driverXbox.getRightX(),
                                                                                                OperatorConstants.RIGHT_X_DEADBAND),
                                                                   driverXbox::getYButtonPressed,
                                                                   driverXbox::getAButtonPressed,
                                                                   driverXbox::getXButtonPressed,
                                                                   driverXbox::getBButtonPressed);

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the desired angle NOT angular rotation
    Command driveFieldOrientedDirectAngle = drivebase.driveCommand(
        () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> driverXbox.getRightX(),
        () -> driverXbox.getRightY());

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the angular velocity of the robot
    Command driveFieldOrientedAnglularVelocity = drivebase.driveCommand(
        () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> -driverXbox.getRightX()); // driverXbox.getRawAxis(2)

    Command driveFieldOrientedDirectAngleSim = drivebase.simDriveCommand(
        () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> driverXbox.getRawAxis(2));

      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);
      //drivebase.setDefaultCommand(driveFieldOrientedDirectAngle);

    //drivebase.setDefaultCommand(
    //    !RobotBase.isSimulation() ? driveFieldOrientedAnglularVelocity : driveFieldOrientedDirectAngleSim);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  { var ampAlignAndShootCommand = new SequentialCommandGroup 
      (new  ParallelCommandGroup (new AmpAngleCommand (arm, 0.5), new LimelightAmpAlign(drivebase))
     , new GoForward(drivebase), new Dump(shooter, collector, drivebase));
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    arm.setDefaultCommand(new ManualDriveArm(arm, () -> MathUtil.applyDeadband(manipulatorXbox.getRightY(), 0.7)));
    new JoystickButton(driverXbox, XboxController.Button.kBack.value).onTrue((new InstantCommand(drivebase::zeroGyro)));
    // new JoystickButton(driverXbox, 3).onTrue(new InstantCommand(drivebase::addFakeVisionReading));
    // new JoystickButton(driverXbox,
    //                    2).whileTrue(
    //     Commands.deferredProxy(() -> drivebase.driveToPose(
    //                                new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
    //                           ));
//    new JoystickButton(driverXbox, 3).whileTrue(new RepeatCommand(new InstantCommand(drivebase::lock, drivebase)));
    
    // TODO: Unremove these...
    //new JoystickButton(driverXbox, XboxController.Button.kX.value).whileTrue(new LimelightShootAlign(drivebase));
    //new JoystickButton(driverXbox, XboxController.Button.kY.value).whileTrue(new LimelightMoveAlign(drivebase));
    //new JoystickButton(driverXbox, XboxController.Button.kY.value).whileTrue(new LimelightAmpAlign(drivebase)); // Test bind
    //new JoystickButton(driverXbox, XboxController.Button.kA.value).whileTrue(ampAlignAndShootCommand);
    //new JoystickButton(driverXbox, XboxController.Button.kX.value).whileTrue( new LimelightAmpAlign(drivebase));
    
    new JoystickButton(driverXbox, XboxController.Button.kLeftBumper.value).whileTrue(new InstantCommand(() -> {System.out.println(arm.getAngle());}));
    new JoystickButton(driverXbox, XboxController.Button.kStart.value).whileTrue(new LedPassive(ledManager));
    new JoystickButton(driverXbox, XboxController.Button.kB.value).whileTrue(ampAlignAndShootCommand);
    new JoystickButton(driverXbox, XboxController.Button.kY.value).whileTrue(new LimelightTrapAlign(drivebase));


    new JoystickButton(manipulatorXbox, XboxController.Button.kY.value).whileTrue(new Shoot(shooter));
    new JoystickButton(manipulatorXbox, XboxController.Button.kLeftBumper.value).whileTrue(new Spit(shooter, collector, drivebase)); // Test bind
    new JoystickButton(manipulatorXbox, XboxController.Button.kX.value).whileTrue(new AmpAngleCommand(arm, Constants.RobotDemensions.ArmHeightLimit));
    new JoystickButton(manipulatorXbox, XboxController.Button.kRightBumper.value).whileTrue(new Feed(collector));
    new JoystickButton(manipulatorXbox, XboxController.Button.kA.value).whileTrue(new Debug(collector, arm));
 
    

    mainpulatorXboxCommanded.rightTrigger(0.5).whileTrue( new ParallelCommandGroup ( new LimelightShootAlign(drivebase), new Shoot(shooter), new TargetSpeaker(arm)));
    mainpulatorXboxCommanded.leftTrigger(0.5).whileTrue(new Collect(collector, arm));
    // new JoystickButton(driverXbox, XboxController.Button.kX.value).whileTrue(new TargetSpeaker(arm)); // TODO: Double bound from merge
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    var auto = SmartDashboard.getString("Auto Selector", null);
    System.out.println("Selected Autonomous: " + ((auto == null) ? "[null, cannot find one]" : auto));

    // Verify and autonomous command was able to be found from the Dashboard
    if (auto == null)
      return drivebase.getAutonomousCommand(Auton.DEFAULT_AUTO_NAME, true);

    // Verify the command actually exists. This will return the command if its in the list. (MUST match)
    for (var i = 0; i < Auton.AUTO_NAMES.length; i++)
      if (Auton.AUTO_NAMES[i].equals(auto))
        return drivebase.getAutonomousCommand(auto, true);

    // Dont try to run a autonomous that isn't verifiably in the list
    System.out.println("This autonomous is not in the list!!!");
    System.out.println(" . Make sure you select one from the dropdown and DONT CHANGE IT.");
    System.out.println("    > It MUST be in the list to be run.");
    return drivebase.getAutonomousCommand(Auton.DEFAULT_AUTO_NAME, true);
  }

  public void setDriveMode()
  {
    //drivebase.setDefaultCommand();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }

  private void registerAutos() {
   SmartDashboard.putStringArray("Auto List", Auton.AUTO_NAMES);
  }
}
