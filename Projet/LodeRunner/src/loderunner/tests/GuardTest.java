package loderunner.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.contracts.EnvironnementContract;
import loderunner.contracts.GuardContract;
import loderunner.contracts.PlayerContract;
import loderunner.data.Cell;
import loderunner.data.Coord;
import loderunner.errors.InvariantError;
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;
import loderunner.impl.EnvironnementImpl;
import loderunner.impl.GuardImpl;
import loderunner.impl.PlayerImpl;
import loderunner.services.EditableScreenService;
import loderunner.services.EngineService;
import loderunner.services.PlayerService;

public class GuardTest extends CharacterTest {
	
	private PlayerService player;
	private EngineService engine;
	private EditableScreenService es;
	
	@Before
	public void beforeTests() {
		es = new EditableScreenContract(new EditableScreenImpl());
		engine = new EngineContract(new EngineImpl());
		c = new GuardContract(new GuardImpl(-1));
		engine = new EngineContract(new EngineImpl());
		//engine.init(builder.buildMapTestPlayer("src/loderunner/maps/test0.txt"), null,null,null);
		player = new PlayerContract(new PlayerImpl());
		es.init(5, 3);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
		engine.init(es, new Coord(2,2), new ArrayList<>(), new ArrayList<>());
		envi = engine.getEnvi();
	}
	
	@After
	public void afterTests() {
		envi = null;
		engine = null;
		c = null;
	}
	
	public void checkinv() {
		Cell nat = c.getEnvi().getCellNature(c.getWdt(), c.getHgt());
		assertTrue(nat == Cell.EMP || nat == Cell.HOL || nat == Cell.LAD || nat == Cell.HDR);
	}
	
	/**
	 * PRECONDITIONS
	 **/
	@Test
	public void testInitPass() {
		//Conditions Initiales : None
		EditableScreenContract es = new EditableScreenContract(new EditableScreenImpl());
		envi.init(es);
		//engine.init(es, new Coord(, y), guards, treasures);
		//Opération(s)
		//player.init(15,10);
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
