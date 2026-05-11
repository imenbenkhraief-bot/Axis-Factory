package business.models.machines;
import business.models.Machine;

public class AssemblyRobot extends Machine {
    public AssemblyRobot(String id, String name) {
        super(id, name);
    }

    @Override
    public void produce() {
        this.setStatus("RUNNING");
        this.parts += 8;  
        System.out.println("Axis Assembly Arm " + getName() + " joined 8 components.");
    }
}
