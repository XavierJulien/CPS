package loderunner.services;

import loderunner.data.Cell;

public interface EditableScreenService extends ScreenService{

	/* Observators */
	public boolean isPlayable();
	
	/* Invariant */
	/**
	 * \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			 getCellNature(x,y) != HOL
	 * \forall x: Integer \in [0..getWidth()-1]
	 * 	getCellNature(x,0) == MTL
	 */
		
	/* Operators */

	/**
	 *  pre : setNature(u,v,c) require 0<=x<getWidth() && require 0<=y<getHeight() 
	 * 	post : getCellNature(x,y) == C
	 * 	post : \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			 \with (x,y)!=(u,v)
	 * 			  \implies getCellNature(x,y) == getCellNature(x,y)@pre
	 */
	public void setNature(int x, int y, Cell c);
	

	
}