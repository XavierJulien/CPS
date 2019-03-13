package lift.test;


import org.junit.Test;

import lift.contracts.LiftContract;
import lift.contracts.PreconditionError;
import lift.services.CommandsService;
import lift.services.LiftService;
import liftimpl1.Lift1;

public class LiftTest1 extends AbstractLiftTest {

	LiftService l;
	CommandsService c;
	@Override
	public void beforeTests() {
		Lift1 lift = new Lift1();
		setLift(new LiftContract(lift));
		l = getLift();
		c = getLift().getCommands();
	}
	
	@Test
	public void preInitPass() {
		//Conditions Initiales : None
		//Opération(s)
		l.init(2,5);
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preInitFail1() {
		//Conditions Initiales : None
		//Opération(s)
		l.init(5,2);
		//Oracle : None
	}
	
	@Test(expected = PreconditionError.class)
	public void preInitFail2() {
		//Conditions Initiales : None
		//Opération(s)
		l.init(-2,5);
		//Oracle : exception expected
	}
	@Test
	public void preBeginMoveUpPass() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3);
		l.closeDoor(); //door status -> CLOSING
		l.doorAck(); //door status -> CLOSED
		//Opération(s)
		l.beginMoveUp();
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preBeginMoveUpFail1() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3);
		l.closeDoor(); //door status -> CLOSING
		//Opération(s)
		l.beginMoveUp();
		//Oracle : exception expected
	}
	
	@Test(expected = PreconditionError.class)
	public void preBeginMoveUpFail2() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.closeDoor(); //door status -> CLOSING
		l.doorAck(); //door status -> CLOSED
		l.getCommands().addUpCommand(5);
		//Opération(s)
		l.beginMoveUp();
		//Oracle : exception expected
	}

		
	@Test
	public void preStepMoveUpPass() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3); 
		l.closeDoor(); // door status -> CLOSING
		l.doorAck(); // door status -> CLOSED
		l.beginMoveUp(); // liftStatus -> MOVING UP 
		//Opération(s)
		l.stepMoveUp();
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preStepMoveUpFail1() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3); 
		l.closeDoor(); // door status -> CLOSING
		l.doorAck(); // door status -> CLOSED
		//Opération(s)
		l.stepMoveUp();
		//Oracle : exception expected
	}

	@Test(expected = PreconditionError.class)
	public void preStepMoveUpFail2() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3); 
		l.closeDoor(); // door status -> CLOSING
		//Opération(s)
		l.stepMoveUp();
		//Oracle : exception expected
	}
	
	@Test
	public void preEndMoveUpPass() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3); //
		l.closeDoor(); // door status -> CLOSING
		l.doorAck(); // door status -> CLOSED
		l.beginMoveUp(); // liftStatus -> MOVING UP 
		l.stepMoveUp(); 
		//Opération(s)
		l.endMoveUp();
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preEndMoveUpFail1() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3); //
		l.closeDoor(); // door status -> CLOSING
		l.doorAck(); // door status -> CLOSED
		l.beginMoveUp(); // liftStatus -> MOVING UP 
		//Opération(s)
		l.endMoveUp();
		//Oracle : exception expected
	}
	
	
	@Test(expected = PreconditionError.class)
	public void preEndMoveUpFail2() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3); //
		l.closeDoor(); // door status -> CLOSING
		l.doorAck(); // door status -> CLOSED
		//Opération(s)
		l.endMoveUp();
		//Oracle : exception expected
	}
	
	
	@Test
	public void preBeginMoveDownPass() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3);
		l.closeDoor(); //door status -> CLOSING
		l.doorAck(); //door status -> CLOSED + liftStatus -> standby up
		l.beginMoveUp();//lift status -> Moving up
		l.stepMoveUp();//level(l) = level(l)+1
		l.endMoveUp();//lift status -> STOP UP
		l.openDoor();
		l.doorAck();
		l.selectLevel(2);//lift status -> standby down
		l.closeDoor();
		l.doorAck(); //door status -> CLOSED / lift status -> STANDBY DOWN si nb de commande down est pas 0
		//Opération(s)
		l.beginMoveDown();
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preBeginMoveDownFail1() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3);
		l.closeDoor(); //door status -> CLOSING
		l.doorAck(); //door status -> CLOSED + liftStatus -> standby up
		l.beginMoveUp();//lift status -> Moving up
		l.stepMoveUp();//level(l) = level(l)+1
		l.endMoveUp();//lift status -> STOP UP
		l.openDoor();
		l.doorAck();
		l.selectLevel(2);//lift status -> standby down
		l.closeDoor();
		//Opération(s)
		l.beginMoveDown();
		//Oracle : exception expected
	}
	
