package loderunner.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EngineContract;
import loderunner.data.Cell;
import loderunner.data.CellContent;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.errors.InvariantError;
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;
import loderunner.services.EditableScreenService;
import loderunner.services.GuardService;

public class EngineTest {
	
	private EngineContract engine;
	private EditableScreenService es;
	
	@Before
	public void beforeTests() {
		engine = new EngineContract(new EngineImpl());
		es = new EditableScreenContract(new EditableScreenImpl());
		es.init(15, 10);
		for(int i = 0;i<es.getWidth();i++) {
				es.setNature(i, 0, Cell.MTL);
				es.setNature(i, 1, Cell.PLT);
		}
	}
	
	@After
	public final void afterTests() {
		engine = null;
		es = null;
	}
	
	public void checkinv() {
		CellContent cell_check = engine.getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt());
		assertTrue(cell_check.getCharacter().equals(engine.getPlayer())); 
		for(GuardService g : engine.getGuards()) {
			cell_check = engine.getEnvi().getCellContent(g.getWdt(), g.getHgt());
			if(!cell_check.getGuard().equals(g)) throw new InvariantError("checkInvariants : Le guard aux position du guard n'est pas le guard");
			for(Item t : engine.getTreasures()) {
				cell_check = engine.getEnvi().getCellContent(t.getCol(), t.getHgt());
				assertTrue(cell_check.getItem() != null && (t.getCol() != g.getWdt() || t.getHgt() != g.getHgt()) && !g.hasItem());
			}
			
		}
		for(Item t : engine.getTreasures()) {
			cell_check = engine.getEnvi().getCellContent(t.getCol(), t.getHgt());
			for(GuardService g : engine.getGuards()) {
				assertTrue(cell_check.getItem() != null && (t.getCol() != g.getWdt() || t.getHgt() != g.getHgt()));
			}
			
		}
	}
	
	/**
	 * PRECONDITIONS
	 **/
	
	@Test
	public void preInitPass() {
		//Conditions Initiales :
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		guards.add(new Coord(10,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		//Opération(s)
		engine.init(es, new Coord(2,2), guards, treasures);
		//Oracle : None
	}

	@Test(expected = PreconditionError.class)
	public void preInitFail() {
		//Conditions Initiales : None
		//Opération(s)
		engine.init(es, new Coord(20,2), new ArrayList<>(), new ArrayList<>());
		//Oracle : Error
	}
	
	/**
	 * TRANSITIONS
	 **/
	
	@Test
	public void transitionAddCommand() {
		//Conditions Initiales 
		engine.init(es, new Coord(2,2), new ArrayList<>(), new ArrayList<>());
		//Opération(s)
		engine.addCommand(Command.RIGHT);
		//Oracle : 
		assertEquals(Command.RIGHT,engine.getNextCommand());
		checkinv();
	}
	
	@Test
	public void transitionStep() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		guards.add(new Coord(10,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.RIGHT);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		ArrayList<Coord> guards_capture = engine.getGuardsCoord();
		//Opération(s)
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture+1,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		for(int i = 0;i<guards_capture.size();i++) {
			Coord c = guards_capture.get(i);
			assertNotEquals(c.getX(),engine.getGuards().get(i).getWdt());
			assertEquals(c.getY(),engine.getGuards().get(i).getHgt());
		}
		checkinv();
	}
	
	@Test
	public void transitionStep2() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		guards.add(new Coord(10,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.LEFT);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		ArrayList<Coord> guards_capture = engine.getGuardsCoord();
		//Opération(s)
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture-1,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		for(int i = 0;i<guards_capture.size();i++) {
			Coord c = guards_capture.get(i);
			assertNotEquals(c.getX(),engine.getGuards().get(i).getWdt());
			assertEquals(c.getY(),engine.getGuards().get(i).getHgt());
		}
		checkinv();
	}
	
	@Test
	public void transitionStep3() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		guards.add(new Coord(10,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.UP);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		ArrayList<Coord> guards_capture = engine.getGuardsCoord();
		//Opération(s)
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		for(int i = 0;i<guards_capture.size();i++) {
			Coord c = guards_capture.get(i);
			assertNotEquals(c.getX(),engine.getGuards().get(i).getWdt());
			assertEquals(c.getY(),engine.getGuards().get(i).getHgt());
		}
		checkinv();
	}
	
	@Test
	public void transitionStep4() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		guards.add(new Coord(10,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.DOWN);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		ArrayList<Coord> guards_capture = engine.getGuardsCoord();
		//Opération(s)
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		for(int i = 0;i<guards_capture.size();i++) {
			Coord c = guards_capture.get(i);
			assertNotEquals(c.getX(),engine.getGuards().get(i).getWdt());
			assertEquals(c.getY(),engine.getGuards().get(i).getHgt());
		}
		checkinv();
	}
	
	@Test
	public void transitionStep5() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		guards.add(new Coord(10,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.DIGL);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		ArrayList<Coord> guards_capture = engine.getGuardsCoord();
		//Opération(s)
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		assertEquals(Cell.HOL,engine.getEnvi().getCellNature(wdt_capture-1, hgt_capture-1));
		for(int i = 0;i<guards_capture.size();i++) {
			Coord c = guards_capture.get(i);
			assertNotEquals(c.getX(),engine.getGuards().get(i).getWdt());
			assertEquals(c.getY(),engine.getGuards().get(i).getHgt());
		}
		checkinv();
	}
	
	@Test
	public void transitionStep6() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		guards.add(new Coord(10,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.DIGR);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		ArrayList<Coord> guards_capture = engine.getGuardsCoord();
		//Opération(s)
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		assertEquals(Cell.HOL,engine.getEnvi().getCellNature(wdt_capture+1, hgt_capture-1));
		for(int i = 0;i<guards_capture.size();i++) {
			Coord c = guards_capture.get(i);
			assertNotEquals(c.getX(),engine.getGuards().get(i).getWdt());
			assertEquals(c.getY(),engine.getGuards().get(i).getHgt());
		}
		checkinv();
	}
	
	/**
	* ETATS REMARQUABLES
	*/
	
	@Test
	public void etatRemarquableGagnant() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		guards.add(new Coord(10,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.LEFT);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		ArrayList<Coord> guards_capture = engine.getGuardsCoord();
		//Opération(s)
		engine.step();
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture-2,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		assertEquals(0,engine.getTreasures().size());
		assertEquals(GameState.Win,engine.getStatus());
		for(int i = 0;i<guards_capture.size();i++) {
			Coord c = guards_capture.get(i);
			assertNotEquals(c.getX(),engine.getGuards().get(i).getWdt());
			assertEquals(c.getY(),engine.getGuards().get(i).getHgt());
		}
		checkinv();
	}
	
	@Test
	public void etatRemarquablePerdant() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(3,2), guards, treasures);
		engine.addCommand(Command.RIGHT);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		int guard_wdt_capture = engine.getGuards().get(0).getWdt();
		int guard_hgt_capture = engine.getGuards().get(0).getHgt();
		//Opération(s)
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture+1,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		assertEquals(2,engine.getTreasures().size());
		assertEquals(GameState.Loss,engine.getStatus());
		assertEquals(guard_wdt_capture-1,engine.getGuards().get(0).getWdt());
		assertEquals(guard_hgt_capture,engine.getGuards().get(0).getHgt());
		checkinv();
	}
	/**
	*  PAIRES DE TRANSITIONS
	*/
	
	@Test
	public void paireStepStep() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.RIGHT);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		int guard_wdt_capture = engine.getGuards().get(0).getWdt();
		int guard_hgt_capture = engine.getGuards().get(0).getHgt();
		//Opération(s)
		engine.step();
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture+2,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		assertEquals(2,engine.getTreasures().size());
		assertEquals(GameState.Loss,engine.getStatus());
		assertEquals(guard_wdt_capture-1,engine.getGuards().get(0).getWdt());
		assertEquals(guard_hgt_capture,engine.getGuards().get(0).getHgt());
		checkinv();
	}
	
	@Test
	public void paireStepStep2() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(3,2), guards, treasures);
		engine.addCommand(Command.UP);
		engine.addCommand(Command.NEUTRAL);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		int guard_wdt_capture = engine.getGuards().get(0).getWdt();
		int guard_hgt_capture = engine.getGuards().get(0).getHgt();
		//Opération(s)
		engine.step();
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		assertEquals(2,engine.getTreasures().size());
		assertEquals(GameState.Loss,engine.getStatus());
		assertEquals(guard_wdt_capture-2,engine.getGuards().get(0).getWdt());
		assertEquals(guard_hgt_capture,engine.getGuards().get(0).getHgt());
		checkinv();
	}
	
	@Test
	public void paireStepStep3() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(5,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.NEUTRAL);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		int guard_wdt_capture = engine.getGuards().get(0).getWdt();
		int guard_hgt_capture = engine.getGuards().get(0).getHgt();
		//Opération(s)
		engine.step();
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture-1,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		assertEquals(1,engine.getTreasures().size());
		assertEquals(GameState.Playing,engine.getStatus());
		assertEquals(guard_wdt_capture-2,engine.getGuards().get(0).getWdt());
		assertEquals(guard_hgt_capture,engine.getGuards().get(0).getHgt());
		checkinv();
	}
	
	@Test
	public void paireStepStep4() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(4,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.DIGR);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		int guard_wdt_capture = engine.getGuards().get(0).getWdt();
		int guard_hgt_capture = engine.getGuards().get(0).getHgt();
		//Opération(s)
		engine.step();
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		assertEquals(2,engine.getTreasures().size());
		assertEquals(GameState.Playing,engine.getStatus());
		assertEquals(guard_wdt_capture-1,engine.getGuards().get(0).getWdt());
		assertEquals(guard_hgt_capture-1,engine.getGuards().get(0).getHgt());
		assertEquals(Cell.HOL,engine.getEnvi().getCellNature(engine.getGuards().get(0).getWdt(), engine.getGuards().get(0).getHgt()));
		checkinv();
	}
	/**
	*  SCENARIO
	*/
	
	@Test
	public void scenario() {
		//Conditions Initiales 
		ArrayList<Coord> guards = new ArrayList<>();
		guards.add(new Coord(14,2));
		ArrayList<Item> treasures = new ArrayList<>();
		treasures.add(new Item(0,2,ItemType.Treasure));
		treasures.add(new Item(1,2,ItemType.Treasure));
		es.setNature(3, 2, Cell.LAD);
		es.setNature(3, 3, Cell.LAD);
		es.setNature(2, 3, Cell.PLT);
		es.setNature(1, 3, Cell.PLT);
		engine.init(es, new Coord(2,2), guards, treasures);
		engine.addCommand(Command.RIGHT);
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.NEUTRAL);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.LEFT);
		engine.addCommand(Command.UP);
		engine.addCommand(Command.UP);
		engine.addCommand(Command.DIGR);
		engine.addCommand(Command.RIGHT);
		int wdt_capture = engine.getPlayer().getWdt();
		int hgt_capture = engine.getPlayer().getHgt();
		int guard_wdt_capture = engine.getGuards().get(0).getWdt();
		int guard_hgt_capture = engine.getGuards().get(0).getHgt();
		//Opération(s)
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		engine.step();
		//Oracle : 
		assertEquals(wdt_capture-1,engine.getPlayer().getWdt());
		assertEquals(hgt_capture,engine.getPlayer().getHgt());
		assertEquals(0,engine.getTreasures().size());
		assertEquals(GameState.Win,engine.getStatus());
		assertEquals(guard_wdt_capture-10,engine.getGuards().get(0).getWdt());//le fait de monter rend le garde a faire neutral
		assertEquals(guard_hgt_capture-1,engine.getGuards().get(0).getHgt());
		assertEquals(Cell.HOL,engine.getEnvi().getCellNature(engine.getGuards().get(0).getWdt(), engine.getGuards().get(0).getHgt()));
		checkinv();
	}
	
}

