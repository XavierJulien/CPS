package loderunner.services;

import loderunner.data.Cell;

public interface ScreenService {
	/* Observators */
	public int getHeight();
	public int getWidth();
	
	/**
	 * pre : getCellNature(x,y) require 0<=x<getWidth() && require 0<=y<getHeight() 
	 */
	public Cell getCellNature(int x,int y);
	
	
	
	/* Constructors */
	/**
	 * pre : init(h,w) require 0<h && 0<w
	 * post : getHeight() == h
	 * 	 	  getWidth() == w
	 * 		  \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			  getCellNature(x,y) == EMP
	 */
	public void init(int height,int width);
	
	/* Invariant */
	/*   Empty   */
		
	/* Operators */
	/**
	 * pre : dig(u,v) require getCellNature(u,v) == PLT
	 * post : getCellNature(u,v) == HOL
	 * 		  \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			  \with (x,y)!=(u,v)
	 * 			    \implies getCellNature(x,y) == getCellNature(x,y)@pre
	 */
	public void dig(int x, int y);
	
	/**
	 * pre : fill(u,v) require getCellNature(u,v) == HOL
	 * post : getCellNature(u,v) == PLT
	 * 		  \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			  \with (x,y)!=(u,v)
	 * 			    \implies getCellNature(x,y) == getCellNature(x,y)@pre
	  */
	public void fill(int x, int y);
}
