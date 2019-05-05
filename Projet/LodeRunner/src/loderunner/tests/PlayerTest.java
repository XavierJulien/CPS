package loderunner.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.contracts.PlayerContract;
import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;
import loderunner.impl.PlayerImpl;
//import loderunner.main.MapBuilder;
import loderunner.services.EditableScreenService;
import loderunner.services.PlayerService;

public class PlayerTest {
	
	private EngineContract engine;
	private PlayerService player;
	private EditableScreenService edit;
	//private MapBuilder builder;
	
	
	
	@Before
	public void beforeTests() {
		engine = new EngineContract(new EngineImpl());
		//engine.init(builder.buildMapTestPlayer("src/loderunner/maps/test0.txt"), null,null,null);
		edit = new EditableScreenContract(new EditableScreenImpl());
		player = new PlayerContract(new PlayerImpl());
		edit.init(5, 3);
		for(int i = 0;i<edit.getWidth();i++) {
				edit.setNature(i, 0, Cell.MTL);
				edit.setNature(i, 1, Cell.PLT);
				edit.setNature(i, 2, Cell.EMP);
		}
		engine.init(edit, new Coord(1,2), new ArrayList<>(), new ArrayList<>());
	}
	
	@After
	public final void afterTests() {
		engine = null;
	}
	
	public void checkinv() {
		assertTrue(player.getEngine().getEnvi().getCellNature(player.getWdt(), player.getHgt()) == Cell.EMP
				|| player.getEngine().getEnvi().getCellNature(player.getWdt(), player.getHgt()) == Cell.HOL
				|| player.getEngine().getEnvi().getCellNature(player.getWdt(), player.getHgt()) == Cell.LAD
				|| player.getEngine().getEnvi().getCellNature(player.getWdt(), player.getHgt()) == Cell.HDR);
		if (player.getEngine().getEnvi().getCellContent(player.getWdt(), player.getHgt()).getCharacter() != null)
			assertEquals(player, player.getEnvi().getCellContent(player.getWdt(), player.getHgt()).getCharacter());
		if (player.getEngine().getEnvi().getCellContent(player.getWdt(), player.getHgt()).getGuard() != null)
			assertEquals(player, player.getEnvi().getCellContent(player.getWdt(), player.getHgt()).getGuard());
		if(player.getEngine().getEnvi().getCellContent(player.getWdt(), player.getHgt()).getCharacter() != null) {
			assertEquals(player,player.getEngine().getEnvi().getCellContent(player.getWdt(), player.getHgt()).getCharacter());
		}
	}
	
	/**
	 * PRECONDITIONS
	 **/
	
	@Test
	public void preInitPass() {
		//Conditions Initiales : None
		//Opération(s)
		player.init(engine, new Coord(2,2));
		//Oracle : None
		assertEquals(player.getWdt(),2);
		assertEquals(player.getWdt(),2);
		assertEquals(player.getEngine(),engine);
	}

	@Test(expected = PreconditionError.class)
	public void preInitFail() {
		//Conditions Initiales : None
		//Opération(s)
		player.init(engine, new Coord(5,5));
		//Oracle : Error
		}
	
	@Test
	public void preStepPass() {
		//Conditions Initiales  
		engine.addCommand(Command.DIGL);
		player = engine.getDelegate().getPlayer();
		//Opération(s)
		player.step();
		//Oracle : None
		assertTrue(engine.getEnvi().getCellNature(player.getWdt()-1, player.getHgt()-1) == Cell.HOL);
		checkinv();
	}
	
	@Test
	public void preStepPass2() {
		//Conditions Initiales
		engine.addCommand(Command.DIGR);
		player = engine.getDelegate().getPlayer();
		//Opération(s)
		player.step();
		//Oracle 
		assertTrue(engine.getEnvi().getCellNature(player.getWdt()+1, player.getHgt()-1) == Cell.HOL);
		checkinv();
	}
	
	@Test
	public void preStepPass3() {
		//Conditions Initiales
		engine.addCommand(Command.DOWN);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinv();
	}
	
	@Test
	public void preStepPass4() {
		//Conditions Initiales
		engine.addCommand(Command.UP);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinv();
	}
	
	@Test
	public void preStepPass5() {
		//Conditions Initiales
		engine.addCommand(Command.LEFT);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		//Oracle 
		assertEquals(wdt_capture-1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinv();
	}
	
	@Test
	public void preStepPass6() {
		//Conditions Initiales
		engine.addCommand(Command.RIGHT);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		//Oracle 
		assertEquals(wdt_capture+1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinv();
	}
	
	/**
	 * INVARIANTS
	 **/
	@Test(expected = InvariantError.class)
	public void preInitFail2() {
		//Conditions Initiales : None
		//Opération(s)
		player.init(engine, new Coord(1,2));
		//Oracle : None
	}
}
