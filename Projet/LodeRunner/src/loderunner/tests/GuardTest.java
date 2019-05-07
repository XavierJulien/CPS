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
import loderunner.contracts.GuardContract;
import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EngineImpl;
import loderunner.impl.GuardImpl;
import loderunner.services.EngineService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class GuardTest extends CharacterTest {
	
	private GuardService guard;
	private PlayerService player;
	private EngineService engine;
	
	@Before
	public void beforeTests() {
		es = new EditableScreenContract(new EditableScreenImpl());
		engine = new EngineContract(new EngineImpl());
		guard = new GuardContract(new GuardImpl(-1));
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
		guard = engine.getGuards().get(0);
		c = (CharacterContract)guard;
	}
	
	@After
	public void afterTests() {
		envi = null;
		engine = null;
		c = null;
		es = null;
	}
	
	public void checkinvG() {
		Cell nat = guard.getEnvi().getCellNature(guard.getWdt(), guard.getHgt());
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
	
	@Test(expected = PreconditionError.class) // echec car la case contient deja un character
	public void preInitFail() {
		//Conditions Initiales : None
		//Opération :
		guard.init(engine, 2, 2, player);
		//Oracle : error exists character 
	}
	
	@Test(expected = PreconditionError.class) // echec car la case n'est pas EMP
	public void preInitFail2() {
		//Conditions Initiales : None
		//Opération :
		guard.init(engine, 2, 1, player);
		//Oracle : error Cell not EMP
	}
	
	@Test
	public void preClimbLeftPass() {
		//Conditions Initiales : 
		engine.getEnvi().dig(8, 1);
		engine.addCommand(Command.NEUTRAL);
		//guard = engine.getGuards().get(0); déja fait dans le before test
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
	
	@Test(expected = PreconditionError.class) // echec car pas dans un trou
	public void preClimbLeftFail() {
		//Conditions Initiales : 
		//Opération :
		guard.climbLeft();
		//Oracle : not in HOL
	}
	
	@Test(expected = PreconditionError.class) // echec car pas dans un trou
	public void preClimbRightFail() {
		//Conditions Initiales : 
		//Opération :
		guard.climbRight();
		//Oracle : not in HOL
	}
	
	
	
	/**
	 * TRANSITIONS
	 **/
	@Test
	public void transitionInit() {
		//Conditions Initiales : 
		//Opération :
		guard.init(engine,8,2,player);
		//Oracle :
		/*post*/
		assertEquals(player, guard.getTarget());
		assertTrue(guard.getTimeInHole()==0);
		/*inv*/
		checkinvG();
	}
	
	@Test
	public void transitionClimbLeft() {
		//Conditions Initiales : 
		engine.getEnvi().dig(8, 1);
		for (int i=0; i<11;i++) {
			engine.addCommand(Command.NEUTRAL);
			engine.step();
		}
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();
		//Opération :
		guard.climbLeft();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture-1);
		assertTrue(guard.getHgt() == hgt_capture+1);
		/*inv*/
		checkinvG();
	}
	
	@Test
	public void transitionClimbRight() {
		//Conditions Initiales : 
		ArrayList<Coord> g_list = new ArrayList<>();
		ArrayList<Item> item_list = new ArrayList<>();
		g_list.add(new Coord(8, 2));
		item_list.add(new Item(14,2,ItemType.Treasure));
		engine.init(es, new Coord(12,2), g_list, item_list);
		envi = engine.getEnvi();
		player = engine.getPlayer();
		guard = engine.getGuards().get(0);
		c = (CharacterContract)guard;

		engine.getEnvi().dig(8, 1);
		for (int i=0; i<11;i++) {
			engine.addCommand(Command.NEUTRAL);
			engine.step();
		}
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();
		//Opération :
		guard.climbRight();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture+1);
		assertTrue(guard.getHgt() == hgt_capture+1);
		/*inv*/
		checkinvG();
	}
	
	@Test
	public void transitionStep1() { // willfall
		//Conditions initiales:
		engine.getEnvi().dig(8, 1);
		
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();
		//Operation:
		guard.step();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture);
		assertTrue(guard.getHgt() == hgt_capture-1);
		/*inv*/
		checkinvG();
	}
	
	@Test
	public void transitionStep2() { // willwaitinhole
		//Conditions initiales:
		engine.getEnvi().dig(8, 1);
		engine.addCommand(Command.NEUTRAL);
		engine.step();
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();
		int time_capture = guard.getTimeInHole();
		//Operation:
		guard.step();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture);
		assertTrue(guard.getHgt() == hgt_capture);
		assertTrue(guard.getTimeInHole() == time_capture+1);
		/*inv*/
		checkinvG();
	}
	
	
	@Test
	public void transitionStep3() { // willclimbleft
		//Conditions initiales
		engine.getEnvi().dig(8, 1);
		for (int i=0; i<11;i++) {
			engine.addCommand(Command.NEUTRAL);
			engine.step();
		}
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();
		//Opération :
		guard.step();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture-1);
		assertTrue(guard.getHgt() == hgt_capture+1);
		assertTrue(guard.getTimeInHole() == 0);
		/*inv*/
		checkinvG();
	}
	
	@Test
	public void transitionStep4() { // willclimbright
		//Conditions initiales
		ArrayList<Coord> g_list = new ArrayList<>();
		ArrayList<Item> item_list = new ArrayList<>();
		g_list.add(new Coord(8, 2));
		item_list.add(new Item(14,2,ItemType.Treasure));
		engine.init(es, new Coord(12,2), g_list, item_list);
		envi = engine.getEnvi();
		player = engine.getPlayer();
		guard = engine.getGuards().get(0);
		c = (CharacterContract)guard;

		engine.getEnvi().dig(8, 1);
		for (int i=0; i<11;i++) {
			engine.addCommand(Command.NEUTRAL);
			engine.step();
		}
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();
		//Opération :
		guard.step();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture+1);
		assertTrue(guard.getHgt() == hgt_capture+1);
		assertTrue(guard.getTimeInHole() == 0);
		/*inv*/
		checkinvG();
	}
	
	@Test
	public void transitionStep5() { // willclimbneutral
		
	}
	
	@Test
	public void transitionStep6() { // willfollowbehaviour -> left
		//Conditions initiales : none
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();
		System.out.println(guard.getWdt());
		System.out.println(guard.getHgt());
		//Opération :
		guard.step();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture-1);
		assertTrue(guard.getHgt() == hgt_capture);
		assertTrue(guard.getTimeInHole() == 0);
		/*inv*/
		checkinvG();
	}
	
	@Test
	public void transitionStep7() { // willfollowbehaviour -> right
		//Conditions initiales :
		ArrayList<Coord> g_list = new ArrayList<>();
		ArrayList<Item> item_list = new ArrayList<>();
		g_list.add(new Coord(8, 2));
		item_list.add(new Item(14,2,ItemType.Treasure));
		engine.init(es, new Coord(12,2), g_list, item_list);
		envi = engine.getEnvi();
		player = engine.getPlayer();
		guard = engine.getGuards().get(0);
		c = (CharacterContract)guard;
		
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();
		//Opération :
		guard.step();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture+1);
		assertTrue(guard.getHgt() == hgt_capture);
		assertTrue(guard.getTimeInHole() == 0);
		/*inv*/
		checkinvG();
	}
	
	@Test
	public void transitionStep8() { // willfollowbehaviour -> up
		//Conditions initiales :
		es.setNature(2, 2, Cell.LAD);
		es.setNature(2, 3, Cell.LAD);
		es.setNature(2, 4, Cell.LAD);
		ArrayList<Coord> g_list = new ArrayList<>();
		ArrayList<Item> item_list = new ArrayList<>();
		g_list.add(new Coord(0, 2));
		item_list.add(new Item(14,2,ItemType.Treasure));
		engine.init(es, new Coord(3,2), g_list, item_list);
		envi = engine.getEnvi();
		player = engine.getPlayer();
		guard = engine.getGuards().get(0);
		c = (CharacterContract)guard;
		engine.addCommand(Command.UP);
		engine.addCommand(Command.UP);
		engine.addCommand(Command.LEFT);
		engine.step();
		engine.step();
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();

		//Opération :
		guard.step();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture);
		assertTrue(guard.getHgt() == hgt_capture+1);
		assertTrue(guard.getTimeInHole() == 0);
		/*inv*/
		checkinvG();
	}
	
	@Test
	public void transitionStep9() { // willfollowbehaviour -> down
		//Conditions initiales :
		es.setNature(2, 2, Cell.LAD);
		es.setNature(2, 3, Cell.LAD);
		es.setNature(2, 4, Cell.LAD);
		ArrayList<Coord> g_list = new ArrayList<>();
		ArrayList<Item> item_list = new ArrayList<>();
		g_list.add(new Coord(2,5));
		item_list.add(new Item(14,2,ItemType.Treasure));
		engine.init(es, new Coord(3,2), g_list, item_list);
		envi = engine.getEnvi();
		player = engine.getPlayer();
		guard = engine.getGuards().get(0);
		c = (CharacterContract)guard;
		engine.addCommand(Command.LEFT);
		engine.step();
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();

		//Opération :
		guard.step();
		//Oracle :
		/*post*/
		assertTrue(guard.getWdt() == wdt_capture);
		assertTrue(guard.getHgt() == hgt_capture-1);
		assertTrue(guard.getTimeInHole() == 0);
		/*inv*/
		checkinvG();
	}
	
	/**
	* ETATS REMARQUABLES
	*/
	// le garde recupère un trésor 
	@Test
	public void etatRemarquableRecupTresor() {
		//Conditions initiales
		ArrayList<Coord> g_list = new ArrayList<>();
		ArrayList<Item> item_list = new ArrayList<>();
		g_list.add(new Coord(8, 2));
		item_list.add(new Item(7,2,ItemType.Treasure));
		engine.init(es, new Coord(2,2), g_list, item_list);
		envi = engine.getEnvi();
		player = engine.getPlayer();
		guard = engine.getGuards().get(0);
		c = (CharacterContract)guard;
		System.out.println("guard: x="+guard.getWdt()+" y="+guard.getHgt());
		//System.out.println("est ce qu'il y a un item dans (8,2)? : "+engine.getTreasures().get(0).get);
		int wdt_capture = guard.getWdt();
		int hgt_capture = guard.getHgt();
		//Opération :
		guard.step();
		//Oracle :
		/*post*/
		System.out.println("apres step guard: x="+guard.getWdt()+" y="+guard.getHgt());
		assertTrue(guard.getWdt() == wdt_capture-1);
		assertTrue(guard.getHgt() == hgt_capture);
		assertTrue(guard.getTimeInHole() == 0);
		assertTrue(guard.hasItem());
		/*inv*/
		checkinvG();
	}
	
	// le garde croise un trésor alors qu'il en a deja un sur lui
	// le garde croise le joueur 
	// le garde croise un autre garde
	// le garde portant un trésor tombe dans un trou
	
	// le garde arrive sur une plateforme de teleportation
	
	/**
	*  PAIRES DE TRANSITIONS
	*/
	// goleft goright goup godown
	// step climbleft climbright 
		
}
