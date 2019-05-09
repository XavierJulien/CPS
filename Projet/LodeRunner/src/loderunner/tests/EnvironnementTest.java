package loderunner.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EnvironnementContract;
import loderunner.data.Cell;
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.impl.EnvironnementImpl;
import loderunner.services.CharacterService;
import loderunner.services.EditableScreenService;
import loderunner.services.EnvironnementService;

public class EnvironnementTest {
	
	private EnvironnementService envi;
	private EditableScreenService es;
	
	@Before
	public void beforeTests() {
		envi = new EnvironnementContract(new EnvironnementImpl());
		es = new EditableScreenContract(new EditableScreenImpl());
	}
	
	@After
	public final void afterTests() {
		envi = null;
		es = null;
	}
	
	public void checkinv() {
		for(int i=0;i<envi.getWidth();i++) {
			for(int j=0;j<envi.getHeight();j++) {
				CharacterService c1 = envi.getCellContent(i, j).getCharacter();
				CharacterService c2 = envi.getCellContent(i, j).getCharacter();
				assertEquals(c1, c2);
				if(envi.getCellNature(i,j) == Cell.MTL || envi.getCellNature(i,j) == Cell.PLT) {
					assertEquals(envi.getCellContent(i,j).getCharacter(),null);
					assertEquals(envi.getCellContent(i, j).getItem(),null);
				}
				if(envi.getCellContent(i, j).getItem() != null) {
					assertTrue((envi.getCellNature(i, j) == Cell.EMP) 
							&& (envi.getCellNature(i, j-1) == Cell.MTL || envi.getCellNature(i, j-1) == Cell.PLT));
				}
			}
		}
	}
	
	/**
	 * PRECONDITIONS
	 **/
	
	//RIEN
	
