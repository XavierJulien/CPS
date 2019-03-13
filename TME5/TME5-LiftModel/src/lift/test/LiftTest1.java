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
		//Oracle : None
	}
	@Test
	public void preBeginMoveUpPass() {
		//Conditions Initiales
		l.init(2, 5);//-> IDLE
		l.selectLevel(3); // -> STANDBY UP
		l.closeDoor();
		l.doorAck();
		//Opération(s)
		l.beginMoveUp();
		//Oracle : None
	}

//	@Test(expected = PreconditionError.class)
//	public void preBeginMoveUpFail() {
//		//Conditions Initiales
//		//Opération(s)
//		//Oracle : None
//	}
//	
//	@Test
//	public void prePass() {
//		//Conditions Initiales
//		
//		//Opération(s)
//		//Oracle : None
//	}
//
//	@Test(expected = PreconditionError.class)
//	public void preFail() {
//		//Conditions Initiales
//		//Opération(s)
//		//Oracle : None
//	}
//	@Test
//	public void prePass() {
//		//Conditions Initiales
//		
//		//Opération(s)
//		//Oracle : None
//	}
//
//	@Test(expected = PreconditionError.class)
//	public void preFail() {
//		//Conditions Initiales
//		//Opération(s)
//		//Oracle : None
//	}
//	@Test
//	public void prePass() {
//		//Conditions Initiales
//		
//		//Opération(s)
//		//Oracle : None
//	}
//
//	@Test(expected = PreconditionError.class)
//	public void preFail() {
//		//Conditions Initiales
//		//Opération(s)
//		//Oracle : None
//	}
//	@Test
//	public void prePass() {
//		//Conditions Initiales
//		
//		//Opération(s)
//		//Oracle : None
//	}
//
//	@Test(expected = PreconditionError.class)
//	public void preFail() {
//		//Conditions Initiales
//		//Opération(s)
//		//Oracle : None
//	}
//	@Test
//	public void prePass() {
//		//Conditions Initiales
//		
//		//Opération(s)
//		//Oracle : None
//	}
//
//	@Test(expected = PreconditionError.class)
//	public void preFail() {
//		//Conditions Initiales
//		//Opération(s)
//		//Oracle : None
//	}
//	@Test
//	public void prePass() {
//		//Conditions Initiales
//		
//		//Opération(s)
//		//Oracle : None
//	}
//
//	@Test(expected = PreconditionError.class)
//	public void preFail() {
//		//Conditions Initiales
//		//Opération(s)
//		//Oracle : None
//	}
}
