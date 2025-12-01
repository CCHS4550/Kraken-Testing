package frc.robot.Subsystems.Arm;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Rotation2d;
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

    // I'm pretty sure this is a fancy PID Controller. Research more into this.
    MotionMagicVoltage positionVoltage = new MotionMagicVoltage(0).withSlot(0);


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

    @Override
    // I'm not sure whether referencing the "StatusSignal" variable or doing like "arm.getMotorVotage()" is the best way
    public void updateInputs(ArmIOInputs inputs){
        inputs.armConnected = arm.isConnected();

        // The constant at the end of this line of code represents the coefficient to convert from the rotor position to radians
        inputs.armPositionRad = Rotation2d.fromRadians(armPosition.getValueAsDouble() * 0.6748348);

        inputs.armAppliedVolts = armVoltage.getValueAsDouble();

        inputs.armTemperature = armTemperature.getValueAsDouble(); 

        inputs.armVelocityRadPerSec = armAngularVelocity.getValueAsDouble();

        inputs.armCurrentAmps = armSupplyCurrent.getValueAsDouble();
    }

    @Override
    public void setArmOpenLoop (double voltage){
        VoltageOut voltageControl = new VoltageOut(voltage);
        arm.setControl(voltageControl.withOutput(voltage));

    }

    @Override
    public void setArmPos (Rotation2d angle){
        arm.setControl(positionVoltage.withPosition(angle.getRadians() / 0.2345982345923485)); // coefficient to convert
    }

    @Override

    public void refreshData(){
        BaseStatusSignal.refreshAll(armPosition, armVoltage, armAngularAcceleration, armAngularVelocity, armSupplyCurrent, armStatorCurrent, armTemperature);
    }



}