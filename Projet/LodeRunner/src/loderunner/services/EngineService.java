package loderunner.services;

import java.util.ArrayList;
import java.util.List;

import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;

public interface EngineService {
	/* Observators */
	public EnvironnementService getEnvi();
	public PlayerService getPlayer();
	public GuardService getGuards();
	public ArrayList<Coord> getTreasures();
	public GameState getStatus();
	public Command getNextCommand();
	
	/* Constructors */
	/**
	 * pre : init(e,p,g,t) require 
	 * 	e.isPlayable() && 
	 * 	e.getCellNature(p.getX(),p.getY()) == EMP &&
	 *  \forall guard : Guard \in g 
	 *  	e.getCellNature(guard.getX(),guard.getY()) == EMP &&
	 *  \forall treasure : Treasure \in t 
	 *  	e.getCellNature(treasure.getX(),treasure.getY()) == EMP &&
	 *  	e.getCellNature(treasure.getX(),treasure.getY()-1) \in {PLT,MTL}
	 *  
	 */
	public void init(EditableScreenService e,
					 Coord player,
					 List<Coord> guards,
					 List<Coord> treasures);
	/* Invariant */
	/**
	 *  pre : step() require c : CellContent \def getEnvi().getCellContent(getPlayer.getWdt(),getPlayer().getHgt())
	 *  c : CellContent \def getEnvi().getCellContent(getPlayer.getWdt(),getPlayer().getHgt()) 
	 * 		\implies c.getCharacter() == getPlayer() &&
	 *  \forall guard : Guard \in getGuards() 
	 *  	c : CellContent \def getEnvi().getCellContent(guard.getWdt(),guard.getHgt())
	 *  		\implies c.getCharacter() == guard &&
	 *  \forall treasure : Treasure \in t 
	 *		c : CellContent \def getEnvi().getCellContent(treasure.getX(),treasure.getY())
	 *  		\implies c.getItem() == ItemType.Treasure 
	 */
	/* Operators */
	/**
	 * post : \exists t : Treasure \in 
	 * 				getEnvi().getCellContent(getPlayer().getX()@pre+1,getPlayer().getY()@pre).getItem() && 
	 * 				getTreasure()@pre					
	 * 				\implies getCellContent(getPlayer().getX(),getPlayer().getY()).getItem().getItem() == null &&
	 * 						 getTreasure() == {getTreasure()@pre / t}
	 * 
	 * post: \forall t : Coord \in getTreasure()@pre 
	 * 			getPlayer().getX() == t.getX() && getPlayer().getY() == t.getY() 
	 *  		 
	 */
	public void step();
}
