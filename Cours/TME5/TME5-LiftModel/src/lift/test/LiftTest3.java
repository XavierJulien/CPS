package lift.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import lift.contracts.LiftContract;
import lift.contracts.PreconditionError;
import lift.impl.CommandsImpl;
import lift.services.CommandsService;
import lift.services.DoorStatus;
import lift.services.LiftService;
import lift.services.LiftStatus;
import liftimpl1.Lift1;
import liftimpl3.Lift3;

public class LiftTest3 extends AbstractLiftTest {

	LiftService l;
	CommandsService c;
	@Override
	public void beforeTests() {
		Lift3 lift = new Lift3();
		setLift(new LiftContract(lift));
		l = getLift();
		c = getLift().getCommands();
	}
	
	
	/**
	 * PRECONDITIONS
	 **/
	
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
	
	@Test(expected = PreconditionError.class)
	public void preBeginMoveDownFail2() {
		//Conditions Initiales
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3);
		l.closeDoor(); //door status -> CLOSING
		l.doorAck(); //door status -> CLOSED + liftStatus -> STANDBY UP
		l.beginMoveUp();//lift status -> MOVING UP
		l.stepMoveUp();//level(l) = level(l)+1
		l.endMoveUp();//lift status -> STOP UP
		l.openDoor();//door status -> OPENING
		l.doorAck();//door status -> OPENED
		l.closeDoor();
		l.doorAck();
		System.out.println(l.getDoorStatus());
		System.out.println(l.getLiftStatus());
		l.getCommands().addDownCommand(2);
		//Opération(s)
		l.beginMoveDown();
		//Oracle : exception expected
	}
	
	@Test
	public void preStepMoveDownPass() {
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
		l.beginMoveDown();
		//Opération(s)
		l.stepMoveDown();
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preStepMoveDownFail() {
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
		l.stepMoveDown();
		//Oracle : exception expected
	}
	
//	@Test(expected = PreconditionError.class)
//	public void preStepMoveDownFail2() {
//		//Conditions Initiales
//		l.init(2, 5);// liftStatus -> IDLE
//		l.selectLevel(3);
//		l.closeDoor(); //door status -> CLOSING
//		l.doorAck(); //door status -> CLOSED + liftStatus -> standby up
//		l.beginMoveUp();//lift status -> Moving up
//		l.stepMoveUp();//level(l) = level(l)+1
//		l.endMoveUp();//lift status -> STOP UP
//		l.openDoor();
//		l.doorAck();
//		l.selectLevel(2);//lift status -> standby down
//		l.closeDoor();
//		l.doorAck(); //door status -> CLOSED / lift status -> STANDBY DOWN si nb de commande down est pas 0
//		l.beginMoveDown();
//		l.getCommands().endDownCommand();
//		//Opération(s)
//		l.stepMoveDown();
//		//Oracle : exception expected
//	}
	
	
	@Test
	public void preEndMoveDownPass() {
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
		l.beginMoveDown();
		l.stepMoveDown();
		//Opération(s)
		l.endMoveDown();
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preEndMoveDownFail() {
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
		l.beginMoveDown();
		//Opération(s)
		l.endMoveDown();
		//Oracle : Exception expected
	}
	
//	@Test(expected = PreconditionError.class)
//	public void preEndMoveDownFail2() {
//		//Conditions Initiales
//		l.init(2, 5);// liftStatus -> IDLE
//		l.selectLevel(3);
//		l.closeDoor(); //door status -> CLOSING
//		l.doorAck(); //door status -> CLOSED + liftStatus -> standby up
//		l.beginMoveUp();//lift status -> Moving up
//		l.stepMoveUp();//level(l) = level(l)+1
//		l.endMoveUp();//lift status -> STOP UP
//		l.openDoor();
//		l.doorAck();
//		l.selectLevel(2);//lift status -> standby down
//		l.closeDoor();
//		l.doorAck(); //door status -> CLOSED / lift status -> STANDBY DOWN si nb de commande down est pas 0
//		l.beginMoveDown();
//		l.getCommands().addDownCommand(5);
//		//Opération(s)
//		l.endMoveDown();
//		//Oracle : Exception expected
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
		l.selectLevel(3);
		l.closeDoor();
		l.doorAck();
		//Opération(s)
		l.openDoor();
		//Oracle : exception expected
	}
	
	
	public void preCloseDoorPass() {
		//Conditions Initiales
		l.init(2, 5);
		//Opération(s)
		l.closeDoor();
		//Oracle : None
}

	@Test(expected = PreconditionError.class)
	public void preCloseDoorFail1() {
		//Conditions Initiales
		l.init(2, 5);
		l.closeDoor();
		l.doorAck();
		//Opération(s)
		l.closeDoor();
		//Oracle : exception expected
	}
	
	@Test(expected = PreconditionError.class)
	public void preCloseDoorFail2() {
		//Conditions Initiales
		l.init(2, 5);
		l.selectLevel(3);
		l.closeDoor();
		l.doorAck();
		l.beginMoveUp();
		//Opération(s)
		l.closeDoor();
		//Oracle : exception expected
	}
	
	public void preDoorAckPass() {
		//Conditions Initiales
		l.init(2,5);
		l.closeDoor();
		//Opération(s)
		l.doorAck();
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preDoorAckFail() {
		//Conditions Initiales
		l.init(2, 5);
		//Opération(s)
		l.doorAck();
		//Oracle : None
	}
	
	public void preSelectLevelPass() {
		//Conditions Initiales
		l.init(2, 5);
		//Opération(s)
		l.selectLevel(3);
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preSelectLevelFail() {
		//Conditions Initiales
		l.init(2, 5);
		//Opération(s)
		l.selectLevel(8);
		//Oracle : exception expected
	}
	
	

	/**
	 * TRANSITIONS
	 **/
	
	@Test
	public void transitionInit() {
		//CI
		//OP
		l.init(2, 5);
		//ORACLE
		/*post*/
		assertTrue(l.getMinLevel() == 2);
		assertTrue(l.getMaxLevel() == 5);
		assertTrue(l.getLevel() == 2);
		assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		assertTrue(l.getDoorStatus() == DoorStatus.OPENED);
		CommandsService c = l.getCommands();
		c.init();
		assertEquals(l.getCommands(), c);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (l.getLiftStatus()==LiftStatus.IDLE) 
			assertTrue(l.getDoorStatus()==DoorStatus.OPENED);
	}
	
	@Test
	public void transitionBeginMoveUp() {
		//CI
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3);
		l.closeDoor(); //door status -> CLOSING
		l.doorAck(); //door status -> CLOSED
		//OP
		l.beginMoveUp();
		//ORACLE
		/*post*/
		assertTrue(l.getLiftStatus() == LiftStatus.MOVING_UP);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (l.getLiftStatus()==LiftStatus.IDLE) 
			assertTrue(l.getDoorStatus()==DoorStatus.OPENED);
	}
	
	@Test
	public void transitionStepMoveUp() {
		//CI
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(3); 
		l.closeDoor(); // door status -> CLOSING
		l.doorAck(); // door status -> CLOSED
		l.beginMoveUp(); // liftStatus -> MOVING UP 
		//OP
		int level_atPre = l.getLevel();
		l.stepMoveUp();
		//ORACLE
		/*post*/
		assertTrue(l.getLevel() == level_atPre+1);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (l.getLiftStatus()==LiftStatus.IDLE) 
			assertTrue(l.getDoorStatus()==DoorStatus.OPENED);
	}
	
	@Test
	public void transitionEndMoveUp() {
		//CI
		l.init(2, 5);
		l.selectLevel(3);
		l.closeDoor();
		l.doorAck();
		l.beginMoveUp();
		l.stepMoveUp();
		//OP
		int nbUpCmd_atPre = l.getCommands().getNbUpCommands();
		l.endMoveUp();
		//ORACLE
		/*post*/
		assertTrue(l.getLiftStatus() == LiftStatus.STOP_UP);
		assertTrue(l.getCommands().getNbUpCommands() == nbUpCmd_atPre-1);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (l.getLiftStatus()==LiftStatus.IDLE) 
			assertTrue(l.getDoorStatus()==DoorStatus.OPENED);
	}
	
	@Test
	public void transitionBeginMoveDown() {
		//CI
		l.init(2, 5);
		l.selectLevel(3);
		l.closeDoor();
		l.doorAck();
		l.beginMoveUp();
		l.stepMoveUp();
		l.endMoveUp();
		l.openDoor();
		l.doorAck();
		l.selectLevel(2);
		l.closeDoor();
		l.doorAck();
		//OP
		l.beginMoveDown();
		//ORACLE
		/*post*/
		assertTrue(l.getLiftStatus() == LiftStatus.MOVING_DOWN);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (l.getLiftStatus()==LiftStatus.IDLE) 
			assertTrue(l.getDoorStatus()==DoorStatus.OPENED);
	}
	
	@Test
	public void transitionStepMoveDown() {
		//CI
		l.init(2, 5);
		l.selectLevel(3);
		l.closeDoor();
		l.doorAck();
		l.beginMoveUp();
		l.stepMoveUp();
		l.endMoveUp();
		l.openDoor();
		l.doorAck();
		l.selectLevel(2);
		l.closeDoor();
		l.doorAck();
		l.beginMoveDown();
		//OP
		int level_atPre = l.getLevel();
		l.stepMoveDown();
		//ORACLE
		/*post*/
		assertTrue(l.getLevel() == level_atPre-1);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (l.getLiftStatus()==LiftStatus.IDLE) 
			assertTrue(l.getDoorStatus()==DoorStatus.OPENED);
	}
	
	@Test
	public void transitionEndMoveDown() {
		//CI
		l.init(2, 5);
		l.selectLevel(3);
		l.closeDoor();
		l.doorAck();
		l.beginMoveUp();
		l.stepMoveUp();
		l.endMoveUp();
		l.openDoor();
		l.doorAck();
		l.selectLevel(2);
		l.closeDoor();
		l.doorAck();
		l.beginMoveDown();
		l.stepMoveDown();
		//OP
		int nbDownCmd_atPre = l.getCommands().getNbDownCommands();
		l.endMoveDown();
		//ORACLE
		/*post*/
		assertTrue(l.getLiftStatus() == LiftStatus.STOP_DOWN);
		assertTrue(l.getCommands().getNbDownCommands() == nbDownCmd_atPre-1);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (l.getLiftStatus()==LiftStatus.IDLE) 
			assertTrue(l.getDoorStatus()==DoorStatus.OPENED);
	}
	
	@Test
	public void transitionOpenDoor() {
		//CI
		l.init(2, 5);
		l.closeDoor();
		l.doorAck();
		//OP
		l.openDoor();
		//ORACLE
		/*post*/
		assertTrue(l.getDoorStatus() == DoorStatus.OPENING);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
	}
	
	@Test
	public void transitionCloseDoor() {
		//CI
		l.init(2, 5);
		//OP
		l.closeDoor();
		//ORACLE
		/*post*/
		assertTrue(l.getDoorStatus() == DoorStatus.CLOSING);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
	}
	
	@Test
	public void transitionDoorAck() {
		//CI
		l.init(2,5);
		l.closeDoor();
		//OP
		DoorStatus doorStatus_atPre = l.getDoorStatus();
		LiftStatus liftStatus_atPre = l.getLiftStatus();
		l.doorAck();
		//ORACLE
		/*post*/
		if (doorStatus_atPre == DoorStatus.OPENING)
			assertTrue(l.getDoorStatus() == DoorStatus.OPENED);
		if (doorStatus_atPre == DoorStatus.CLOSING)
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (liftStatus_atPre == LiftStatus.IDLE && l.getCommands().getNbDownCommands() > 0)
			assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_DOWN);
		if (liftStatus_atPre == LiftStatus.IDLE && l.getCommands().getNbUpCommands() > 0)
			assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_UP);
		if (liftStatus_atPre == LiftStatus.IDLE && l.getCommands().getNbUpCommands() == 0 && l.getCommands().getNbDownCommands() == 0)
			assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		if (liftStatus_atPre != LiftStatus.IDLE)
			assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		
	}
	
	@Test
	public void transitionSelectLevel() {
		//CI
		l.init(2, 5);
		//OP
		int level = 3;
		int nbUpCmd_atPre = l.getCommands().getNbUpCommands();
		int nbDownCmd_atPre = l.getCommands().getNbDownCommands();
		l.selectLevel(level);
		//ORACLE
		/*post*/
		if (level == l.getLevel()) {
			assertTrue(l.getCommands().getNbDownCommands() == nbDownCmd_atPre);
			assertTrue(l.getCommands().getNbUpCommands() == nbUpCmd_atPre);
		}
		if (level > l.getLevel()) {
			assertTrue(l.getCommands().getNbUpCommands() == nbUpCmd_atPre +1);
			assertTrue(l.getCommands().getNbDownCommands() == nbDownCmd_atPre);
		}
		if (level < l.getLevel()) {
			assertTrue(l.getCommands().getNbDownCommands() == nbDownCmd_atPre +1);
			assertTrue(l.getCommands().getNbUpCommands() == nbUpCmd_atPre);
		}
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (l.getLiftStatus()==LiftStatus.IDLE) 
			assertTrue(l.getDoorStatus()==DoorStatus.OPENED);
	}
	
	
	/**
	* ETATS REMARQUABLES
	*/
	
	@Test
	public void etatRemarquableMaxLevel() {
		//Conditions Initiales : Vides
		//Opérations
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(5); //
		l.closeDoor(); // door status -> CLOSING
		l.doorAck(); // door status -> CLOSED
		l.beginMoveUp(); // liftStatus -> MOVING UP 
		l.stepMoveUp();
		l.stepMoveUp();
		l.stepMoveUp();
		l.endMoveUp();
		l.openDoor();
		//capture
		LiftStatus getLiftStatus_atPre = l.getLiftStatus();
		DoorStatus getDoorStatus_atPre = l.getDoorStatus();
		System.out.println(getDoorStatus_atPre);
		System.out.println(getLiftStatus_atPre);
		//last op
		l.doorAck();
		//Oracle
		//Post-conditions

		if(getDoorStatus_atPre == DoorStatus.OPENING) {
			assertTrue(l.getDoorStatus() == DoorStatus.OPENED);
		} else {
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		}
		
		if(getLiftStatus_atPre == LiftStatus.IDLE) {
			if(l.getCommands().getNbDownCommands() > 0) {
				assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_DOWN);
			} else if(l.getCommands().getNbUpCommands() > 0) {
				assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_UP);
			} else {
				assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
			}
		} else {
			assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		}
		//Invariants
		assertTrue(l.getLevel() == l.getMaxLevel());
	}
	
	@Test
	public void etatRemarquableMinLevel() {
		//Conditions Initiales : Vides
		//Opérations
		l.init(2, 5);// liftStatus -> IDLE
		l.closeDoor();
		l.doorAck();
		l.openDoor();
		//capture
		LiftStatus getLiftStatus_atPre = l.getLiftStatus();
		DoorStatus getDoorStatus_atPre = l.getDoorStatus();
		System.out.println(getDoorStatus_atPre);
		System.out.println(getLiftStatus_atPre);
		//last op
		l.doorAck();
		//Oracle
		//Post-conditions

		if(getDoorStatus_atPre == DoorStatus.OPENING) {
			assertTrue(l.getDoorStatus() == DoorStatus.OPENED);
		} else {
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		}
		
		if(getLiftStatus_atPre == LiftStatus.IDLE) {
			if(l.getCommands().getNbDownCommands() > 0) {
				assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_DOWN);
			} else if(l.getCommands().getNbUpCommands() > 0) {
				assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_UP);
			} else {
				assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
			}
		} else {
			assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		}
		//Invariants
				assertTrue(l.getLevel() == l.getMinLevel());
	}




	/**
	*  PAIRES DE TRANSITIONS
	*/

	@Test 
	public void PairesCloseDoorDoorAck() {
		//CI
		l.init(2, 5);
		//OP
		l.closeDoor();
		DoorStatus doorStatus_atPre = l.getDoorStatus();
		LiftStatus liftStatus_atPre = l.getLiftStatus();
		
		l.doorAck();
		//Oracle 
		if (doorStatus_atPre == DoorStatus.OPENING)
			assertTrue(l.getDoorStatus() == DoorStatus.OPENED);
		if (doorStatus_atPre == DoorStatus.CLOSING)
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (liftStatus_atPre == LiftStatus.IDLE && l.getCommands().getNbDownCommands() > 0)
			assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_DOWN);
		if (liftStatus_atPre == LiftStatus.IDLE && l.getCommands().getNbUpCommands() > 0)
			assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_UP);
		if (liftStatus_atPre == LiftStatus.IDLE && l.getCommands().getNbUpCommands() == 0 && l.getCommands().getNbDownCommands() == 0)
			assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		if (liftStatus_atPre != LiftStatus.IDLE)
			assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
	}

	@Test 
	public void PairesOpenDoorDoorAck() {
		//CI
		l.init(2, 5);
		l.closeDoor();
		l.doorAck();
		//OP
		l.openDoor();
		DoorStatus doorStatus_atPre = l.getDoorStatus();
		LiftStatus liftStatus_atPre = l.getLiftStatus();
		l.doorAck();
		//Oracle 
		if (doorStatus_atPre == DoorStatus.OPENING)
			assertTrue(l.getDoorStatus() == DoorStatus.OPENED);
		if (doorStatus_atPre == DoorStatus.CLOSING)
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (liftStatus_atPre == LiftStatus.IDLE && l.getCommands().getNbDownCommands() > 0)
			assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_DOWN);
		if (liftStatus_atPre == LiftStatus.IDLE && l.getCommands().getNbUpCommands() > 0)
			assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_UP);
		if (liftStatus_atPre == LiftStatus.IDLE && l.getCommands().getNbUpCommands() == 0 && l.getCommands().getNbDownCommands() == 0)
			assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		if (liftStatus_atPre != LiftStatus.IDLE)
			assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
	}

	@Test 
	public void PairesDoorAckCloseDoor() {
		//CI
		l.init(2, 5);
		l.closeDoor();
		l.doorAck();
		l.openDoor();
		//OP
		l.doorAck();
		l.closeDoor();
		//Oracle 
		assertTrue(l.getDoorStatus() == DoorStatus.CLOSING);
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
	}
	
	@Test 
	public void PairesInitSelectLevel() {
		//CI : None
		//OP
		l.init(2,5);
		int level = 3;
		int nbUpCmd_atPre = l.getCommands().getNbUpCommands();
		int nbDownCmd_atPre = l.getCommands().getNbDownCommands();
		
		l.selectLevel(3);
		//Oracle 
		if (level == l.getLevel()) {
			assertTrue(l.getCommands().getNbDownCommands() == nbDownCmd_atPre);
			assertTrue(l.getCommands().getNbUpCommands() == nbUpCmd_atPre);
		}
		if (level > l.getLevel()) {
			assertTrue(l.getCommands().getNbUpCommands() == nbUpCmd_atPre +1);
			assertTrue(l.getCommands().getNbDownCommands() == nbDownCmd_atPre);
		}
		if (level < l.getLevel()) {
			assertTrue(l.getCommands().getNbDownCommands() == nbDownCmd_atPre +1);
			assertTrue(l.getCommands().getNbUpCommands() == nbUpCmd_atPre);
		}
		/*inv*/
		assertTrue(l.getMinLevel() <= l.getLevel() && l.getLevel() <= l.getMaxLevel());
		if (l.getLiftStatus()==LiftStatus.MOVING_UP || l.getLiftStatus()==LiftStatus.MOVING_DOWN) 
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		if (l.getLiftStatus()==LiftStatus.IDLE) 
			assertTrue(l.getDoorStatus()==DoorStatus.OPENED);
	}

	/**
	*  SCENARIOS
	*/

	@Test
	public void scenarioMonteAuMaxEtDescendAuMinUnParUn(){
		l.init(2, 5);// liftStatus -> IDLE
		l.selectLevel(5); //
		l.closeDoor(); // door status -> CLOSING
		l.doorAck(); // door status -> CLOSED
		l.beginMoveUp(); // liftStatus -> MOVING UP 
		l.stepMoveUp();
		l.stepMoveUp();
		l.stepMoveUp();
		l.endMoveUp();
		l.openDoor();
		l.doorAck();
		//Jsuis au plus haut level
		l.selectLevel(4);
		l.closeDoor();
		l.doorAck();
		l.beginMoveDown();
		l.stepMoveDown();
		l.endMoveDown();
		l.openDoor();
		l.doorAck();
		l.selectLevel(3);
		l.closeDoor();
		l.doorAck();
		l.beginMoveDown();
		l.stepMoveDown();
		l.endMoveDown();
		l.openDoor();
		l.doorAck();
		l.selectLevel(2);
		l.closeDoor();
		l.doorAck();
		l.beginMoveDown();
		l.stepMoveDown();
		l.endMoveDown();
		l.openDoor();
		//capture
		LiftStatus getLiftStatus_atPre = l.getLiftStatus();
		DoorStatus getDoorStatus_atPre = l.getDoorStatus();
		//last op
		l.doorAck();
		//arrivé au min
		if(getDoorStatus_atPre == DoorStatus.OPENING) {
			assertTrue(l.getDoorStatus() == DoorStatus.OPENED);
		} else {
			assertTrue(l.getDoorStatus() == DoorStatus.CLOSED);
		}
		
		if(getLiftStatus_atPre == LiftStatus.IDLE) {
			if(l.getCommands().getNbDownCommands() > 0) {
				assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_DOWN);
			} else if(l.getCommands().getNbUpCommands() > 0) {
				assertTrue(l.getLiftStatus() == LiftStatus.STANDBY_UP);
			} else {
				assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
			}
		} else {
			assertTrue(l.getLiftStatus() == LiftStatus.IDLE);
		}
		//Invariants
				assertTrue(l.getLevel() == l.getMinLevel());
	}
	
}
