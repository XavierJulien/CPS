package loderunner.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import loderunner.data.GameState;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.data.Teleporteur;
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
		es = new EditableScreenContract(new EditableScreenImpl());
		player = new PlayerContract(new PlayerImpl());
		es.init(15, 10);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
		ArrayList<Coord> g_list = new ArrayList<>();
		ArrayList<Item> item_list = new ArrayList<>();
		g_list.add(new Coord(3, 2));
		item_list.add(new Item(0,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), g_list, item_list,new ArrayList<>(),new Item(12,2,ItemType.Gauntlet));
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
	
	public void preHitLPass() {//aucun garde a gauche
		//Conditions Initiales : None
		player.setGauntlet(new Item(1,2,ItemType.Gauntlet));
		//Opération(s)
		player.hitRight();
		//Oracle : Error
	}
	
	@Test(expected = PreconditionError.class)
	public void preHitLFail1() {//aucun garde a gauche
		//Conditions Initiales : None
		player.setGauntlet(new Item(1,2,ItemType.Gauntlet));
		//Opération(s)
		player.hitLeft();
		//Oracle : Error
	}
	
	@Test(expected = PreconditionError.class)
	public void preHitLFail2() {
		//Conditions Initiales : None
		//Opération(s)
		player.hitLeft();
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
	
	@Test
	public void transitionHitR() {
		//Conditions Initiales
		engine = new EngineContract(new EngineImpl());
		es = new EditableScreenContract(new EditableScreenImpl());
		player = new PlayerContract(new PlayerImpl());
		es.init(15, 10);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
		ArrayList<Coord> g_list = new ArrayList<>();
		g_list.add(new Coord(5, 2));
		ArrayList<Item> item_list = new ArrayList<>();
		item_list.add(new Item(0,2,ItemType.Treasure));
		ArrayList<Teleporteur> tp_list = new ArrayList<>();
		engine.init(es, new Coord(2,2), g_list, item_list,tp_list,new Item(1,2,ItemType.Gauntlet));
		envi = engine.getEnvi();
		player = engine.getPlayer();
		player.setGauntlet(new Item(1,2,ItemType.Gauntlet));
		c = (CharacterContract)player;
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.hitRight();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertEquals(0, engine.getGuards().size());
		checkinvP();
	}
	
	@Test
	public void transitionHitL() {
		//Conditions Initiales
		engine = new EngineContract(new EngineImpl());
		es = new EditableScreenContract(new EditableScreenImpl());
		player = new PlayerContract(new PlayerImpl());
		es.init(15, 10);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
		ArrayList<Coord> g_list = new ArrayList<>();
		g_list.add(new Coord(0, 2));
		ArrayList<Item> item_list = new ArrayList<>();
		item_list.add(new Item(5,2,ItemType.Treasure));
		ArrayList<Teleporteur> tp_list = new ArrayList<>();
		engine.init(es, new Coord(2,2), g_list, item_list,tp_list,new Item(1,2,ItemType.Gauntlet));
		envi = engine.getEnvi();
		player = engine.getPlayer();
		player.setGauntlet(new Item(1,2,ItemType.Gauntlet));
		c = (CharacterContract)player;
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.hitLeft();
		//Oracle 
		assertEquals(wdt_capture, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertEquals(0, engine.getGuards().size());
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
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.DIGL);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture-1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertEquals(Cell.HOL, engine.getEnvi().getCellNature(player.getWdt(), player.getHgt()-1));
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
	
	@Test
	public void paireStepHitL() {
		//Conditions Initiales
		engine = new EngineContract(new EngineImpl());
		es = new EditableScreenContract(new EditableScreenImpl());
		player = new PlayerContract(new PlayerImpl());
		es.init(15, 10);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
		ArrayList<Coord> g_list = new ArrayList<>();
		g_list.add(new Coord(0, 2));
		ArrayList<Item> item_list = new ArrayList<>();
		item_list.add(new Item(5,2,ItemType.Treasure));
		ArrayList<Teleporteur> tp_list = new ArrayList<>();
		engine.init(es, new Coord(3,2), g_list, item_list,tp_list,new Item(2,2,ItemType.Gauntlet));
		envi = engine.getEnvi();
		player = engine.getPlayer();
		player.setGauntlet(new Item(1,2,ItemType.Gauntlet));
		c = (CharacterContract)player;
		engine.addCommand(Command.LEFT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.hitLeft();
		//Oracle 
		assertEquals(wdt_capture-1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertEquals(0, engine.getGuards().size());
		checkinvP();
	}
	
	@Test
	public void paireStepHitR() {
		//Conditions Initiales
		engine = new EngineContract(new EngineImpl());
		es = new EditableScreenContract(new EditableScreenImpl());
		player = new PlayerContract(new PlayerImpl());
		es.init(15, 10);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
		ArrayList<Coord> g_list = new ArrayList<>();
		g_list.add(new Coord(6, 2));
		ArrayList<Item> item_list = new ArrayList<>();
		item_list.add(new Item(5,2,ItemType.Treasure));
		ArrayList<Teleporteur> tp_list = new ArrayList<>();
		engine.init(es, new Coord(0,2), g_list, item_list,tp_list,new Item(2,2,ItemType.Gauntlet));
		envi = engine.getEnvi();
		player = engine.getPlayer();
		player.setGauntlet(new Item(1,2,ItemType.Gauntlet));
		c = (CharacterContract)player;
		engine.addCommand(Command.RIGHT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		player.hitRight();
		//Oracle 
		assertEquals(wdt_capture+1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertEquals(0, engine.getGuards().size());
		checkinvP();
	}
	/**
	* ETATS REMARQUABLES
	*/
	//rencontre un autre guard
	@Test
	public void etatRemarquableEncounterGuard() {
		//Conditions Initiales
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.RIGHT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		engine.step();
		//Oracle 
		assertEquals(wdt_capture+1, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertNotEquals(null, engine.getEnvi().getCellContent(wdt_capture+1, hgt_capture).getGuard());
		assertEquals(GameState.Loss,engine.getStatus());
		checkinvP();
	}
	//rencontre un trésor : le ramasse
	@Test
	public void etatRemarquableEncounterTreasure() {
		//Conditions Initiales
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.LEFT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		assertNotEquals(null,engine.getEnvi().getCellContent(wdt_capture-2, hgt_capture).getItem());
		//Opération(s)
		player.step();
		player.step();
		engine.step();
		//Oracle 
		assertEquals(wdt_capture-2, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertEquals(null, engine.getEnvi().getCellContent(wdt_capture-2, hgt_capture).getItem());
		checkinvP();
	}
	//arrive sur un teleporteur
	
	@Test
	public void etatRemarquableTeleport() {
		//Conditions Initiales
		engine = new EngineContract(new EngineImpl());
		es = new EditableScreenContract(new EditableScreenImpl());
		player = new PlayerContract(new PlayerImpl());
		es.init(15, 10);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
		ArrayList<Coord> g_list = new ArrayList<>();
		ArrayList<Item> item_list = new ArrayList<>();
		g_list.add(new Coord(5, 2));
		item_list.add(new Item(0,2,ItemType.Treasure));
		ArrayList<Teleporteur> tp_list = new ArrayList<>();
		tp_list.add(new Teleporteur(new Coord(1,1), new Coord(13,1)));
		engine.init(es, new Coord(2,2), g_list, item_list,tp_list,new Item(12,2,ItemType.Gauntlet));
		envi = engine.getEnvi();
		player = engine.getPlayer();
		c = (CharacterContract)player;
		
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.LEFT);
		int wdt_capture = player.getWdt();
		int hgt_capture = player.getHgt();
		//Opération(s)
		player.step();
		engine.step();
		player.step();
		player.step();
		//Oracle 
		assertEquals(wdt_capture-2, player.getWdt());
		assertEquals(hgt_capture, player.getHgt());
		assertTrue(engine.getPlayer().hasGauntlet());
		checkinvP();
	}
	
	//tombe dans un trou
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
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.DIGL);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.DIGL);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.DIGL);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.DIGL);
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
		//Oracle 
		for(int i = 1;i<5;i++) {
			assertEquals(Cell.HOL, engine.getEnvi().getCellNature(i, 1));
		}
		assertEquals(wdt_capture+2, player.getWdt());
		assertEquals(hgt_capture-1, player.getHgt());
		checkinvP();
	}
	
}
