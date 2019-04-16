package loderunner.services;

public interface CharacterService {
	/* Observators */
	public EnvironnementService getEnvi();
	public int getHeight();
	public int getWidth();
	
	
	/* Constructors */
	/**
	 * pre : init(h,w) require 0<h && 0<w
	 * post : getHeight(init(h,w)) == h 
	 * 	 	  getWidth(init(h,w)) == w
	 * 		  \forall x: Integer \in [0..getWidth()-1]
	 * 		  	\forall y: Integer \in [0..getHeight()-1]
	 * 			  getCellNature(init(h,w),x,y) == EMP
	 */
	public void init(ScreenService s,int height,int width);
	
	
	/* Invariant */
	/**
	 * Environnement::CellNature(getEnvi(),getWidth(),getHeight()) \in {Cell.EMP,Cell.HOL,Cell.LAD,Cell.HDR}
	 * Environnement::CellNature(getEnvi(),getWidth(),getHeight()) \in {Cell.EMP,Cell.HOL,Cell.LAD,Cell.HDR}
	 */
		
	
	/* Operators */
	/**
	 * post : getHeigth(goLeft()) = getHeight()
	 *		  getWidth() == 0 \implies getWidth(goLeft()) == getWidth()
	 *		  Environnement::CellNature(getEnvi(),getWidth()-1,getHeight()) \in {Cell.MTL,Cell.PLT,Cell.LAD}
	 *		  	\implies getWidth(goLeft()) == getWidth()
	 *		  Environnement::CellNature(getEnvi(),getWidth(),getHeight()) \not \in {Cell.HDR,Cell.LAD} &&
	 *	   	  	Environnement::CellNature(getEnvi(),getWidth(),getHeight()-1) \not \in {Cell.MTL,Cell.PLT} &&
	 *	   	  	\not \exists c : Character \in Environment::CellContent(getEnvi(),getWidth(),getHeight()-1)
	 *	      		\implies getWidth(goLeft()) == getWidth()
	 *		  \exists c : Character \in Environment::CellContent(getEnvi(),getWidth()-1,getHeight())
	 *			\implies getWidth(goLeft()) == getWidth()
	 *		  getWidth() != 0 &&
	 *			Environnement::CellNature(getEnvi(),getWidth()-1,getHeight()) \not \in {Cell.MTL,Cell.PLT} &&
	 *			Environnement::CellNature(getEnvi(),getWidth(),getHeight()) \in {Cell.LAD,Cell.HDR} ||
	 *			Environnement::CellNature(getEnvi(),getWidth(),getHeight()-1) \in {Cell.MTL,Cell.PLT,Cell.LAD} ||
	 *			\exists c : Character \in Environment::CellContent(getEnvi(),getWidth(),getHeight()-1) &&
	 *			\not \exists c : Character \in Environment::CellContent(getEnvi(),getWidth()-1,getHeight())
	 *			\implies getWidth(goLeft()) == getWidth()-1
	 *		  
	 */
	public void goLeft();
	public void goRight();
	public void goUp();
	public void goDown();
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
