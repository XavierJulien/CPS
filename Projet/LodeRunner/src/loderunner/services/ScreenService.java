package loderunner.services;

public interface ScreenService {
	/* Observators */
	public int getHeight();
	public int getWidth();
	
	/**
	 * pre : getCellNature(x,y) require 0<=x<getHeight() && require 0<=y<getHeight() 
	 */
	public Cell getCellNature(int x,int y);
	
	
	
	/* Constructors */
	/**
	 * pre : init(h,w) require 0<h && 0<w
	 * post : getHeight(init(h,w)) == h 
	 * 	 	  getWidth(init(h,w)) == w
	 * 		  \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			  getCellNature(init(h,w),x,y) == EMP
	 */
	public void init(int height,int width);
	
	/* Invariant */
	/*   Empty   */
		
	/* Operators */
	/**
	 * pre : dig(x,y) require getCellNature(x,y) == PLT
	 * post : getCellNature(dig(x,y),x,y) == HOL
	 * 		  \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			  \with (x,y)!=(u,v)
	 * 			    \implies getCellNature(dig(u,v),x,y) == CellNature(x,y)
	 */
	public void dig(int x, int y);
	
	/**
	 * pre : fill(x,y) require getCellNature(x,y) == HOL
	 * post : getCellNature(fill(x,y),x,y) == PLT
	 * 		  \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			  \with (x,y)!=(u,v)
	 * 			    \implies getCellNature(fill(u,v),x,y) == CellNature(x,y)
	  */
	public void fill(int x, int y);
}
