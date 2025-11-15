package main.java.frc.robot.Subsystems.Drive.Module;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Rotation2d;

public interface ModuleIO {

    @AutoLog
    public static class ModuleIOInputs {

        //drive motor tracking variables
        public double drivePositionRadians = 0;
        public double driveVelocityRadiansPerSec = 0;
        public double driveAppliedVoltage = 0;
        public double driveCurrentAmps = 0;

        public boolean driveConnected = false;

        //turn motor tracking variables
        public Rotation2d turnPosition = new Rotation2d();
        public double turnVelocityRadiansPerSec = 0;
        public double turnAppliedVoltage = 0;
        public double turnCurrentAmps = 0;
        public double turnEncoderVoltage = 0;

        //odometry
        public double[] odometryTimestamps = new double []{};
        public double [] odometryDrivePositionsRad = new double [] {};
        public Rotation2d[] odometryTurnPositions = new Rotation2d[]{};


    }
    //essential to every IO. you implementation of this method helps to update the variables above
    public default void updateInputs(ModuleIOInputs inputs){}

    //you give it a set speed for the chassis to drive. good for testing
    public default void setDriveOpenLoop (double power){}    

    //give the drive motor a set velocity to go at. this is is the main way we control the robot
    public default void setDriveVelocity (double velocityRadiansPerSec) {}

    //you give it a set speed for the chassis to turn. good for testing
    public default void setTurnOpenLoop (double power) {}

    //makes turn motor to go to a certain angle.
    public default void setTurnPosition (Rotation2d rotation){}
} 
