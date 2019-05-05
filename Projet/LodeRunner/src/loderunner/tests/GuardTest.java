package loderunner.tests;

import org.junit.After;
import org.junit.Before;

import loderunner.contracts.EngineContract;
import loderunner.contracts.EnvironnementContract;
import loderunner.impl.EngineImpl;
import loderunner.impl.EnvironnementImpl;
import loderunner.services.EngineService;
import loderunner.services.EnvironnementService;

public class GuardTest {
	
	private EnvironnementService envi;
	private EngineService engine;
	
	@Before
	public void beforeTests() {
		envi = new EnvironnementContract(new EnvironnementImpl());
		engine = new EngineContract(new EngineImpl());
	}
	
	@After
	public final void afterTests() {
		envi = null;
		engine = null;
	}
	
	public void checkinv() {
		
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
