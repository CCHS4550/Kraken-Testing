package frc.robot.Subsystems.Drive.Module;

public class Module 
{
    private final ModuleIO io;
    private final int index;
    
    //DC alerts? idk how they work, i just saw it in the off season swerve testing

    public Module(ModuleIO io, int index)
    {
        this.io = io;
        this.index = index;
    }

    //for testing
    public void runOpenLoopTest(double power)
    {
        io.setDriveOpenLoop(power);
        io.setTurnOpenLoop(power);
    }
}
