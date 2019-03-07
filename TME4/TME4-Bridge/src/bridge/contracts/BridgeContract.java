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
		// post: getNbIn() == 0
		if(getNbIn()!=0) {
			Contractor.defaultContractor().postconditionError("BridgeService", "init", "getNbIn() == 0");
		}
		// post: getNbOut() == 0
		if(getNbOut()!=0) {
			Contractor.defaultContractor().postconditionError("BridgeService", "init", "getNbOut() == 0");
		}
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
		if(getNbIn()!=0) {
			Contractor.defaultContractor().postconditionError("BridgeService", "init", "getNbIn() == 0");
		}
		// post: getNbOut() == 0
		if(getNbOut()!=0) {
			Contractor.defaultContractor().postconditionError("BridgeService", "init", "getNbOut() == 0");
		}
	}

	@Override
	public void enterIn() {
		// pre
		if(isFull()){
			Contractor.defaultContractor().preconditionError("BridgeService", "enterIn", "!isFull()");
		}
		// pre-invariants
		checkInvariant();
		// capture
		int nbIn_at_pre = getNbIn();
		int nbOut_at_pre = getNbOut();
		// appel
		getDelegate().enterIn();
		// post-invariants
		checkInvariant();
		// post-conditions
		if(getNbIn() != nbIn_at_pre+1) {
			Contractor.defaultContractor().postconditionError("BridgeService", "enterIn", "getNbIn() != getNbIn()@pre + 1");
		}
		if(getNbOut() != nbOut_at_pre) {
			Contractor.defaultContractor().postconditionError("BridgeService", "enterIn", "getNbOut() != getNbOut()@pre");
		}
	}

	@Override
	public void leaveIn() {
		// pre
		if(getNbIn() == 0){
			Contractor.defaultContractor().preconditionError("BridgeContract", "leaveIn", "getNbIn > 0");
		}
		// pre-invariants
		checkInvariant();
		// capture
		int nbIn_at_pre = getNbIn();
		int nbOut_at_pre = getNbOut();
		// appel
		getDelegate().leaveIn();
		// post-invariants
		checkInvariant();
		// post-conditions
		if(getNbIn() != nbIn_at_pre-1) {
			Contractor.defaultContractor().postconditionError("BridgeService", "leaveIn", "getNbIn() != getNbIn()@pre - 1");
		}
		if(getNbOut() != nbOut_at_pre) {
			Contractor.defaultContractor().postconditionError("BridgeService", "leaveIn", "getNbOut() != getNbOut()@pre");
		}
	}

	@Override
	public void enterOut() {
		// pre
		if(isFull()){
			Contractor.defaultContractor().preconditionError("BridgeContract", "enterOut", "!isFull()");
		}
		// pre-invariants
		checkInvariant();
		// capture
		int nbIn_at_pre = getNbIn();
		int nbOut_at_pre = getNbOut();
		// appel
		getDelegate().enterOut();
		// post-invariants
		checkInvariant();
		// post-conditions
		if(getNbIn() != nbIn_at_pre) {
			Contractor.defaultContractor().postconditionError("BridgeService", "enterOut", "getNbIn() != getNbIn()@pre");
		}
		if(getNbOut() != nbOut_at_pre+1) {
			Contractor.defaultContractor().postconditionError("BridgeService", "enterOut", "getNbOut() != getNbOut()@pre + 1");
		}
	}

	@Override
	public void leaveOut() {
		// pre
		if(getNbOut() == 0){
			Contractor.defaultContractor().preconditionError("BridgeService", "leaveOut", "getNbOut > 0");
		}
		// pre-invariants
		checkInvariant();
		// capture
		int nbIn_at_pre = getNbIn();
		int nbOut_at_pre = getNbOut();
		// appel
		getDelegate().leaveOut();
		// post-invariants
		checkInvariant();
		// post-conditions
		if(getNbIn() != nbIn_at_pre) {
			Contractor.defaultContractor().postconditionError("BridgeService", "leaveOut", "getNbIn() != getNbIn()@pre");
		}
		if(getNbOut() != nbOut_at_pre-1) {
			Contractor.defaultContractor().postconditionError("BridgeService", "leaveOut", "getNbOut() != getNbOut()@pre - 1");
		}
	}

	public void enter(){
		// pre
		if (isFull()) {
			Contractor.defaultContractor().preconditionError("BridgeService", "enter", "!isFull()");
		}
		// pre-invariants
		checkInvariant();
		// appel
		if(getNbIn()>getNbOut()) {
			enterOut();
		}else {
			enterIn();
		}
	}

	public void leave(){
		// pre
		if (getNbCars() <= 0) {
			Contractor.defaultContractor().preconditionError("BridgeService", "leave", "getNbCars() > 0");
		}
		// pre-invariants
		checkInvariant();
		// appel
		if(getNbIn()>getNbOut()) {
			leaveIn();
		}else {
			leaveOut();
		}
		// post-invariants
		checkInvariant();
	}

}
