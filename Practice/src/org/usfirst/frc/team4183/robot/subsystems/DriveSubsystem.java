/**
 * DriveSubsystem - This is the mentor solution to the practice robot drive
 * subsystem; in here we will attempt to heavily comment **why** we do something
 * and potential alternatives based on needs.
 *
 * In this sense the DriveSubsystem presented here is very basic and serves
 * as a tutorial.
 * 
 * This file was created from the ExampleSubsystem that was included with
 * the new Command-based Robot project, thus refactored to be the DriveSubsystem
 * 
 */

// The following package and imports were part of the original definition
package org.usfirst.frc.team4183.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

// These remaining imports are added by us, defining our robot
// using the framework provided to us (WPI Lib) 
// Import mappings, controller types, and other API elements we need to tie it all together
import org.usfirst.frc.team4183.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon; // The type of motor controller we are using
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.RobotDrive; // A class that provides predefined controls for the motors

/**
 * The DriveSubsystem class definition
 * 
 * Our Robot class will be required to instantiate one of these subsystems in
 * order to be able to drive either autonomously or under user control
 */
public class DriveSubsystem extends Subsystem
{
    // Keep track of the parameters of our drive subsystem
    private double wheelTrack_m;     // Needed for autonomous driving
    
    // Create motor controllers
    // In this case our robot has CANTalon (i.e., Talon SRX motor controllers
    // are on the CAN bus)
    // The CAN ID will be assigned at construction
    private CANTalon leftMotor0;
    private CANTalon leftMotor1;
    private CANTalon rightMotor0;
    private CANTalon rightMotor1;

    // Create a RobotDrive object to bind the motors together
    private RobotDrive robotDrive;

    /**
     * Constructor
     */
    public DriveSubsystem(double aWheelTrack_m)
    {
        wheelTrack_m = aWheelTrack_m;
        
        // Instantiate the motor controllers mapped to the IDs defined for our
        // robot
        // In our case we have four (4) motors defining the drive subsystem
        leftMotor0 = new CANTalon(RobotMap.leftMotor0);
        leftMotor1 = new CANTalon(RobotMap.leftMotor1);
        rightMotor0 = new CANTalon(RobotMap.rightMotor0);
        rightMotor1 = new CANTalon(RobotMap.rightMotor1);

        // Bind the motors into a single drive system
        // The RobotDrive class provides several interfaces that support
        // both autonomous and manual control (e.g., drive based on magnitude
        // and curve,
        // or tankDrive/arcadeDrive based on "stick" inputs)
        // We will have to provide methods for accessing the robotDrive object
        // through an instance of the DriveSubsystem.
        //
        // Conveniently, the RobotDrive class accepts 2 or 4 motor solutionss
        robotDrive = new RobotDrive(leftMotor0, leftMotor1, rightMotor0, rightMotor1);

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    /**
     * The Command-based robot defines a method for initial commands to be
     * called automatically after ALL subsystems have be instantiated To cause
     * the default command to be invoked it must be "registered" using a
     * setDefaultCommand call.
     * 
     * I doth protest: In general this is considered an architectural inversion
     * that should be avoided. While the Subsystem definition is part of the WPI
     * Lib definition of wpilibj.command classes, the inclusion of our
     * robot.commands package inside the robot.subsystems package and vice-versa
     * couples the packages in an undesirable way. A BETTER APPROACH is to have
     * the robot induce a set of default commands itself, and if necessary to
     * invoke through robot.commands, then have support methods in the
     * robot.subsystems to provide for an initial state as needed. This will
     * keep the commands dependent on the subsystems and not the other way
     * around
     */
    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        // e.g., setDefaultCommand(new MySpecialCommand());

        // For now there is nothing to do here
    }
    
    /**
     * We would like our robot to provide both tank and arcade style driving to
     * the operator based on their preferences and the fact that we don't know
     * what their needs are.
     * 
     * Autonomous support is further down
     */

    /**
     * --------------------------------------------------------------------------
     * arcadeDrive - a simple user-controlled arcade drive is one defining a
     * speed and turn coefficient (-1 to +1) based on a joystick input; the
     * caller will be responsible for defining how speed and turn are related
     * (e.g., more or less turn based on speed)
     * 
     * How the joystick is mapped is up to the designer
     * 
     * Examples of having two (2) two-axis joysticks on a controller:
     *
     * |---|        |---| 
     * | ^ |        | ^ | 
     * |   |        |   | 
     * | v |        | v | 
     * |---|        |---| 
     * Speed        Turn
     *
     * |---|        |---| 
     * | ^ |        |   | 
     * |   |        |< >| 
     * | v |        |   | 
     * |---|        |---| 
     * Speed        Turn
     *
     * |---|        |---| 
     * | ^ |        |   | 
     * |< >|Turn    |   | Advantage if additional control 
     * | v |        |   | for something else needed 
     * |---|        |---| 
     * Speed        Not used
     *
     * |---|        |---| 
     * |   |        | ^ | 
     * |   |   Turn |< >| Advantage if additional control 
     * |   |        | v | for something else needed 
     * |---|        |---| 
     * Not Used    Speed   
     *
     * Other options of pushing both sticks forward/backward to move
     * forward/backward while the difference between the sticks defines a turn
     * is known as "tank drive" See the tankDrive function for more information.
     * --------------------------------------------------------------------------
     */
    public void arcadeDrive(double speed, double turn)
    {
        // The underlying definition of arcadeDrive is not clear if the
        // inputs will be limited; because the documentation is incomplete
        // we must dive into how the RobotDrive class works. In this case
        // we find that drive and turn values are coefficients from -1 to +1
        // and will be enforced by the RobotDrive class; this means there
        // is nothing more to do here.
        robotDrive.arcadeDrive(speed, turn);
    }

