package loderunner.tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.EditableScreenContract;
import loderunner.data.Cell;
import loderunner.errors.PreconditionError;
import loderunner.impl.EditableScreenImpl;
import loderunner.services.EditableScreenService;

public class EditableScreenTest {

	private EditableScreenService es;
	
	public EditableScreenTest() {
		es = null;
	}
	
	@Before
	public void beforeTests() {
		es = new EditableScreenContract(new EditableScreenImpl());
	}
	
	@After
	public final void afterTests() {
		es = null;
	}
	
	
	public void checkinv() {
		boolean ok_es = true;
		for (int i=0;i<es.getWidth();i++) {
			for (int j=0; j<es.getHeight();j++) {
				if (j==0) {
					if (es.getCellNature(i, j) != Cell.MTL)
						ok_es = false;
				}else {
					if (es.getCellNature(i, j) == Cell.HOL)
						ok_es = false;
				}
			}	
		}
		assertTrue(es.isPlayable() == ok_es);
	}
	
	/**
	 * PRECONDITIONS
	 **/
	/*@Test
	public void preInitPass() {
		//Conditions Initiales : None
		//Opération(s)
		//Oracle : None
	}
	
	@Test(expected = PreconditionError.class)
	public void preInitFail() {
		//Conditions Initiale : None
		//Opération(s)
		//Oracle : None
	}*/
	
	public void preSetNaturePass() {
		//Conditions Initiales :
		es.init(15, 10);
		//Opération(s)
		es.setNature(0, 0, Cell.MTL);
		//Oracle : None
	}
	
	@Test(expected = PreconditionError.class)
	public void preSetNatureFail() {
		//Conditions Initiales : None
		es.init(15, 10);
		//Opération(s)
		es.setNature(15, 10, Cell.MTL);
		//Oracle : Precondition error expected
		
	}

	
	/**
	 * TRANSITIONS
	 **/
	@Test
	public void transitionSetNature() {
		//CI :
		es.init(15,10);
		//OP :
		Cell[][] natures_atpre = new Cell[15][10];
		for (int i=0;i<es.getWidth();i++) {
			for (int j=0; j<es.getHeight();j++) {
				natures_atpre[i][j] = es.getCellNature(i, j);
			}
		}
		es.setNature(0, 0, Cell.MTL);
		//ORACLE
		/*post*/
		for (int i=0;i<es.getWidth();i++) {
			for (int j=0; j<es.getHeight();j++) {
				if (i==0 && j==0)
					assertTrue(es.getCellNature(i,j) == Cell.MTL);
				else 
					assertTrue(es.getCellNature(i,j) == natures_atpre[i][j]);
			}
		}
		/*inv*/
		checkinv();
		
	}
	
	// De même pour les transitions Dig et Fill
	
	/**
	* ETATS REMARQUABLES
	*/
	
	/**
	*  PAIRES DE TRANSITIONS
	*/
	
}
