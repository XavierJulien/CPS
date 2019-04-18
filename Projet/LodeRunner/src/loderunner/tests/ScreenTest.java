package loderunner.tests;


import loderunner.contracts.ScreenContract;
import loderunner.impl.ScreenImpl;
import loderunner.services.ScreenService;

public class ScreenTest {
	private ScreenService screen;
	
	public ScreenTest() {
		screen = null;
	}
	
	public void beforeTests() {
		screen = new ScreenContract(new ScreenImpl());

	}
	
	/**
	 * PRECONDITIONS
	 **/
	
	/**
	 * TRANSITIONS
	 **/
	
	/**
	* ETATS REMARQUABLES
	*/
	
	/**
	*  PAIRES DE TRANSITIONS
	*/
	
	
}
