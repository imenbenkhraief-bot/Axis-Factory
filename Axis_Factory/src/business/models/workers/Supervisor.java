package business.models.workers;
import business.models.Worker;

public class Supervisor extends Worker {
    private String department;

    public Supervisor(String id, String name, String department) {
        super(id, name, "SUPERVISOR");
        this.department = department;
    }

    public String getDepartment() { 
        return department; 
    }

    @Override
    public void performWork() {
        System.out.println("Supervisor " + getName() + " overseeing " + department);
    }

    @Override
    public String getAssignment() {
        return this.department;
    }
}