package business.interfaces;

public interface IMachine {
	void produce();
    String getId();
    String getName();
    String getStatus();
    void setStatus(String status);
    int getTotalPartsProduced();
}
