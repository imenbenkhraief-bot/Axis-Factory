package business.models;
import business.interfaces.IMachine;

public abstract class Machine implements IMachine {
    private String id;
    private String name;
    
    protected int parts; 
    protected String status;

    public Machine(String id, String name) {
        this.id = id;
        this.name = name;
        this.parts = 0;
        this.status = "READY";
    }

    public String getId() { return id; }
    public String getName() { return name; }
    
    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getTotalPartsProduced() {
        return this.parts;
    }
    public void setParts(int parts) {
        this.parts = parts;
    }
    
    public int getParts() { return this.parts; } 
}