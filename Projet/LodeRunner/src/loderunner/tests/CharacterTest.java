package loderunner.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.CharacterContract;
import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EnvironnementContract;
import loderunner.data.Cell;
import loderunner.errors.PreconditionError;
import loderunner.impl.CharacterImpl;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EnvironnementImpl;
import loderunner.services.CharacterService;
import loderunner.services.EnvironnementService;

public class CharacterTest {
		
	private CharacterService c;
	private EnvironnementService envi;
	

	@Before
	public void beforeTests() {
		c = new CharacterContract(new CharacterImpl());
		envi = new EnvironnementContract(new EnvironnementImpl());
	}
	
	@After
	public final void afterTests() {
		c = null;
		envi = null;
	}
	
	public void checkinv() {
		assertTrue(c.getEnvi().getCellNature(c.getWdt(), c.getHgt()) == Cell.EMP
				|| c.getEnvi().getCellNature(c.getWdt(), c.getHgt()) == Cell.HOL
				|| c.getEnvi().getCellNature(c.getWdt(), c.getHgt()) == Cell.LAD
				|| c.getEnvi().getCellNature(c.getWdt(), c.getHgt()) == Cell.HDR);
		if (c.getEnvi().getCellContent(c.getWdt(), c.getHgt()).getCharacter() != null)
			assertEquals(c, c.getEnvi().getCellContent(c.getWdt(), c.getHgt()).getCharacter());
		if (c.getEnvi().getCellContent(c.getWdt(), c.getHgt()).getGuard() != null)
			assertEquals(c, c.getEnvi().getCellContent(c.getWdt(), c.getHgt()).getGuard());
	}
	
	/**
	 * PRECONDITIONS
	 **/
	@Test
	public void preInitPass() {
		//Conditions Initiales :
		EditableScreenContract es = new EditableScreenContract(new EditableScreenImpl());
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		//Opération(s)
		c.init(envi,5,1,-1);
		//Oracle : None
	}
	
	@Test(expected = PreconditionError.class)
	public void preInitFail() {
		//Conditions Initiales :
		EditableScreenContract es = new EditableScreenContract(new EditableScreenImpl());
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		//Opération(s)
		c.init(envi,-1,10,-1);
		//Oracle : error
	}
	
	@Test(expected = PreconditionError.class)
	public void preInitFail2() {
		//Conditions Initiales :
		EditableScreenContract es = new EditableScreenContract(new EditableScreenImpl());
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		//Opération(s)
		c.init(envi,5,5,-1);
		//Oracle : error
	}
	
	/**
	 * TRANSITIONS
	 **/
	@Test
	public void transitionInit() {
		//CI :
		EditableScreenContract es = new EditableScreenContract(new EditableScreenImpl());
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		//OP :
		c.init(envi, 5, 1,-1);
		//ORACLE
		/*post*/
		/*inv*/
		checkinv();
	}
	
	@Test
	public void transitionGoLeft() {
		//CI :
		EditableScreenContract es = new EditableScreenContract(new EditableScreenImpl());
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		c.init(envi, 5, 1,-1);
		int hgt_capture = c.getHgt();
		int wdt_capture = c.getWdt();
		//OP :
		c.goLeft();
		//ORACLE
		/*post*/
		Cell nat_left = c.getEnvi().getCellNature(wdt_capture-1, hgt_capture);
		Cell nat = c.getEnvi().getCellNature(wdt_capture, hgt_capture);
		Cell nat_under = c.getEnvi().getCellNature(wdt_capture, hgt_capture-1);
		assertTrue(c.getHgt() == hgt_capture);
		if (wdt_capture == 0) assertTrue(c.getWdt()==wdt_capture);
		if (nat_left == Cell.MTL || nat_left == Cell.PLT) assertTrue(c.getWdt()==wdt_capture);
		if (nat != Cell.LAD && nat != Cell.HDR) {
			if (nat_under != Cell.PLT && nat_under != Cell.MTL && nat_under != Cell.LAD) {
				if (c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() == null 
						|| c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() == null) {
					assertTrue(c.getWdt() == wdt_capture);
				}
			}
		}
		if (c.getEnvi().getCellContent(wdt_capture-1, hgt_capture).getCharacter() != null ||
				c.getEnvi().getCellContent(wdt_capture-1, hgt_capture).getGuard() != null) { 
			assertTrue(c.getWdt() == wdt_capture);
		}
		if(wdt_capture != 0) {
			if(nat_left != Cell.MTL && nat_left != Cell.PLT) {
				if((nat == Cell.LAD || nat == Cell.HDR)
				   ||
				   (nat_under == Cell.PLT || nat_under == Cell.MTL || nat_under == Cell.LAD)
				   ||
				   (c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() != null
				   || c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() != null)) {
					  if(c.getEnvi().getCellContent(wdt_capture-1, hgt_capture).getCharacter() == null
							  && c.getEnvi().getCellContent(wdt_capture-1, hgt_capture).getGuard() == null) {
						  assertTrue(c.getWdt() == wdt_capture-1);
					  }	
				}
			}
		}
		/*inv*/
		checkinv();
	}
	
