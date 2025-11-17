package main.java.frc.robot.Subsystems.Drive.Module;

import java.util.Queue;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.geometry.Rotation2d;

public class ModuleIOHardware implements ModuleIO{
    private final Rotation2d rotationOffset;

    private CANcoder absoluteEncoder;
    
    private final TalonFX driveMotor;
    private final TalonFX turnMotor;

    private final Queue<Double> timeQueue;
    private final Queue<Double> drivePosQueue;
    private final Queue<Double> turnPosQueue;

    private double absoluteEncoderOffset;
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
                default -> -999999
            };
        
        turnMotorID =
            switch (moduleID) {
                case 0 -> Constants.DriveConstants.frontLeftTurnCanId;
                case 1 -> Constants.DriveConstants.frontRightTurnCanId;
                case 2 -> Constants.DriveConstants.backLeftTurnCanId;
                case 3 -> Constants.DriveConstants.backRightTurnCanId;
                default -> -999999;
            };

        

        
        

    }
}
