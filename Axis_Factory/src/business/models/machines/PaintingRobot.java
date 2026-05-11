package business.models.machines;
import business.models.Machine;

public class PaintingRobot extends Machine {
    public PaintingRobot(String id, String name) {
        super(id, name);
    }

    @Override
    public void produce() {
        this.setStatus("RUNNING");
        this.parts += 5; 
        System.out.println("Axis Coating Unit " + getName() + " painted 5 parts.");
    }
}