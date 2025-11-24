package frc.robot.Subsystems.Arm;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public class ArmIOHardware implements ArmIO {
    
    // ooh motor declaration! sexy
    private TalonFX arm;

    // these are fancy variables to hold and track the stuff we want.
    private final StatusSignal<Angle> armPosition;
    private final StatusSignal<Voltage> armVoltage;
    private final StatusSignal<Current> armSupplyCurrent;
    private final StatusSignal<Current> armStatorCurrent;
    private final StatusSignal<Temperature> armTemperature;
    private final StatusSignal<AngularVelocity> armAngularVelocity;
    private final StatusSignal<AngularAcceleration> armAngularAcceleration;

       // this can't be the best way to put in constants, but let me be mfers. i haven't slept for 20 hours
       public ArmIOHardware(int deviceID, String deviceName, double kP, double kI, double kD, double kS, double accelerationConstraint, double velocityConstraint, double brakeMode, InvertedValue reverse ) {
        arm  = new TalonFX (deviceID, deviceName); // basic config with ID and name (name is optional tho)

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 30.0;
        config.CurrentLimits.StatorCurrentLimit = 80.0;

        config.Slot0.kP = kP;

        config.Slot0.kI = kI;
        config.Slot0.kD = kD;

        config.Slot0.kS = kS;

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotionMagic.MotionMagicAcceleration = accelerationConstraint;
        config.MotionMagic.MotionMagicCruiseVelocity = velocityConstraint;
        config.MotorOutput.Inverted = reverse;
        arm.getConfigurator().apply(config);

        armPosition = arm.getRotorPosition();
        armVoltage = arm.getMotorVoltage();
        armSupplyCurrent = arm.getSupplyCurrent();
        armStatorCurrent = arm.getStatorCurrent();
        armTemperature = arm.getDeviceTemp();
        armAngularVelocity = arm.getRotorVelocity();
        armAngularAcceleration = arm.getAcceleration();
    }

}