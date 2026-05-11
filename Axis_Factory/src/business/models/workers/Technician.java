package business.models.workers;
import business.models.Worker;

public class Technician extends Worker {
    private String specialization;

    public Technician(String id, String name, String specialization) {
        super(id, name, "TECHNICIAN");
        this.specialization = specialization;
    }

    @Override
    public void performWork() {
        System.out.println("Axis Technician " + getName() + " is performing " + specialization + " maintenance.");
    }

    @Override
    public String getAssignment() {
        return this.specialization;
    }
}