package frc.robot.Subsystems.Drive.Module;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.DeviceIdentifier;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import frc.robot.Constants;
import java.util.Queue;

public class ModuleIOHardware implements ModuleIO {
  private final Rotation2d rotationOffset;

  private CANcoder absoluteEncoder;

  private final TalonFX driveMotor;
  private final TalonFX turnMotor;

  private final Queue<Double> timeQueue;
  private final Queue<Double> drivePosQueue;
  private final Queue<Double> turnPosQueue;

  private Rotation2d absoluteEncoderOffset;
  private final int driveMotorID;
  private final int turnMotorID;

  private final boolean driveMotorInverted;
  private final boolean turnMotorInverted;

  private final Debouncer driveDebouncer = new Debouncer(.5);
  private final Debouncer turnDebouncer = new Debouncer(.5);

  public ModuleIOHardware(int moduleID) {
    absoluteEncoderOffset =
        switch (moduleID) {
          case 0 -> Constants.DriveConstants.frontLeftOffset;
          case 1 -> Constants.DriveConstants.frontRightOffset;
          case 2 -> Constants.DriveConstants.backLeftOffset;
          case 3 -> Constants.DriveConstants.backRightOffset;
          default -> new Rotation2d();
        };

    driveMotorID =
        switch (moduleID) {
          case 0 -> Constants.DriveConstants.frontLeftDriveCanId;
          case 1 -> Constants.DriveConstants.frontRightDriveCanId;
          case 2 -> Constants.DriveConstants.backLeftDriveCanId;
          case 3 -> Constants.DriveConstants.backRightDriveCanId;
          default -> -999999;
        };

    turnMotorID =
        switch (moduleID) {
          case 0 -> Constants.DriveConstants.frontLeftTurnCanId;
          case 1 -> Constants.DriveConstants.frontRightTurnCanId;
          case 2 -> Constants.DriveConstants.backLeftTurnCanId;
          case 3 -> Constants.DriveConstants.backRightTurnCanId;
          default -> -999999;
        };

    absoluteEncoder =
        new CANcoder(
            switch (moduleID) {
              case 0 -> Constants.DriveConstants.frontLeftTurnEncoder;
              case 1 -> Constants.DriveConstants.frontRightTurnEncoder;
              case 2 -> Constants.DriveConstants.backLeftTurnEncoder;
              case 3 -> Constants.DriveConstants.backRightTurnEncoder;
              default -> -999999;
            });

    driveMotorInverted = 
        switch (moduleID)
        {
          case 0 -> Constants.DriveConstants.frontLeftDriveInverted;
          case 1 -> Constants.DriveConstants.frontRightDriveInverted;
          case 2 -> Constants.DriveConstants.backLeftDriveInverted;
          case 3 -> Constants.DriveConstants.backRightDriveInverted;
          default -> false;
        };

    turnMotorInverted = 
      switch (moduleID)
      {
        case 0 -> Constants.DriveConstants.frontLeftTurnInverted;
        case 1 -> Constants.DriveConstants.frontRightTurnInverted;
        case 2 -> Constants.DriveConstants.backLeftTurnInverted;
        case 3 -> Constants.DriveConstants.backRightTurnInverted;
        default -> false;
      };

    absoluteEncoder.clearStickyFault_BadMagnet();

    // can't be bothered to figure out if reverse if "InvertedValue.CounterClockwise_Positive"
    // driveConfiguration.MotorOutput.Inverted =
    //         switch(moduleID){
    //             case 0 -> (InvertedValue) Constants.DriveConstants.frontLeftDriveInverted;
    //             case 1 -> Constants.DriveConstants.frontRightDriveInverted;

    //         };

    // blah, blah, blah: other stuff I'll do later

    

    driveMotor = configureTalonFX(driveMotorID, 80, driveMotorInverted);
    turnMotor = configureTalonFX(turnMotorID, 80, turnMotorInverted);
  }

  @Override
  public void setDriveOpenLoop(double power) {
    // there's probably a better way to do this...
    VoltageOut voltageRequest = new VoltageOut(power);
    driveMotor.setControl(voltageRequest.withOutput(power));
  }

  @Override
  public void setTurnOpenLoop(double power) {
    // there's probably a better way to do this...
    VoltageOut voltageRequest = new VoltageOut(power);
    turnMotor.setControl(voltageRequest.withOutput(power));
  }

  //WIP - should finish <----------------------
  static TalonFX configureTalonFX(int deviceID, int currentLimit, boolean inverted)
  {
    TalonFX motor = new TalonFX(deviceID);

    TalonFXConfiguration configuration = new TalonFXConfiguration();
    configuration.CurrentLimits.StatorCurrentLimitEnable = true;
    configuration.CurrentLimits.SupplyCurrentLimitEnable = true;
    configuration.CurrentLimits.StatorCurrentLimit = currentLimit;
    configuration.CurrentLimits.SupplyCurrentLimit = currentLimit;

    //for this one i have no fucking idea and im tired now
    configuration.MotorOutput.Inverted = (inverted) ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;

    return motor;
  }
}
