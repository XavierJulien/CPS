package bridge.services;

public interface BridgeControllerService {
	public boolean canEnter();
	public boolean canLeave();
	public void control();
	public boolean canLeaveIn();
	public boolean canLeaveOut();
	public boolean canEnterIn();
	public boolean canEnterOut();
}
