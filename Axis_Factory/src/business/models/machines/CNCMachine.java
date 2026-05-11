package business.models.machines;
import business.models.Machine;

public class CNCMachine extends Machine {
    public CNCMachine(String id, String name) {
        super(id, name);
    }

    @Override
    public void produce() { 
        this.setStatus("RUNNING");
        this.parts += 15;     
        System.out.println("Axis CNC " + getName() + " milled 15 parts.");
    }
}