	@Test
	public void transitionGoRight() {
		//CI :
		EditableScreenContract es = new EditableScreenContract(new EditableScreenImpl());
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		c.init(envi, 5, 1,-1);
		int hgt_capture = c.getHgt();
		int wdt_capture = c.getWdt();
		//OP :
		c.goRight();
		//ORACLE
		/*post*/
		Cell nat_right = c.getEnvi().getCellNature(wdt_capture+1, hgt_capture);
		Cell nat = c.getEnvi().getCellNature(wdt_capture, hgt_capture);
		Cell nat_under = c.getEnvi().getCellNature(wdt_capture, hgt_capture-1);
		assertTrue(c.getHgt() == hgt_capture);
		if (wdt_capture == c.getEnvi().getWidth()) assertTrue(c.getWdt()==wdt_capture);
		if (nat_right == Cell.MTL || nat_right == Cell.PLT) assertTrue(c.getWdt()==wdt_capture);
		if (nat != Cell.LAD && nat != Cell.HDR) {
			if (nat_under != Cell.PLT && nat_under != Cell.MTL && nat_under != Cell.LAD) {
				if (c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() == null 
						|| c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() == null) {
					assertTrue(c.getWdt() == wdt_capture);
				}
			}
		}
		if (c.getEnvi().getCellContent(wdt_capture+1, hgt_capture).getCharacter() != null ||
				c.getEnvi().getCellContent(wdt_capture+1, hgt_capture).getGuard() != null) { 
			assertTrue(c.getWdt() == wdt_capture);
		}
		if(wdt_capture != 0) {
			if(nat_right != Cell.MTL && nat_right != Cell.PLT) {
				if((nat == Cell.LAD || nat == Cell.HDR)
				   ||
				   (nat_under == Cell.PLT || nat_under == Cell.MTL || nat_under == Cell.LAD)
				   ||
				   (c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() != null
				   || c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() != null)) {
					  if(c.getEnvi().getCellContent(wdt_capture+1, hgt_capture).getCharacter() == null
							  && c.getEnvi().getCellContent(wdt_capture+1, hgt_capture).getGuard() == null) {
						  assertTrue(c.getWdt() == wdt_capture+1);
					  }	
				}
			}
		}
		/*inv*/
		checkinv();
	}
	
	@Test
	public void transitionGoUp() {
		//CI :
		EditableScreenContract es = new EditableScreenContract(new EditableScreenImpl());
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		c.init(envi, 5, 1,-1);	
		int hgt_capture = c.getHgt();
		int wdt_capture = c.getWdt();
		//OP :
		c.goUp();
		//ORACLE
		/*post*/
		Cell nat_up = c.getEnvi().getCellNature(wdt_capture, hgt_capture+1);
		Cell nat = c.getEnvi().getCellNature(wdt_capture, hgt_capture);
		assertTrue(c.getWdt() == wdt_capture);
		if (hgt_capture == c.getEnvi().getHeight()-1) assertTrue(c.getHgt() == hgt_capture);
		if (nat != Cell.LAD) { assertTrue(c.getHgt() == hgt_capture);
		}else {
			if (c.getEnvi().getCellContent(wdt_capture, hgt_capture).getCharacter() == null
				&& 
				c.getEnvi().getCellContent(wdt_capture, hgt_capture+1).getGuard() == null
			    &&
			    nat_up != Cell.MTL
			    &&
			    nat_up != Cell.PLT
			    &&
			    nat_up != Cell.HOL) {
				assertTrue(c.getHgt() == hgt_capture+1);
			}
		}
		/*inv*/
		checkinv();
	}
	
	@Test
	public void transitionGoDown() {
		//CI :
		EditableScreenContract es = new EditableScreenContract(new EditableScreenImpl());
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		c.init(envi, 5, 1,-1);	
		int hgt_capture = c.getHgt();
		int wdt_capture = c.getWdt();
		//OP :
		c.goDown();
		//ORACLE
		Cell nat_under = c.getEnvi().getCellNature(wdt_capture, hgt_capture-1);
		/*post*/
		assertTrue(c.getWdt() == wdt_capture);
		if (hgt_capture == 1) {//peut etre pas utile car on le verifie en dessous avec le metal
			assertTrue(c.getHgt() == hgt_capture);
			return;
		}
		if(nat_under == Cell.PLT || nat_under == Cell.MTL) {
			assertTrue(c.getHgt() == hgt_capture);
		}
		if(c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() != null || 
		   c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() != null) {
			assertTrue(c.getHgt() == hgt_capture);
		}
		if(hgt_capture != 1) {
			if(nat_under != Cell.MTL 
			   && nat_under != Cell.PLT 
			   && c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() == null && 
			   c.getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() == null) {
				assertTrue(c.getHgt() == hgt_capture-1);;
			}
		}
		/*inv*/
		checkinv();
	}
	
	
	
	/**
	* ETATS REMARQUABLES
	*/
	
	
	/**
	*  PAIRES DE TRANSITIONS
	*/
	
		



}
