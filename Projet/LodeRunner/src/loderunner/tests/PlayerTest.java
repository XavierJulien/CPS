package loderunner.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.CharacterContract;
import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.contracts.PlayerContract;
import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;
import loderunner.impl.PlayerImpl;
import loderunner.services.PlayerService;

public class PlayerTest extends CharacterTest{
	
	private EngineContract engine;
	private PlayerService player;
	
	public PlayerTest() {
		player = null;
	}
		
	@Before
	public void beforeTests() {
		engine = new EngineContract(new EngineImpl());
		System.out.println("passé par la ");
		es = new EditableScreenContract(new EditableScreenImpl());
		player = new PlayerContract(new PlayerImpl());
		es.init(15, 10);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
		ArrayList<Coord> g_list = new ArrayList<>();
		ArrayList<Item> item_list = new ArrayList<>();
		g_list.add(new Coord(8, 2));
		item_list.add(new Item(14,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), g_list, item_list);
		envi = engine.getEnvi();
		player = engine.getPlayer();
		c = (CharacterContract)player;
	}
	
	@After
	public final void afterTests() {
		engine = null;
		es = null;
		player = null;
	}
	
	public void checkinvP() {
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
		player.init(engine, new Coord(15,5));
		//Oracle : Error
	}
	
	/**
	 * TRANSITIONS
	 */
	
	@Test
	public void transitionStep() {
		//Conditions Initiales  
		engine.addCommand(Command.DIGL);
		player = engine.getPlayer();
		//Opération(s)
		player.step();
		//Oracle 
		assertTrue(engine.getEnvi().getCellNature(player.getWdt()-1, player.getHgt()-1) == Cell.HOL);
		checkinvP();
	}
	
	@Test
	public void transitionStep2() {
		//Conditions Initiales
		engine.addCommand(Command.DIGR);
		//Opération(s)
		player.step();
		//Oracle 
		assertTrue(engine.getEnvi().getCellNature(player.getWdt()+1, player.getHgt()-1) == Cell.HOL);
		checkinvP();
	}
	
	@Test
	public void transitionStep3() {
		//Conditions Initiales
		engine.addCommand(Command.DOWN);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinvP();
	}
	
	@Test
	public void transitionStep4() {
		//Conditions Initiales
		engine.addCommand(Command.UP);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinvP();
	}
	
	@Test
	public void transitionStep5() {
		//Conditions Initiales
		engine.addCommand(Command.LEFT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		//Oracle 
		assertEquals(wdt_capture-1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinvP();
	}
	
	@Test
	public void transitionStep6() {
		//Conditions Initiales
		engine.addCommand(Command.RIGHT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		//Oracle 
		assertEquals(wdt_capture+1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinvP();
	}
	
	/**
	 * PAIRE DE TRANSITIONS
	 */
	
	@Test
	public void paireStepStep() {
		//Conditions Initiales
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.LEFT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinvP();
	}
	
	@Test
	public void paireStepStep2() {
		//Conditions Initiales
		engine.addCommand(Command.DOWN);
		engine.addCommand(Command.UP);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinvP();
	}
	
	@Test
	public void paireStepStep3() {
		//Conditions Initiales
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.LEFT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture-2, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		checkinvP();
	}
	
	@Test
	public void paireStepStep4() {
		//Conditions Initiales
		engine.addCommand(Command.DIGL);
		engine.addCommand(Command.LEFT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture-1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertEquals(Cell.HOL, engine.getEnvi().getCellNature(player.getWdt()-1, player.getHgt()-1));
		checkinvP();
	}
	
	@Test
	public void paireStepStep5() {
		//Conditions Initiales
		engine.addCommand(Command.DIGL);
		engine.addCommand(Command.DIGR);
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
		checkinvP();
	}
	/**
	* ETATS REMARQUABLES
	*/
	//rencontre un autre guard
	//rencontre le joueur
	//rencontre un trésor : le ramasse
	
	//arrive sur un teleporteur
	
	
	
	@Test
	public void etatRemarquableFallInHol() {
		//Conditions Initiales
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.DIGL);
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
		checkinvP();
	}

	/**
	 * SCENARIO
	 */
	
	@Test
	public void scenario() {
		//Conditions Initiales
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.DIGR);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.DIGR);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.DIGL);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.LEFT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		player.step();
		player.step();
		player.step();
		player.step();
		player.step();
		player.step();
		player.step();
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture+1, player.getWdt());
		assertEquals(hgt_capture-1, player.getHgt());
		assertEquals(Cell.HOL, engine.getEnvi().getCellNature(player.getWdt()-3, player.getHgt()));
		assertEquals(Cell.HOL, engine.getEnvi().getCellNature(player.getWdt(), player.getHgt()));
		
		checkinvP();
	}
	
}
