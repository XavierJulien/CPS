package loderunner.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.contracts.GuardContract;
import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;
import loderunner.impl.GuardImpl;
import loderunner.services.EditableScreenService;
import loderunner.services.EngineService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class GuardTest extends CharacterTest {
	
	private GuardService guard;
	private PlayerService player;
	private EngineService engine;
	private EditableScreenService es;
	
	@Before
	public void beforeTests() {
		es = new EditableScreenContract(new EditableScreenImpl());
		engine = new EngineContract(new EngineImpl());
		guard = new GuardContract(new GuardImpl(-1));
		c = guard;
		es.init(15, 10);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
		ArrayList<Coord> g_list = new ArrayList<>();
		g_list.add(new Coord(8, 2));
		engine.init(es, new Coord(2,2), g_list, new ArrayList<>());
		envi = engine.getEnvi();
		player = engine.getPlayer();
		guard = engine.getGuards().get(0);
;	}
	
	@After
	public void afterTests() {
		envi = null;
		engine = null;
		c = null;
		es = null;
	}
	
	public void checkinvG() {
		Cell nat = c.getEnvi().getCellNature(c.getWdt(), c.getHgt());
		assertTrue(nat == Cell.EMP || nat == Cell.HOL || nat == Cell.LAD || nat == Cell.HDR);
	}
	
	/**
	 * PRECONDITIONS
	 **/
	@Test
	public void preInitPass() {
		//Conditions Initiales : None
		//Opération :
		guard.init(engine, 8, 2, player);
		//Oracle : None
	}
	
	@Test(expected = PreconditionError.class)
	public void preInitFail() {
		//Conditions Initiales : None
		//Opération :
		guard.init(engine, 2, 2, player);
		//Oracle : error exists character 
	}
	
	@Test(expected = PreconditionError.class)
	public void preInitFail2() {
		//Conditions Initiales : None
		//Opération :
		guard.init(engine, 2, 1, player);
		//Oracle : error Cell.PLT
	}
	
	@Test
	public void preClimbLeftPass() {
		//Conditions Initiales : 
		engine.getEnvi().dig(8, 1);
		engine.addCommand(Command.NEUTRAL);
		//guard = engine.getGuards().get(0);
		engine.step();
		//Opération :
		guard.climbLeft();
		//Oracle : None
	}
	
	@Test
	public void preClimbRightPass() {
		//Conditions Initiales : 
		engine.getEnvi().dig(8, 1);
		engine.addCommand(Command.NEUTRAL);
		//guard = engine.getGuards().get(0);
		engine.step();
		//Opération :
		guard.climbRight();
		//Oracle : None
	}
	
	@Test(expected = PreconditionError.class)
	public void preClimbLeftFail() {
		//Conditions Initiales : 
		//Opération :
		guard.climbLeft();
		//Oracle : None
	}
	
	@Test(expected = PreconditionError.class)
	public void preClimbRightFail() {
		//Conditions Initiales : 
		//Opération :
		guard.climbRight();
		//Oracle : None
	}
	
	
	/**
	 * TRANSITIONS
	 **/
	
	/**
	* ETATS REMARQUABLES
	*/
	
	/**
	*  PAIRES DE TRANSITIONS
	*/
	
		
}
