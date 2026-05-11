package business.factory;

import business.models.Machine;
import business.models.Worker;
import java.util.ArrayList;
import java.util.List;

public class Factory {
    private String name;
    private List<Machine> machines;
    private List<Worker> workers;

    public Factory(String name) {
        this.name = name;
        this.machines = new ArrayList<>();
        this.workers = new ArrayList<>();
    }

    public void addMachine(Machine m) {
        machines.add(m);
    }

    public void addWorker(Worker w) {
        workers.add(w);
    }

    public void runShift() {
        System.out.println("=== " + name + " SHIFT STARTED ===");
        
        for (Worker w : workers) {
            w.performWork();
        }
        
        for (Machine m : machines) {
            m.produce();
        }
        
        System.out.println("=== SHIFT COMPLETED ===");
    }

    // Getters for the ui
    public List<Machine> getMachines() { return machines; }
    public List<Worker> getWorkers() { return workers; }
    public String getName() { return name; }
}
