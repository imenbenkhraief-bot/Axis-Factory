package business.models.machines;
import business.models.Machine;

public class ConveyorBelt extends Machine {
    public ConveyorBelt(String id, String name) {
        super(id, name);
    }

    @Override
    public void produce() {
        this.setStatus("RUNNING");
        this.parts += 20; 
        System.out.println("Axis Conveyor " + getName() + " moved 20 parts to shipping.");
    }
}