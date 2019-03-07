package bridge.contracts;

import bridge.decorators.LimitedRoadDecorator;
import bridge.services.LimitedRoadService;

public class LimitedRoadContract extends LimitedRoadDecorator {

	public LimitedRoadContract(LimitedRoadService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		// remarque : include et non refine donc on n'hérite
		// pas des invariants de RoadSectionService, il faut refaire des tests.
<<<<<<< HEAD
	
		// inv: getNbCars() >= 0
		if(!(getNbCars() >= 0))
			Contractor.defaultContractor().invariantError("RoadSectionService","The number of cars should be positive");
		
		if (isFull() != (getNbCars() == getLimit()))
			Contractor.defaultContractor().invariantError("LimiteRoadService", "isFull() == (getNbCars() == getLimit())");
		
		if (getNbCars() > getLimit())
			Contractor.defaultContractor().invariantError("LimitedRoadService", "getNbCars() <= getLimit()");
		/* A COMPLETER */
	}
	
	public void init(int lim) {
		// pre
		if (lim <= 0)
			Contractor.defaultContractor().preconditionError("LimitedRoadService", "init", "lim > 0");
		// run
		super.init(lim);
		// post inv
		checkInvariant();
		// post
		if (getLimit() != lim){
			Contractor.defaultContractor().postconditionError("LimitedRoadServce", "init", "getLimit() == lim");
		}
	}
	
	public void enter() {
		//pre: !isFull()
		if (isFull()) 
			Contractor.defaultContractor().preconditionError("LimitedRoadService", "enter", "!isFull()");
		// pre inv
		checkInvariant();
		// run 
		super.enter();
		//post inv
		check
		
	}
=======
				
		/* A COMPLETER */
	}
	
>>>>>>> fc14157098d62069b3d239fe6d8f50a96a97fbbc
	/* A COMPLETER */
	
	
}
