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
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;
import loderunner.impl.PlayerImpl;
import loderunner.services.EditableScreenService;
import loderunner.services.PlayerService;

public class PlayerTest {
	
	private EngineContract engine;
	private PlayerService player;
	private EditableScreenService edit;
	
	
	
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
		}
		engine.init(edit, new Coord(2,2), new ArrayList<>(), new ArrayList<>());
	}
	
	@After
	public final void afterTests() {
		engine = null;
		edit = null;
		player = null;
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
			assertEquals(player, player.getEnvi().getCellContent(player.getWdt(), player.getHgt()).getCharacter());
		}
	}
	
	/**
	 * PRECONDITIONS
	 **/
	
	@Test
	public void preInitPass() {
		//Conditions Initiales : None
		//Opération(s)
		player.init(engine, new Coord(3,2));
		//Oracle : None
		assertEquals(player.getWdt(),3);
		assertEquals(player.getHgt(),2);
		assertEquals(player.getEngine(),engine);
	}

	@Test(expected = PreconditionError.class)
	public void preInitFail() {
		//Conditions Initiales : None
		//Opération(s)
		player.init(engine, new Coord(5,5));
		//Oracle : Error
	}
	
	/**
	 * TRANSITIONS
	 */
	
	@Test
	public void StepPass() {
		//Conditions Initiales  
		engine.addCommand(Command.DIGL);
		player = engine.getDelegate().getPlayer();
		//Opération(s)
		player.step();
		//Oracle 
		assertTrue(engine.getEnvi().getCellNature(player.getWdt()-1, player.getHgt()-1) == Cell.HOL);
		checkinv();
	}
	
	@Test
	public void StepPass2() {
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
	public void StepPass3() {
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
	public void StepPass4() {
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
	public void StepPass5() {
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
	public void StepPass6() {
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
	 * PAIRE DE TRANSITIONS
	 */
	
	@Test
	public void StepStepPass() {
		//Conditions Initiales
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.LEFT);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinv();
	}
	
	@Test
	public void StepStepPass2() {
		//Conditions Initiales
		engine.addCommand(Command.DOWN);
		engine.addCommand(Command.UP);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinv();
	}
	
	@Test
	public void StepStepPass3() {
		//Conditions Initiales
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.LEFT);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture-2, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinv();
	}
	
	@Test
	public void StepStepPass4() {
		//Conditions Initiales
		engine.addCommand(Command.DIGL);
		engine.addCommand(Command.LEFT);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture-1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertEquals(Cell.HOL, engine.getEnvi().getCellNature(player.getWdt()-1, player.getHgt()-1));
		checkinv();
	}
	
	@Test
	public void StepStepPass5() {
		//Conditions Initiales
		engine.addCommand(Command.DIGL);
		engine.addCommand(Command.DIGR);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertEquals(Cell.HOL, engine.getEnvi().getCellNature(player.getWdt()-1, player.getHgt()-1));
		assertEquals(Cell.HOL, engine.getEnvi().getCellNature(player.getWdt()+1, player.getHgt()-1));
		checkinv();
	}
	/**
	* ETATS REMARQUABLES
	*/
	
	@Test
	public void EtatRemarquable() {
		//Conditions Initiales
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.DIGL);
		player = engine.getDelegate().getPlayer();
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture-1, player.getWdt());
		assertEquals(hgt_capture-1, player.getHgt());
		assertEquals(Cell.HOL, engine.getEnvi().getCellNature(player.getWdt(), player.getHgt()));
		checkinv();
	}

	/**
	 * SCENARIO
	 */
	
	
	
}
