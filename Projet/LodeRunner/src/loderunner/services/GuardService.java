package loderunner.services;

import loderunner.data.Command;

public interface GuardService extends CharacterService {
	
	/* Observators */
	public int getId();
	public PlayerService getTarget();
	public Command getBehaviour();
	public int getTimeInHole();
	
	/* Constructor */
	/**
	 * pre : getEnvi().getCellContent(x,y).getCharacter() == null
	 * pre : getEnvi().getCellNature(x,y) == EMP
	 * post : getTarget() == target
	 * post : getTimeInHole() == 0
	 * post : getBahavior() == ?
	 */
	public void init(ScreenService s, int x, int y, PlayerService target);
	
	/* Invariant */ 
	/* NONE */

	/* Operators */
	
	/**
	 * pre : climbLeft() require getEnvi().getCellNature(getWdt(),getHgt()) == HOL 
	 */
	
	/**
	 * post : getEnvi().getCellNature(getWdt()@pre,getHdt()@pre+1) \not \in {PTL,MTL}
	 * 			&& getEnvi().getCellNature(getWdt()@pre-1,getHdt()@pre+1) \not \in {PTL,MTL} 
	 * 			&& getEnvi().getCellContent(getWdt()@pre-1,getHgt()@pre+1).getCharacter() == null
	 * 			implies getHgt()==getHgt()@pre+1 && getWdt()==getWdt()@pre-1 && getBehaviour() == ? && getTimeInHole()==0
	 */
	public void climbLeft();
	
	/**
	 * pre : climbRight() require getEnvi().getCellNature(getWdt(),getHgt()) == HOL 
	 */
	/**
	 * post : getEnvi().getCellNature(getWdt()@pre,getHdt()@pre+1) \not \in {PTL,MTL}
	 * 			&& getEnvi().getCellNature(getWdt()@pre+1,getHdt()@pre+1) \not \in {PTL,MTL} 
	 * 			&& getEnvi().getCellContent(getWdt()@pre+1,getHgt()@pre+1).getCharacter() == null
	 * 			implies getHgt()==getHgt()@pre+1 && getWdt()==getWdt()@pre+1 && getBehaviour() == ? && getTimeInHole()==0
	 */
	public void climbRight();
	
	public void step();
	

	
}