    /**
     * --------------------------------------------------------------------------
     * tankDrive - another simple user-controlled tank drive is one defining a
     * left and right control in which the "sticks" are typically defined as
     * follows:
     *
     * Assuming a controller with two (2) two-axis joysticks:
     *
     * |---|            |---| 
     * | ^ |            | ^ | 
     * |   | Forward    |   |
     * |   |            |   |
     * |---|            |---|
     *
     * |---|            |---|
     * |   |            |   | 
     * |   | Backward   |   | 
     * | v |            | v | 
     * |---|            |---|
     *
     * |---|            |---| 
     * | ^ |            |   | 
     * |   | Right      |   | 
     * |   |            | v | 
     * |---|            |---|
     *
     * |---|            |---| 
     * |   |            | ^ | 
     * |   | Left       |   | 
     * | v |            |   | 
     * |---|            |---|
     *
     * The obvious disadvantage is that two (2) sticks are needed and would
     * avoid using the right/left channels of most controllers with dual
     * two-axis joysticks In other words, tankDrive ignores the available
     * degrees of control on modern controller.
     *
     * However, use of this operational mode is based on user preference and
     * available controllers
     * --------------------------------------------------------------------------
     */
    public void tankDrive(double leftStick, double rightStick)
    {
        // The underlying definition of tankDrive is not clear if the
        // inputs will be limited; because the documentation is incomplete
        // we must dive into how the RobotDrive class works. In this case
        // we find that drive and turn values are coefficients from -1 to +1
        // and will be enforced by the RobotDrive class; this means there
        // is nothing more to do here.
        robotDrive.tankDrive(leftStick, rightStick);
    }
    
    /**
     * Autonomous driving is different that tele-operated driving
     * 
     * In autonomous we will want interfaces that allow control
     * of the robot position and orientation
     */
    
    /**
     * 
     * @param speedCoefficient The speed setting for the outside wheel in a turn,
     *        forward or backwards, +1 to -1.
     * @param radius_m The radius of the turn: < 0 for left and > 0 for right
     */
    public void autoDrive(double speedCoefficient, double radius_m)
    {
        // Do nothing if the wheelBase_m is zero
        if (wheelTrack_m != 0.0)
        {
           robotDrive.drive(speedCoefficient, Math.exp(-radius_m / wheelTrack_m));
        }
    }

    /**
     * configureBrakeMode - each motor controller has a brake mode to determine
     * if the motor will inductively halt when commands go to zero, or coast to
     * a halt (using the overall system friction)
     * 
     * Since the brake mode is a configuration state of the drive subsystem,
     * this function sets up all of the underlying controllers to be in the same
     * mode
     */
    public void configureBrakeMode(boolean brakeMode)
    {
        // I doth protest: Commands like this lack the concept of
        // Critical Section in which we would like all of the commands
        // to occur atomically in time. In this case we are only setting
        // a mode to be utilized later, but similar sequences would
        // be subject to preemptive shearing between the lines of code
        // that could cause undesirable electro-mechanical effects when
        // one or more of the commands do not arrive at the target
        // device at the same time.
        //
        // In this framework, even if we could lock out preemption locally,
        // we still do not have complete visibility into how the commands are
        // sent
        // to each motor controller (i.e., via the CAN bus).
        //
        // For example, we already know the CAN bus is a serial protocol
        // addressing each motor controller separately. As such, the
        // commands to four separate motor controllers will arrive
        // slightly delayed from each other. The actual delay between
        // messages is indeterminate when there are multiple masters
        // on the CAN bus and the CAN IDs are spread far apart. FURTHER,
        // we would need to look into how the commands to the CAN bus are
        // processed by the WPI Library; if the commands are sent to
        // a queue to be processed by a background thread or are mutex
        // protected at the interface to the CAN bus for multi-threaded
        // operation then the impact of preemption may be indeterminate.
        //
        // Such problems only become visible when needing extreme precision,
        // performance or stability. For our robot we are simply happy if
        // we can manually drive it and command some autonomous behaviors
        // for a few seconds.
        leftMotor0.enableBrakeMode(brakeMode);
        rightMotor0.enableBrakeMode(brakeMode);
        leftMotor1.enableBrakeMode(brakeMode);
        rightMotor1.enableBrakeMode(brakeMode);
    }
    
    /**
     * 
     */
    public void enablePositionMode()
    {
        leftMotor0.changeControlMode(TalonControlMode.Position);
        leftMotor1.changeControlMode(TalonControlMode.Follower);
        leftMotor1.set(leftMotor0.getDeviceID());
        
        rightMotor0.changeControlMode(TalonControlMode.Position);
        rightMotor1.changeControlMode(TalonControlMode.Follower);
        rightMotor1.set(rightMotor0.getDeviceID());
        
    }
    
    /**
     * 
     */    
    public void disablePositionMode()
    {
        leftMotor0.changeControlMode(TalonControlMode.PercentVbus);
        leftMotor1.changeControlMode(TalonControlMode.PercentVbus);
        
        rightMotor0.changeControlMode(TalonControlMode.PercentVbus);
        rightMotor1.changeControlMode(TalonControlMode.PercentVbus);
        
    }
   
}
