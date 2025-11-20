package frc.robot.Subsystems.Drive.Module;

import java.util.Queue;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants;

public class ModuleIOHardware implements ModuleIO{
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

    private final Debouncer driveDebouncer = new Debouncer(.5);
    private final Debouncer turnDebouncer = new Debouncer (.5);


    public ModuleIOHardware (int moduleID){
        absoluteEncoderOffset =
            switch(moduleID){
                case 0 -> Constants.DriveConstants.frontLeftOffset;
                case 1 -> Constants.DriveConstants.frontRightOffset;
                case 2 -> Constants.DriveConstants.backLeftOffset;
                case 3 -> Constants.DriveConstants.backRightOffset;
                default -> new Rotation2d();
            };

        driveMotorID =
            switch(moduleID){
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
                switch(moduleID){
                    case 0 -> Constants.DriveConstants.frontLeftTurnEncoder;
                    case 1 -> Constants.DriveConstants.frontRightTurnEncoder;
                    case 2 -> Constants.DriveConstants.backLeftTurnEncoder;
                    case 3 -> Constants.DriveConstants.backRightTurnEncoder;
                    default -> -999999;
                }
            );

        absoluteEncoder.clearStickyFault_BadMagnet();
        
        TalonFXConfiguration driveConfiguration = new TalonFXConfiguration();
        driveConfiguration.CurrentLimits.StatorCurrentLimitEnable = true;
        driveConfiguration.CurrentLimits.SupplyCurrentLimitEnable = true;
        driveConfiguration.CurrentLimits.StatorCurrentLimit = 80;
        driveConfiguration.CurrentLimits.SupplyCurrentLimit = 80;
        

        //can't be bothered to figure out if reverse if "InvertedValue.CounterClockwise_Positive"
        // driveConfiguration.MotorOutput.Inverted =
        //         switch(moduleID){
        //             case 0 -> (InvertedValue) Constants.DriveConstants.frontLeftDriveInverted;
        //             case 1 -> Constants.DriveConstants.frontRightDriveInverted;

        //         };


        

        
        //blah, blah, blah: other stuff I'll do later
        
        

    }

    @Override
    public void setDriveOpenLoop(double power){
        //there's probably a better way to do this...
        VoltageOut voltageRequest = new VoltageOut (power);
        driveMotor.setControl(voltageRequest.withOutput(power));
    }

    @Override
    public void setTurnOpenLoop(double power){
        //there's probably a better way to do this...
        VoltageOut voltageRequest = new VoltageOut (power);
        turnMotor.setControl(voltageRequest.withOutput(power));
    }
}
