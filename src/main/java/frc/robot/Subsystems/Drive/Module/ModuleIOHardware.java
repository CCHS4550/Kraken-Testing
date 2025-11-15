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

    private final Debouncer driveDebouncer = new Debouncer(.5);
    private final Debouncer turnDebouncer = new Debouncer (.5);


    public ModuleIOHardware (int moduleID){
        
    }
}
