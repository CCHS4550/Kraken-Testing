package frc.robot.Subsystems.Arm;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Rotation2d;

public interface ArmIO {
    
    @AutoLog
    public static class ArmIOInputs {
    
    // tracking variables for the first arm motor
    public boolean armConnected = false;
    public Rotation2d armPositionRad = new Rotation2d(0.0);
    public double armVelocityRadPerSec = 0.0;
    public double armAppliedVolts = 0.0;
    public double armCurrentAmps = 0.0;

    public double armTemperature = 0.0;


    
    }


    /** Updates the set of loggable inputs. */
  public default void updateInputs (ArmIOInputs inputs) {}

  /** Run ths (Arm motor at the specified open loop value. */
  public default void setArmOpenLoop(double voltage) {}

  /** Run ths (Arm motor to the specified angle. */
  public default void setArmPos(Rotation2d angle) {}

  /* Refreshes the "statussignal" stuff*/
  public default void refreshData () {}

}
