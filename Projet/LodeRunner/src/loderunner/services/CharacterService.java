package loderunner.services;

public interface CharacterService {
	/* Observators */
	public EnvironnementService getEnvi();
	public int getHgt();
	public int getWdt();
	public int getId();
	
	/* Constructors */
	/**
	 * pre : init(x,y) require getEnvi().getCellNature(x,y) == EMP
	 * pre : init(x,y) require getEnvi().getCellNature(x,y-1) == PLT 
	 * 		&& init(x,y) require getEnvi().getCellNature(x,y-1) == MTL
	 */
	public void init(ScreenService s,int x,int y,int id);
	
	
	/* Invariant */
	/**
	 * getEnvi().getCellNature(getWdt(),getHgt()) \in {Cell.EMP,Cell.HOL,Cell.LAD,Cell.HDR}
	 * \exists x : Character \in getEnvi().getCellContent(getWdt(),getHgt()) \implies x==this
	 *
	 */
		
	
	/* Operators */
	/**
	 * post : getHgt() = getHgt()@pre
	 *		  getWdt()@pre == 0 \implies getWdt() == getWdt()@pre
	 *		  getEnvi().getCellNature(getWdt()@pre-1,getHgt()@pre) \in {MTL,PLT}
	 *		  	\implies getWdt() == getWdt()@pre
	 *		  getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) \not \in {HDR,LAD} &&
	 *	   	  	getEnvi().getCellNature(getWdt()@pre,getHgt()@pre-1) \not \in {MTL,PLT} &&
	 *	   	  	\not \exists c : Character \in getEnvi().getCellContent(getWdt()@pre,getHgt()@pre-1)
	 *	      		\implies getWdt() == getWdt()@pre
	 *		  \exists c : Character \in getEnvi().getCellNature(getWdt()@pre-1,getHgt()@pre)
	 *			\implies getWdt() == getWdt()@pre
	 *		  getWdt()@pre != 0 &&
	 *			getEnvi().getCellNature(getWdt()@pre-1,getHgt()@pre) \not \in {MTL,PLT} &&
	 *				(getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) \in {LAD,HDR} ||
	 *				getEnvi().getCellNature(getWdt()@pre,getHgt()@pre-1) \in {MTL,PLT,LAD} ||
	 *				\exists c : Character \in getEnvi().getCellContent(getWdt()@pre,getHgt()@pre-1)) &&
	 *			\not \exists c : Character \in getEnvi().getCellContent(getWdt()@pre-1,getHgt()@pre)
	 *			\implies getWdt() == getWdt()@pre-1
	 *		  
	 */
	public void goLeft();
	
	/**
	 * post : getHgt() = getHgt()@pre
	 *		  getWdt()@pre == getEnvi().getWidth()-1 \implies getWdt() == getWdt()@pre
	 *		  getEnvi().getCellNature(getWdt()@pre+1,getHgt()@pre) \in {MTL,PLT}
	 *		  	\implies getWdt() == getWdt()@pre
	 *		  getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) \not \in {HDR,LAD} &&
	 *	   	  	getEnvi().getCellNature(getWdt()@pre,getHgt()@pre-1) \not \in {MTL,PLT} &&
	 *	   	  	\not \exists c : Character \in getEnvi().getCellContent(getWdt()@pre,getHgt()@pre-1)
	 *	      		\implies getWdt() == getWdt()@pre
	 *		  \exists c : Character \in getEnvi().getCellNature(getWdt()@pre+1,getHgt()@pre)
	 *			\implies getWdt() == getWdt()@pre
	 *		  getWdt()@pre != 0 &&
	 *			getEnvi().getCellNature(getWdt()@pre+1,getHgt()@pre) \not \in {MTL,PLT} &&
	 *				(getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) \in {LAD,HDR} ||
	 *				getEnvi().getCellNature(getWdt()@pre,getHgt()@pre-1) \in {MTL,PLT,LAD} ||
	 *				\exists c : Character \in getEnvi().getCellContent(getWdt(@pre),getHgt()@pre-1)) &&
	 *			\not \exists c : Character \in getEnvi().getCellContent(getWdt()@pre-1,getHgt()@pre)
	 *			\implies getWdt() == getWdt()@pre+1
	 *		  
	 */
	public void goRight();
	
	/**
	 * post : getWdt() = getWdt()@pre
	 *		  getHgt()@pre == getEnvi().getHeight()-1 \implies getHgt() == getHgt()@pre
	 *		  getEnvi().getCellNature(getWdt()@pre,getHgt()@pre+1) != LAD
	 *		  	\implies getHgt() == getHgt()
	 *		  getEnvi().getCellNature(getWdt(),getHgt()) == LAD &&
	 *	   	  	\not \exists c : Character \in getEnvi().getCellContent(getWdt()@pre,getHgt()@pre+1)
	 *	      		\implies getHgt() == getHgt()+1
	 */
	public void goUp();
	
	/**
	 * post : getWdt() = getWdt()@pre
	 *		  getHgt()@pre == 0 \implies getHgt() == getHgt()@pre
	 *		  getEnvi().getCellNature(getWdt()@pre,getHgt()@pre-1) \in {PLT,MTL}
	 *		  	\implies getHgt() == getHgt()@pre
	 *		  getEnvi().getCellNature(getWdt()@pre,getHgt()@pre == HOL &&
	 *			getEnvi().getCellNature(getWdt()@pre,getHgt()@pre-1 \in {MTL,PLT}
	 *			\implies getHgt() == getHgt()@pre 
	 *		  (getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) \in {LAD,HDR} &&
	 *			getEnvi().getCellNature(getWdt()@pre,getHgt()@pre-1) \in {EMP,HDR,LAD,HOL})
	 *			|| (getEnvi().getCellNature(getWdt()@pre,getHgt()@pre-1) == EMP &&
	 *				getEnvi().getCellNature(getwdt()@pre,getHgt()@pre-1) == LAD)
	 *	   	  	\not \exists c : Character \in getEnvi().getCellContent(getWdt()@pre,getHgt()@pre-1)
	 *	      		\implies getHgt() == getHgt()@pre-1
	 */
	public void goDown();
	
}
