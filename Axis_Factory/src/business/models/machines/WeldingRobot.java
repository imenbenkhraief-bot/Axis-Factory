package business.models.machines;
import business.models.Machine;

public class WeldingRobot extends Machine {
    public WeldingRobot(String id, String name) {
        super(id, name);
    }

    @Override
    public void produce() {
        this.setStatus("RUNNING");
        this.parts += 12; 
        System.out.println("Axis Welding Unit " + getName() + " welded 12 parts.");
    }
}
