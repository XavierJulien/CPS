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
		if(!cell_check.getCharacter().equals(engine.getPlayer())) throw new InvariantError("checkInvariants : Le player aux position du player n'est pas le player");
		for(GuardService g : engine.getGuards()) {
			cell_check = engine.getEnvi().getCellContent(g.getWdt(), g.getHgt());
			if(!cell_check.getGuard().equals(g)) throw new InvariantError("checkInvariants : Le guard aux position du guard n'est pas le guard");
			for(Item t : engine.getTreasures()) {
				cell_check = engine.getEnvi().getCellContent(t.getCol(), t.getHgt());
				assertTrue(cell_check.getItem() != null && t.getCol() == g.getWdt() && t.getHgt() == g.getHgt() && !g.hasItem());
			}
			
		}
		for(Item t : engine.getTreasures()) {
			cell_check = engine.getEnvi().getCellContent(t.getCol(), t.getHgt());
			for(GuardService g : engine.getGuards()) {
				assertTrue(cell_check.getItem() == null && (t.getCol() != g.getWdt() || t.getHgt() != g.getHgt()));
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
	}
	
	/**
	* ETATS REMARQUABLES
	*/
	
	
	
	/**
	*  PAIRES DE TRANSITIONS
	*/
	
}

/*
assertEquals(engine.getPlayer().getWdt(),2);
assertEquals(engine.getPlayer().getHgt(),2);
assertEquals(engine.getGuards().get(0).getHgt(),2);
assertEquals(engine.getGuards().get(0).getWdt(),5);
assertEquals(engine.getGuards().get(1).getHgt(),2);
assertEquals(engine.getGuards().get(1).getWdt(),10);
assertEquals(engine.getTreasures().get(0).getHgt(),2);
assertEquals(engine.getTreasures().get(0).getCol(),0);
assertEquals(engine.getTreasures().get(1).getHgt(),2);
assertEquals(engine.getTreasures().get(1).getCol(),1);
assertEquals(engine.getPlayer().getEngine(),engine.getDelegate());
checkinv();
*/
