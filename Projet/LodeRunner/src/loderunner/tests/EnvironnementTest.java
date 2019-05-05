package loderunner.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import loderunner.contracts.EditableScreenContract;
import loderunner.contracts.EnvironnementContract;
import loderunner.data.Cell;
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
	//RIEN ?
	
	
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
	
	/**
	* ETATS REMARQUABLES
	*/
	
	/**
	*  PAIRES DE TRANSITIONS
	*/
	
	

}
