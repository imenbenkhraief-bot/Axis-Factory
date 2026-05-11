package business.models.workers;
import business.models.Worker;

public class Operator extends Worker {
    private String assignedLine;

    public Operator(String id, String name, String assignedLine) {
        super(id, name, "OPERATOR");
        this.assignedLine = assignedLine;
    }

    @Override
    public void performWork() {
        System.out.println("Axis Operator " + getName() + " is monitoring production on " + assignedLine);
    }

    public String getAssignedLine() { return assignedLine; }

    @Override
    public String getAssignment() {
        return this.assignedLine;
    }
}