package loderunner.services;

import loderunner.data.Cell;
import loderunner.data.Command;

public interface GuardService extends CharacterService {

	final int timeEpsilon = 1;
	
	/* Observators */
	public int getId();
	public PlayerService getTarget();
	public Command getBehaviour();
	public int getTimeInHole();
	public EngineService getEngine();
	
	/* Constructor */
	/**
	 * pre : getEnvi().getCellContent(x,y).getCharacter() == null
	 * pre : getEnvi().getCellNature(x,y) == EMP
	 * post : getTarget() == target
	 * post : getTimeInHole() == 0
	 */
	public void init(EngineService s, int x, int y, PlayerService target);
	
	/* Invariant */ 
	/* EST CE QU4IL Y A ENCORE 2 CAS A METTRE EN PLUS : LE CAS OU SEULEMENT SUR ECHELLE DU COUP UP OU DOWN */
	/**
	 * inv : getEnvi().getCellNature(getWdt(),getHgt()) == LAD
	 * 			&& getHgt() < getTarget().getHgt()
	 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL}
	 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() != null
	 * 				implies Math.abs(getTarget().getHgt()-getHgt()) < Math.abs(getTarget().getWdt()-getWdt())
	 * 			implies getBehaviour() == UP 
	 *
	 * inv : getEnvi().getCellNature(getWdt(),getHgt()) == LAD
	 * 			&& getHgt() > getTarget().getHgt()
	 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL}
	 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() != null
	 * 				implies Math.abs(getTarget().getHgt()-getHgt()) < Math.abs(getTarget().getWdt()-getWdt())
	 * 			implies getBehaviour() == DOWN 
	 * 
	 * inv : (getEnvi().getCellNature(getWdt(),getHgt()) == LAD
	 * 			&& getTarget().getWdt() < getWdt()
	 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL}
	 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() != null
	 * 				implies Math.abs(getTarget().getWdt()-getWdt()) < Math.abs(getTarget().getHgt()-getHgt())))
	 * 		||
	 * 		((getEnvi().getCellNature(getWdt(),getHgt()) \in {HOL,HDR}
	 * 			|| getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT}
	 * 			|| getEnvi().getCellContent(getWdt(),getHgt()-1) != null)
	 * 			&& getTarget().getWdt() < getWdt())
	 * 		implies getBehaviour() == LEFT
	 * 
	 * 
	 * inv : (getEnvi().getCellNature(getWdt(),getHgt()) == LAD
	 * 			&& getTarget().getWdt() > getWdt()
	 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL}
	 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getCharacter() != null
	 * 				implies Math.abs(getTarget().getWdt()-getWdt()) < Math.abs(getTarget().getHgt()-getHgt())))
	 * 		||
	 * 		((getEnvi().getCellNature(getWdt(),getHgt()) \in {HOL,HDR}
	 * 			|| getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT}
	 * 			|| getEnvi().getCellContent(getWdt(),getHgt()-1) != null)
	 * 			&& getTarget().getWdt() > getWdt())
	 * 		implies getBehaviour() == RIGHT
	 * 
	 * 
	 *  inv :((getEnvi().getCellNature(getWdt(),getHgt()) \in {HOL,HDR}
		 * 			|| getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT}
		 * 			|| getEnvi().getCellContent(getWdt(),getHgt()-1) != null)
		 * 		&& getTarget().getWdt() == getWdt())
		 * 		||
		 * 		(getEnvi().getCellNature(getWdt(),getHgt()) == LAD 
		 * 			&& getTarget().getHgt() == getHgt())
		 * 		
		 * 		implies getBehaviour() == NEUTRAL
	 * 
	 * 
	 * 
	 */
	  
	  
	 /**
	 * Si le garde est sur LAD et que la cible est strictement au-dessus, la commande du garde est d'aller vers le haut
	 */
	// ((getCellNature(getEnvi(G),getWdt(G),getHgt(G)==LAD)) \and (getHgt(getTarget(G)) > getHgt(G))) => getBehaviour()==UP
	/**
	 * Si le garde est sur LAD et que la cible est strictement en-dessous, la commande du garde est d'aller vers le bas
	 */
	// ((getCellNature(getEnvi(G),getWdt(G),getHgt(G)==LAD)) \and (getHgt(getTarget(G)) < getHgt(G))) => getBehaviour()==DOWN
	/**
	 * Si le garde est sur HOL/HDR ou au-dessus d'une case vide, sa commande est :										 - 
	 */
	/**
	 * - LEFT si la cible est strictement à gauche
	 */
	// ((getCellNature(getEnvi(G),getWdt(G),getHgt(G)) \in {HOL/HDR}
	//   \or (getCellNature(getEnvi(G),getWdt(G),getHgt(G)-1)==EMP 
	//   \or \not \exists Character c \in getCharacters(getCellContent(getEnvi(G),getWdt(G),getHgt(G)-1)) 
	//   \and (getWdt(getTarget(G)) < getWdt(G)))) => getBehaviour()==LEFT
	/**
	 * - RIGHT si la cible est strictement à droite
	 */
	// ((getCellNature(getEnvi(G),getWdt(G),getHgt(G)) \in {HOL/HDR}
	//   \or (getCellNature(getEnvi(G),getWdt(G),getHgt(G)-1)==EMP 
	//   \or \not \exists Character c \in getCharacters(getCellContent(getEnvi(G),getWdt(G),getHgt(G)-1)) 
	//   \and (getWdt(getTarget(G)) > getWdt(G)))) => getBehaviour()==RIGHT
	/**
	 * - NEUTRAL sinon
	 */
	// ((getCellNature(getEnvi(G),getWdt(G),getHgt(G)) \in {HOL/HDR}
	//   \or (getCellNature(getEnvi(G),getWdt(G),getHgt(G)-1)==EMP 
	//   \or \not \exists Character c \in getCharacters(getCellContent(getEnvi(G),getWdt(G),getHgt(G)-1)) 
	//   \and (getWdt(getTarget(G)) == getWdt(G)))) => getBehaviour()==NEUTRAL
	/**
	 * Si le garde est sur LAD et que la case en-dessous est non-libre alors sa
	 * commande se fait sur l'axe sur lequel la distance à la cible est la plus courte
	 */
	// ((getCellNature(getEnvi(G),getWdt(G),getHgt(G)) == LAD) 
	//   \and ((getCellNature(getEnvi(G),getWdt(G),getHgt(G)-1) \not \in {EMP,LAD,HDR,HOL} 
	//   \or \exists Character c \in getCharacter(getCellContent(getEnvi(G),getWdt(G),getHgt(G)-1)))))
	//   \and 
	//   => 



