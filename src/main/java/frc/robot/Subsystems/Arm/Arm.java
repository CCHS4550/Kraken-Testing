package frc.robot.Subsystems.Arm;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase{

    private final ArmIO io;
    private final ArmIOInputsAutoLogged autoLogged = new ArmIOInputsAutoLogged(); // should work on most stuff, but my computer is special


    // we control the subsystem through states. this is an example for these are random
    // what we WANT
    public enum WantedState {
        ZERO, 
        NINETY,
        MANUAL,
        CLOSE_LOOP,
        IDLE
    }

    // what is CURRENTLY HAPPENING
    public enum SystemState {
        ACTIVE_VOLTAGE, // idk man
        IDLE
    }

    private WantedState wantedState = WantedState.IDLE;
    private SystemState systemState = SystemState.IDLE; 

    public Arm (ArmIO io){
        this.io = io;

        /* 2910 uses this */
        // SubsystemDataProcessor.createAndStartSubsystemDataProcessor(
        //         () -> {
        //             synchronized (extensionInputs) {
        //                 synchronized (shoulderInputs) {
        //                     synchronized (wristInputs) {
        //                         extensionIO.updateInputs(extensionInputs);
        //                         shoulderIO.updateInputs(shoulderInputs);
        //                         wristIO.updateInputs(wristInputs);
        //                     }
        //                 }
        //             }
        //         },
        //         extensionIO,
        //         shoulderIO,
        //         wristIO);
    }


    @Override
    public void periodic (){
        synchronized (autoLogged){
            io.updateInputs(autoLogged);

            systemState = handleStateTransitions();

        }
        
    }

    public SystemState handleStateTransitions() {
        // basically here we continually check the wantedState and apply it to the systemstate
        return switch (wantedState){
            case ZERO -> SystemState.ACTIVE_VOLTAGE;
            case NINETY -> SystemState.ACTIVE_VOLTAGE;
            case MANUAL ->SystemState.ACTIVE_VOLTAGE;
            case CLOSE_LOOP -> SystemState.ACTIVE_VOLTAGE;
            case IDLE -> SystemState.IDLE;
            default -> SystemState.IDLE;

        };
     
    }

    public void applyStates (){
        // actually applying the stuff
        // i actually don't wanna code this
    }

    
    

    
}
