package loderunner.services;

public interface CharacterService {
	/* Observators */
	public EnvironnementService getEnvi();
	public int getHgt();
	public int getWdt();
	
	
	/* Constructors */
	/**
	 * pre : init(x,y) require getEnvi().getCellNature(x,y) == EMP
	 */
	public void init(ScreenService s,int x,int y);
	
	
	/* Invariant */
	/**
	 * getEnvi().getCellNature(getWdt(),getHgt()) \in {Cell.EMP,Cell.HOL,Cell.LAD,Cell.HDR}
	 * \exists x : Character \in getEnvi().getCellContent(getWdt(),getHgt()) \implies x==this
	 *
	 */
		
	
	/* Operators */
	/**
	 * post : getHgt(goLeft()) = getHgt()
	 *		  getWdt() == 0 \implies getWdt(goLeft()) == getWdt()
	 *		  getEnvi().getCellNature(getWdt()-1,getHgt()) \in {MTL,PLT}
	 *		  	\implies getWdt(goLeft()) == getWdt()
	 *		  getEnvi().getCellNature(getWdt(),getHgt()) \not \in {HDR,LAD} &&
	 *	   	  	getEnvi().getCellNature(getWdt(),getHgt()-1) \not \in {MTL,PLT} &&
	 *	   	  	\not \exists c : Character \in getEnvi().getCellContent(getWdt(),getHgt()-1)
	 *	      		\implies getWdt(goLeft()) == getWdt()
	 *		  \exists c : Character \in getEnvi().getCellNature(getWdt()-1,getHgt())
	 *			\implies getWdt(goLeft()) == getWdt()
	 *		  getWdt() != 0 &&
	 *			getEnvi().getCellNature(getWdt()-1,getHgt()) \not \in {MTL,PLT} &&
	 *			getEnvi().getCellNature(getWdt(),getHgt()) \in {LAD,HDR} ||
	 *			getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT,LAD} ||
	 *			\exists c : Character \in getEnvi().getCellContent(getWdt(),getHgt()-1) &&
	 *			\not \exists c : Character \in getEnvi().getCellContent(getWdt()-1,getHgt())
	 *			\implies getWdt(goLeft()) == getWdt()-1
	 *		  
	 */
	public void goLeft();
	
	/**
	 * post : getHgt(goRight()) = getHgt()
	 *		  getWdt() == getEnvi().getWidth()-1 \implies getWdt(goRight()) == getWdt()
	 *		  getEnvi().getCellNature(getWdt()+1,getHgt()) \in {MTL,PLT}
	 *		  	\implies getWdt(goRight()) == getWdt()
	 *		  getEnvi().getCellNature(getWdt(),getHgt()) \not \in {HDR,LAD} &&
	 *	   	  	getEnvi().getCellNature(getWdt(),getHgt()-1) \not \in {MTL,PLT} &&
	 *	   	  	\not \exists c : Character \in getEnvi().getCellContent(getWdt(),getHgt()-1)
	 *	      		\implies getWdt(goRight()) == getWdt()
	 *		  \exists c : Character \in getEnvi().getCellNature(getWdt()+1,getHgt())
	 *			\implies getWdt(goRight()) == getWdt()
	 *		  getWdt() != 0 &&
	 *			getEnvi().getCellNature(getWdt()+1,getHgt()) \not \in {MTL,PLT} &&
	 *			getEnvi().getCellNature(getWdt(),getHgt()) \in {LAD,HDR} ||
	 *			getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT,LAD} ||
	 *			\exists c : Character \in getEnvi().getCellContent(getWdt(),getHgt()-1) &&
	 *			\not \exists c : Character \in getEnvi().getCellContent(getWdt()-1,getHgt())
	 *			\implies getWdt(goRight()) == getWdt()+1
	 *		  
	 */
	public void goRight();
	
	/**
	 * post : getWdt(goUp()) = getWdt()
	 *		  getHgt() == getEnvi().getHeight()-1 \implies getHgt(goUp()) == getHgt()
	 *		  getEnvi().getCellNature(getWdt(),getHgt()+1) \in {MTL,PLT}
	 *		  	\implies getHgt(goUp()) == getHgt()
	 *		  getEnvi().getCellNature(getWdt(),getHgt()) == HDR &&
	 *	   	  	\not \exists c : Character \in getEnvi().getCellContent(getWdt(),getHgt()+1)
	 *	      		\implies getHgt(goUp()) == getHgt()+1
	 */
	public void goUp();
	public void goDown();
	/**
	 * pre : dig(x,y) require getCellNature(x,y) == PLT
	 * post : getCellNature(dig(x,y),x,y) == HOL
	 * 		  \forall x: Integer \in [0..getWdt()-1]
	 * 		  	\forall y: Integer \in [0..getHgt()-1]
	 * 			  \with (x,y)!=(u,v)
	 * 			    \implies getCellNature(dig(u,v),x,y) == CellNature(x,y)
	 */
	public void dig(int x, int y);
	
	/**
	 * pre : fill(x,y) require getCellNature(x,y) == HOL
	 * post : getCellNature(fill(x,y),x,y) == PLT
	 * 		  \forall x: Integer \in [0..getWdt()-1]
	 * 		  	\forall y: Integer \in [0..getHgt()-1]
	 * 			  \with (x,y)!=(u,v)
	 * 			    \implies getCellNature(fill(u,v),x,y) == CellNature(x,y)
	  */
	public void fill(int x, int y);
}