	/* Operators */
	

	
	default boolean willFall() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		Cell nat_under = getEnvi().getCellNature(getWdt(), getHgt()-1);
		if (nat != Cell.LAD && nat != Cell.HDR && nat != Cell.HOL) {
			if (nat_under == Cell.HDR || nat_under == Cell.EMP || nat_under == Cell.HOL) {
				if (getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() == null)
					return true;
			}
		}
		return false;
	}
	
	default boolean willWaitInHole() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		if (nat==Cell.HOL) {
			if (getTimeInHole() < 5)
				return true;
		}
		return false;
	}
	
	default boolean willClimbLeft() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		if (nat==Cell.HOL) {
			if (getTimeInHole()==5) {
				if (getBehaviour()==Command.LEFT) 
					return true;
			}
		}
		return false;
	}	
	
	
	default boolean willClimbRight() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		if (nat==Cell.HOL) {
			if (getTimeInHole()==5) {
				if (getBehaviour()==Command.RIGHT) 
					return true;
			}
		}
		return false;
	}	
	
	
	default boolean willClimbNeutral() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		if (nat==Cell.HOL) {
			if (getTimeInHole()==5) {
				if (getBehaviour()==Command.NEUTRAL) 
					return true;
			}
		}
		return false;
	}	
	
	default boolean willFollowBehaviour() {
		if (!willFall() && !willWaitInHole() && !willClimbLeft() && !willClimbRight() && !willClimbNeutral()) {
			return true;
			}
		return false;
	}	
	
	
	/**
	 * pre : climbLeft() require getEnvi().getCellNature(getWdt(),getHgt()) == HOL 
	 */
	/**
	 * post : getEnvi().getCellNature(getWdt()@pre,getHdt()@pre+1) \not \in {PTL,MTL}
	 * 			&& getEnvi().getCellNature(getWdt()@pre-1,getHdt()@pre+1) \not \in {PTL,MTL} 
	 * 			&& getEnvi().getCellContent(getWdt()@pre-1,getHgt()@pre+1).getCharacter() == null
	 * 			implies getHgt()==getHgt()@pre+1 && getWdt()==getWdt()@pre-1 && getTimeInHole()==0
	 */
	public void climbLeft();
	
	/**
	 * pre : climbRight() require getEnvi().getCellNature(getWdt(),getHgt()) == HOL 
	 */
	/**
	 * post : getEnvi().getCellNature(getWdt()@pre,getHdt()@pre+1) \not \in {PTL,MTL}
	 * 			&& getEnvi().getCellNature(getWdt()@pre+1,getHdt()@pre+1) \not \in {PTL,MTL} 
	 * 			&& getEnvi().getCellContent(getWdt()@pre+1,getHgt()@pre+1).getCharacter() == null
	 * 			implies getHgt()==getHgt()@pre+1 && getWdt()==getWdt()@pre+1 && getTimeInHole()==0
	 */
	public void climbRight();
	
	/**
	 * \willFall definedBy getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) \not \in {LAD,HDR,HOL}
	 * 			&& getEnvi().getCellNature(getWdt()@pre,getHgt()@pre-1) \in {HDR,EMP,HOL}
	 * 			&& getEnvi().getCellContent(getWdt()@pre,getHgt()@pre-1).getCharacter() == null

	 * \willWaitInHole definedBy getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) == HOL
	 * 			&& getTimeInHole < 5
	 * 
	 * \willClimbLeft definedBy getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) == HOL
	 * 			&& getTimeInHole()==5 && getBehaviour()==LEFT
	 * 
	 * \willClimbRight definedBy getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) == HOL
	 * 			&& getTimeInHole()==5 && getBehaviour()==RIGHT
	 * 
	 * \willClimbNeutral definedBy getEnvi().getCellNature(getWdt()@pre,getHgt()@pre) == HOL
	 * 			&& getTimeInHole()==5 && getBehaviour()==NEUTRAL
	 * 
	 * \willFollowBehaviour definedBy 
	 * 			\not \willFall && \not \willWaitInHole && \not \willClimbLeft && \not \willClimbRight && \not \willClimbNeutral
	 * 
	 * 
	 * post : \willFall() implies getWdt()==getWdt()@pre && getHgt()==getHgt()@pre-1 && getTimeInHole()==getTimeInHole()@pre
	 * post : \waitInHole implies getWdt()==getWdt()@pre && getHgt()==getHgt()@pre && getTimeInHole()==getTimeInHole()@pre+espilon
	 * post : \willClimbLeft implies \postOfClimbLeft
	 * post : \willClimbRight implies \postOfClimbRight
	 * post : \willClimbNeutral implies getWdt()==getWdt()@pre && getHgt()==getHgt()@pre && getTimeInHole()==getTimeInHole()@pre
	 * post : \willFollowBehaviour && getBehaviour()==LEFT implies \postofgoLeft
	 * post : \willFollowBehaviour && getBehaviour()==RIGTH implies \postofgoRight
	 * post : \willFollowBehaviour && getBehaviour()==UP implies \postofgoUp
	 * post : \willFollowBehaviour && getBehaviour()==DOWN implies \postofgoDown 
	 */
	public void step();
	

	
}