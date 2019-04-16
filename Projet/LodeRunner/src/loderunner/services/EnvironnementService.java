package loderunner.services;

import java.util.Set;

public interface EnvironnementService extends ScreenService{
	/* Observators */
	/**
	 * pre : getCellContent(x,y) require 0<=x<getHeight() && require 0<=y<getHeight() (super?)
	 */
	public Set<Character> getCellContent(int x,int y);
	
	/* Invariant */
	/**
	 *	\forall (x,y) : Integer * Integer \in [0..getWidth()-1]*[0..getHeight()-1]
	 *		\forall (c1,c2) : Character * Character \in getCellContent(x,y), c1 == c2
	 * 	\forall (x,y) : Integer * Integer \in [0..getWidth()-1]*[0..getHeight()-1] 
	 * 		\if getCellNature(x,y) \in {Cell.MTL,Cell.PLR} \implies getCellContent(x,y) == \void
	 * 	\forall (x,y) : Integer * Integer \in [0..getWidth()-1]*[0..getHeight()-1] 
	 * 		\exists t : Treasure \in getCellContent(x,y)
	 * 			\implies getCellNature(x,y) = Cell.EMP && getCellNature(x,y-1) \in {Cell.MTL,Cell.PLT}
	 */
		

}