//	@Test(expected = PreconditionError.class)
//	public void preBeginMoveDownFail2() {
//		//Conditions Initiales
//		l.init(2, 5);// liftStatus -> IDLE
//		l.selectLevel(3);
//		l.closeDoor(); //door status -> CLOSING
//		l.doorAck(); //door status -> CLOSED + liftStatus -> standby up
//		l.beginMoveUp();//lift status -> Moving up
//		//Opération(s)
//		l.beginMoveDown();
//		//Oracle : exception expected
//	}
	
	
	
//	
//	@Test
//	public void preStepMoveDownPass() {
//		//Conditions Initiales
//		
//		//Opération(s)
//		//Oracle : None
//	}
//
//	@Test(expected = PreconditionError.class)
//	public void preStepMoveDownFail() {
//		//Conditions Initiales
//		//Opération(s)
//		//Oracle : None
//	}
//	@Test
//	public void preEndMoveDownPass() {
//		//Conditions Initiales
//		
//		//Opération(s)
//		//Oracle : None
//	}
//
//	@Test(expected = PreconditionError.class)
//	public void preEndMoveDownFail() {
//		//Conditions Initiales
//		//Opération(s)
//		//Oracle : None
//	}
	
	
	
	
	@Test
	public void preOpenDoorPass() {
		//Conditions Initiales
		l.init(2, 5);
		l.closeDoor();
		l.doorAck();
		//Opération(s)
		l.openDoor();
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preOpenDoorFail1() {
		//Conditions Initiales
		l.init(2, 5);
		l.closeDoor();
		//Opération(s)
		l.openDoor();
		//Oracle : exception expected
	}
	
	@Test(expected = PreconditionError.class)
	public void preOpenDoorFail2() {
		//Conditions Initiales
		l.init(2, 5);
		l.closeDoor();
		l.doorAck();
		//Opération(s)
		l.openDoor();
		//Oracle : exception expected
	}
	
//	public void prePass() {
//	//Conditions Initiales
//	
//	//Opération(s)
//	//Oracle : None
//}
//
//@Test(expected = PreconditionError.class)
//public void preFail() {
//	//Conditions Initiales
//	//Opération(s)
//	//Oracle : None
//}
	
//	public void prePass() {
//	//Conditions Initiales
//	
//	//Opération(s)
//	//Oracle : None
//}
//
//@Test(expected = PreconditionError.class)
//public void preFail() {
//	//Conditions Initiales
//	//Opération(s)
//	//Oracle : None
//}
	
//	public void prePass() {
//	//Conditions Initiales
//	
//	//Opération(s)
//	//Oracle : None
//}
//
//@Test(expected = PreconditionError.class)
//public void preFail() {
//	//Conditions Initiales
//	//Opération(s)
//	//Oracle : None
//}
	
//	public void prePass() {
//	//Conditions Initiales
//	
//	//Opération(s)
//	//Oracle : None
//}
//
//@Test(expected = PreconditionError.class)
//public void preFail() {
//	//Conditions Initiales
//	//Opération(s)
//	//Oracle : None
//}
	
//	public void prePass() {
//	//Conditions Initiales
//	
//	//Opération(s)
//	//Oracle : None
//}
//
//@Test(expected = PreconditionError.class)
//public void preFail() {
//	//Conditions Initiales
//	//Opération(s)
//	//Oracle : None
//}
}