	/**
	 * TRANSITIONS
	 **/
	@Test
	public void transitionInit() {
		//CI :
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		//OP :
		envi.init(es);
		//ORACLE
		/*post*/
		for (int i=0; i<es.getWidth();i++) {
			for (int j=0; j<es.getHeight();j++) {
				envi.getCellNature(i, j);
				assertEquals(envi.getCellNature(i, j), es.getCellNature(i, j));
			}
		}
		/*inv*/
		checkinv();
	}
	
	
	@Test
	public void getCellContentPass() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		//OP
		envi.getCellContent(0, 0);
		//ORACLE
	}
	
	@Test(expected=PreconditionError.class)
	public void getCellContentFail() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		//OP
		envi.getCellContent(-1, 0);
		//ORACLE : ERROR
	}
	
	@Test(expected=PreconditionError.class)
	public void getCellContentFail2() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
		}
		envi.init(es);
		//OP
		envi.getCellContent(0, -1);
		//ORACLE : ERROR
	}
	
	@Test
	public void DigPass() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
			es.setNature(i, 1, Cell.PLT);
		}
		envi.init(es);
		Cell[][] dig_pre_all = new Cell[envi.getWidth()][envi.getHeight()];
		for (int i = 0; i < envi.getWidth(); i++) {
			for (int j = 0; j < envi.getHeight(); j++) {
				dig_pre_all[i][j] = envi.getCellNature(i, j);
			}
		}
		Cell dig_pre = envi.getCellNature(3, 1);
		assertEquals(dig_pre,Cell.PLT);
		//OP
		envi.dig(3, 1);
		//ORACLE 
		for (int i = 0; i < envi.getWidth(); i++) {
			assertTrue(envi.getCellNature(1, 0) == dig_pre_all[i][0]);
			for (int j = 1; j < envi.getHeight(); j++) {
				if(j == 1) {
					if(i == 3) {
						assertTrue(envi.getCellNature(i, j) == Cell.HOL);
						assertTrue(envi.getCellNature(i, j) != dig_pre_all[i][j]);
					}else {				
						assertTrue(envi.getCellNature(i, j) == dig_pre_all[i][j]);
					}
				}else {
					assertTrue(envi.getCellNature(i, j) == dig_pre_all[i][j]);
				}
				
			}
		}
		checkinv();
	}
	
	@Test(expected=PreconditionError.class)
	public void DigFail1() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
			es.setNature(i, 1, Cell.PLT);
		}
		envi.init(es);
		Cell[][] dig_pre_all = new Cell[envi.getWidth()][envi.getHeight()];
		for (int i = 0; i < envi.getWidth(); i++) {
			for (int j = 0; j < envi.getHeight(); j++) {
				dig_pre_all[i][j] = envi.getCellNature(i, j);
			}
		}
		Cell dig_pre = envi.getCellNature(1, 1);
		assertEquals(dig_pre,Cell.PLT);
		//OP
		envi.dig(-1, 1);
		//ORACLE : ERROR
	}
	
	@Test(expected=PreconditionError.class)
	public void DigFail2() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
			es.setNature(i, 1, Cell.PLT);
		}
		envi.init(es);
		Cell[][] dig_pre_all = new Cell[envi.getWidth()][envi.getHeight()];
		for (int i = 0; i < envi.getWidth(); i++) {
			for (int j = 0; j < envi.getHeight(); j++) {
				dig_pre_all[i][j] = envi.getCellNature(i, j);
			}
		}
		Cell dig_pre = envi.getCellNature(1, 1);
		assertEquals(dig_pre,Cell.PLT);
		//OP
		envi.dig(3, 0);
		//ORACLE : ERROR
	}
	
	@Test
	public void FillPass() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
			if(i == 3) {
				es.setNature(i, 1, Cell.HOL);
			}else {
				es.setNature(i, 1, Cell.PLT);
			}
			
		}
		envi.init(es);
		Cell[][] fill_pre_all = new Cell[envi.getWidth()][envi.getHeight()];
		for (int i = 0; i < envi.getWidth(); i++) {
			for (int j = 0; j < envi.getHeight(); j++) {
				fill_pre_all[i][j] = envi.getCellNature(i, j);
			}
		}
		
		Cell fill_pre = envi.getCellNature(3, 1);
		assertEquals(fill_pre,Cell.HOL);
		//OP
		envi.fill(3, 1);
		//ORACLE 
		for (int i = 0; i < envi.getWidth(); i++) {
			assertTrue(envi.getCellNature(1, 0) == fill_pre_all[i][0]);
			for (int j = 1; j < envi.getHeight(); j++) {
				if(j == 1) {
					if(i == 3) {
						assertTrue(envi.getCellNature(i, j) == Cell.PLT);
						assertTrue(envi.getCellNature(i, j) != fill_pre_all[i][j]);
					}else {				
						assertTrue(envi.getCellNature(i, j) == fill_pre_all[i][j]);
					}
				}else {
					assertTrue(envi.getCellNature(i, j) == fill_pre_all[i][j]);
				}
				
			}
		}
		checkinv();
	}
	
	@Test(expected=PreconditionError.class)
	public void FillFail1() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
			if(i == 3) {
				es.setNature(i, 1, Cell.PLT);
			}else {
				es.setNature(i, 1, Cell.PLT);
			}
			
		}
		envi.init(es);
		Cell[][] fill_pre_all = new Cell[envi.getWidth()][envi.getHeight()];
		for (int i = 0; i < envi.getWidth(); i++) {
			for (int j = 0; j < envi.getHeight(); j++) {
				fill_pre_all[i][j] = envi.getCellNature(i, j);
			}
		}
		
		Cell fill_pre = envi.getCellNature(3, 1);
		assertEquals(fill_pre,Cell.PLT);
		//OP
		envi.fill(3, 1);
		//ORACLE : ERROR
	}
	
	@Test(expected=PreconditionError.class)
	public void FillFail2() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
			if(i == 3) {
				es.setNature(i, 1, Cell.PLT);
			}else {
				es.setNature(i, 1, Cell.PLT);
			}
			
		}
		envi.init(es);
		Cell[][] fill_pre_all = new Cell[envi.getWidth()][envi.getHeight()];
		for (int i = 0; i < envi.getWidth(); i++) {
			for (int j = 0; j < envi.getHeight(); j++) {
				fill_pre_all[i][j] = envi.getCellNature(i, j);
			}
		}
		
		Cell fill_pre = envi.getCellNature(3, 1);
		assertEquals(fill_pre,Cell.PLT);
		//OP
		envi.fill(-1, 1);
		//ORACLE : ERROR
	}
	
	//autres tests sur d'autre type de plateforme répétitifs
	
	/**
	* ETATS REMARQUABLES
	*/
	//aucunn "réel" test intéressant
	
	/**
	*  PAIRES DE TRANSITIONS
	*/
	
	@Test
	public void pairesDigFillPass() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
			if(i == 3) {
				es.setNature(i, 1, Cell.HOL);
			}else {
				es.setNature(i, 1, Cell.PLT);
			}
			
		}
		envi.init(es);
		Cell[][] fill_pre_all = new Cell[envi.getWidth()][envi.getHeight()];
		for (int i = 0; i < envi.getWidth(); i++) {
			for (int j = 0; j < envi.getHeight(); j++) {
				fill_pre_all[i][j] = envi.getCellNature(i, j);
			}
		}
		
		Cell fill_pre = envi.getCellNature(3, 1);
		assertEquals(fill_pre,Cell.HOL);
		//OP
		envi.fill(3, 1);
		envi.dig(3, 1);
		//ORACLE 
		for (int i = 0; i < envi.getWidth(); i++) {
			assertTrue(envi.getCellNature(1, 0) == fill_pre_all[i][0]);
			for (int j = 1; j < envi.getHeight(); j++) {
				if(j == 1) {
					if(i == 3) {
						assertTrue(envi.getCellNature(i, j) == fill_pre);
						assertTrue(envi.getCellNature(i, j) == fill_pre_all[i][j]);
					}else {				
						assertTrue(envi.getCellNature(i, j) == fill_pre_all[i][j]);
					}
				}else {
					assertTrue(envi.getCellNature(i, j) == fill_pre_all[i][j]);
				}
				
			}
		}
		checkinv();
	}

	@Test
	public void pairesDigFillPass2() {
		//CI
		es.init(15, 10);
		for (int i=0; i<es.getWidth(); i++) {
			es.setNature(i, 0, Cell.MTL);
			es.setNature(i, 1, Cell.PLT);
		}
		envi.init(es);
		Cell[][] dig_pre_all = new Cell[envi.getWidth()][envi.getHeight()];
		for (int i = 0; i < envi.getWidth(); i++) {
			for (int j = 0; j < envi.getHeight(); j++) {
				dig_pre_all[i][j] = envi.getCellNature(i, j);
			}
		}
		Cell dig_pre = envi.getCellNature(3, 1);
		assertEquals(dig_pre,Cell.PLT);
		//OP
		envi.dig(3, 1);
		envi.fill(3, 1);
		//ORACLE 
		for (int i = 0; i < envi.getWidth(); i++) {
			assertTrue(envi.getCellNature(1, 0) == dig_pre_all[i][0]);
			for (int j = 1; j < envi.getHeight(); j++) {
				if(j == 1) {
					if(i == 3) {
						assertTrue(envi.getCellNature(i, j) == dig_pre);
						assertTrue(envi.getCellNature(i, j) == dig_pre_all[i][j]);
					}else {				
						assertTrue(envi.getCellNature(i, j) == dig_pre_all[i][j]);
					}
				}else {
					assertTrue(envi.getCellNature(i, j) == dig_pre_all[i][j]);
				}
				
			}
		}
		checkinv();
	}
}
