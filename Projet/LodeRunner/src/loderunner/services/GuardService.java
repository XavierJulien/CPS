package loderunner.services;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Item;

public interface GuardService extends CharacterService {

	final int timeEpsilon = 1;
	
	/* Observators */
	public int getId();
	public PlayerService getTarget();
	public Command getBehaviour();
	public int getTimeInHole();
	public EngineService getEngine();
	public boolean hasItem();
	public void waitInHole();
	public void setTreasure(Item treasure);
	public Item getTreasure();
	
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
	 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL,TLP}
	 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getGuard() != null
	 * 				implies Math.abs(getTarget().getHgt()-getHgt()) < Math.abs(getTarget().getWdt()-getWdt())
	 * 			implies getBehaviour() == UP 
	 *
	 * inv : getEnvi().getCellNature(getWdt(),getHgt()) == LAD
	 * 			&& getHgt() > getTarget().getHgt()
	 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL,TLP}
	 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getGuard() != null
	 * 				implies Math.abs(getTarget().getHgt()-getHgt()) < Math.abs(getTarget().getWdt()-getWdt())
	 * 			implies getBehaviour() == DOWN 
	 * 
	 * inv : (getEnvi().getCellNature(getWdt(),getHgt()) == LAD
	 * 			&& getTarget().getWdt() < getWdt()
	 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL,TLP}
	 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getGuard() != null
	 * 				implies Math.abs(getTarget().getWdt()-getWdt()) < Math.abs(getTarget().getHgt()-getHgt())))
	 * 		||
	 * 		((getEnvi().getCellNature(getWdt(),getHgt()) \in {HOL,HDR}
	 * 			|| getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT,TLP}
	 * 			|| getEnvi().getCellContent(getWdt(),getHgt()-1) != null)
	 * 			&& getTarget().getWdt() < getWdt())
	 * 		implies getBehaviour() == LEFT
	 * 
	 * 
	 * inv : (getEnvi().getCellNature(getWdt(),getHgt()) == LAD
	 * 			&& getTarget().getWdt() > getWdt()
	 * 			&& (getEnvi().getCellNature(getWdt(),getHgt()-1) \in {PLT,MTL,TLP}
	 * 				|| getEnvi().getCellContent(getWdt(),getHgt()-1).getGuard() != null
	 * 				implies Math.abs(getTarget().getWdt()-getWdt()) < Math.abs(getTarget().getHgt()-getHgt())))
	 * 		||
	 * 		((getEnvi().getCellNature(getWdt(),getHgt()) \in {HOL,HDR}
	 * 			|| getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT,TLP}
	 * 			|| getEnvi().getCellContent(getWdt(),getHgt()-1) != null)
	 * 			&& getTarget().getWdt() > getWdt())
	 * 		implies getBehaviour() == RIGHT
	 * 
	 * 
	 *  inv :((getEnvi().getCellNature(getWdt(),getHgt()) \in {HOL,HDR}
		 * 			|| getEnvi().getCellNature(getWdt(),getHgt()-1) \in {MTL,PLT,TLP}
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
	  
	  



	/* Operators */
	

	
	default boolean willFall() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		Cell nat_under = getEnvi().getCellNature(getWdt(), getHgt()-1);
		if (nat != Cell.LAD && nat != Cell.HDR && nat != Cell.HOL) {
			if (nat_under == Cell.HDR || nat_under == Cell.EMP || nat_under == Cell.HOL) {
				if (getEnvi().getCellContent(getWdt(), getHgt()-1).getGuard() == null)
					return true;
			}
		}
		return false;
	}
	
	default boolean willWaitInHole() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		if (nat==Cell.HOL) {
			if (getTimeInHole() < 10)
				return true;
		}
		return false;
	}
	
	default boolean willClimbLeft() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		if (nat==Cell.HOL) {
			if (getTimeInHole()==10) {
				if (getBehaviour()==Command.LEFT) 
					return true;
			}
		}
		return false;
	}	
	
	
	default boolean willClimbRight() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		if (nat==Cell.HOL) {
			if (getTimeInHole()==10) {
				if (getBehaviour()==Command.RIGHT) 
					return true;
			}
		}
		return false;
	}	
	
	
	default boolean willClimbNeutral() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		if (nat==Cell.HOL) {
			if (getTimeInHole()==10) {
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
	 * post : getEnvi().getCellNature(getWdt()@pre,getHdt()@pre+1) \not \in {PTL,MTL,TLP}
	 * 			&& getEnvi().getCellNature(getWdt()@pre-1,getHdt()@pre+1) \not \in {PTL,MTL,TLP} 
	 * 			&& getEnvi().getCellContent(getWdt()@pre-1,getHgt()@pre+1).getCharacter() == null
	 * 			implies getHgt()==getHgt()@pre+1 && getWdt()==getWdt()@pre-1 && getTimeInHole()==0
	 */
	public void climbLeft();
	
	/**
	 * pre : climbRight() require getEnvi().getCellNature(getWdt(),getHgt()) == HOL 
	 */
	/**
	 * post : getEnvi().getCellNature(getWdt()@pre,getHdt()@pre+1) \not \in {PTL,MTL,TLP}
	 * 			&& getEnvi().getCellNature(getWdt()@pre+1,getHdt()@pre+1) \not \in {PTL,MTL,TLP} 
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