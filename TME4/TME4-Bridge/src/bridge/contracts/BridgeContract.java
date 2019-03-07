package bridge.contracts;

import bridge.services.BridgeService;

public class BridgeContract extends LimitedRoadContract implements BridgeService {

	public BridgeContract(BridgeService delegate) {
		super(delegate);
	}

	@Override
	protected BridgeService getDelegate() {
		return (BridgeService) super.getDelegate();
	}
	
	@Override
	public int getNbIn() {
		return getDelegate().getNbIn();
	}

	@Override
	public int getNbOut() {
		return getDelegate().getNbOut();
	}
	
	public void checkInvariant() {
		// TODO
		// raffinement donc
		super.checkInvariant();
		// inv: getNbCars() == getNbIn() + getNbOut()
		if (getNbCars() != (getNbIn()+getNbOut()))
			Contractor.defaultContractor().invariantError("BridgeService", "getNbCars() == getNbIn() + getNbOut()");
		// inv: getNbIn() >= 0
		if (getNbIn() < 0)
			Contractor.defaultContractor().invariantError("BridgeService","getNbIn() >= 0");
		// inv: getNbOut() >= 0
		if (getNbOut() < 0)
			Contractor.defaultContractor().invariantError("BridgeService", "getNbOut() >= 0");
	}
	

	@Override
	public void init() {
		// TODO
		getDelegate().init();
		// post inv
		checkInvariant();
		// post : 
	}

	@Override
	public void init(int lim) {
		// TODO
		// pre : lim > 0
		if (lim <= 0)
			Contractor.defaultContractor().preconditionError("BridgeService", "init", "lim > 0");
		// run
		getDelegate().init(lim);
		// post inv 
		checkInvariant();
		// post: getNbIn() == 0
		
		// post: getNbOut() == 0
	}

	@Override
	public void enterIn() {
		// TODO
		getDelegate().enterIn();
	}

	@Override
	public void leaveIn() {
		// TODO
		getDelegate().leaveIn();
	}

	@Override
	public void enterOut() {
		// TODO
		getDelegate().enterOut();
	}

	@Override
	public void leaveOut() {
		// TODO
		getDelegate().leaveOut();
	}
	
}
