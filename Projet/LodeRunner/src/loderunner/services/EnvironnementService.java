package loderunner.services;

import loderunner.data.CellContent;

public interface EnvironnementService extends ScreenService{
	/* Observators */
	/**
	 * pre : getCellContent(x,y) require 0<=x<getWidth() && require 0<=y<getHeight()
	 */
	public CellContent getCellContent(int x,int y);
	
	/*Constructor*/
	/**
	 * post : getHeight() == e.getHeight() 
	 * 	 	  getWidth() == e.getWidth() ( \with this=init(e) **************voir comment introduire e)*********)
	 * 		  \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			  getCellNature(x,y) == e.getCellNature(x,y)
	 */
	public void init(EditableScreenService e);
	 
	/* Invariant */
	/**
	 *	\forall (x,y) : Integer * Integer \in [0..getWidth()-1]*[0..getHeight()-1]
	 *		\forall (c1,c2) : Character * Character \in getCellContent(x,y) * getCellContent(x,y), c1 == c2
	 * 	\forall (x,y) : Integer * Integer \in [0..getWidth()-1]*[0..getHeight()-1] 
	 * 		getCellNature(x,y) \in {MTL,PLR} \implies getCellContent(x,y) == null
	 * 	\forall (x,y) : Integer * Integer \in [0..getWidth()-1]*[0..getHeight()-1] 
	 * 		\exists t : Treasure \in getCellContent(x,y)
	 * 			\implies getCellNature(x,y) = EMP && getCellNature(x,y-1) \in {MTL,PLT}
	 */
}
