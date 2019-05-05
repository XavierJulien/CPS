package loderunner.tests;


import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.ScreenContract;
import loderunner.data.Cell;
import loderunner.errors.PreconditionError;
import loderunner.impl.ScreenImpl;
import loderunner.services.ScreenService;

public class ScreenTest {

	private ScreenService screen;
	
	@Before
	public void beforeTests() {
		screen = new ScreenContract(new ScreenImpl());
	}
	
	@After
	public final void afterTests() {
		screen = null;
	}
	
	/**
	 * PRECONDITIONS
	 **/
	@Test
	public void preInitPass() {
		//Conditions Initiales : None
		//Opération(s)
		screen.init(15,10);
		//Oracle : None
	}
	
	@Test(expected = PreconditionError.class)
	public void preInitFail() {
		//Conditions Initiales : None
		//Opération(s)
		screen.init(-2,10);
		//Oracle : None
	}
	//Les tests sur les opérations Dig et Fill sont des objectifs inatteignables
	
	
	/**
	 * TRANSITIONS
	 **/
	@Test
	public void transitionInit() {
		//CI : none
		//OP :
		screen.init(15,10);
		//ORACLE
		/*post*/
		assertTrue(screen.getWidth() == 15);
		assertTrue(screen.getHeight() == 10);
		for (int i=0;i<screen.getWidth();i++) {
			for (int j=0; j<screen.getHeight();j++) {
				assertTrue(screen.getCellNature(i, j) == Cell.EMP);
			}
		}
		/*inv*/ //none
		
	}
	
	// De même pour les transitions Dig et Fill
	
	/**
	* ETATS REMARQUABLES
	*/
	
	/**
	*  PAIRES DE TRANSITIONS
	*/
	
	
}
