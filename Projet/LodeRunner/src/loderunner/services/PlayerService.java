package loderunner.services;

import loderunner.data.Coord;
import loderunner.data.Item;

public interface PlayerService extends CharacterService{
	
	/* Observators */
	public EngineService getEngine();
	public boolean hasGauntlet();
	public Item getGauntlet();
	/* Constructors */
	/*    EMPTY     */
	
	/* Invariant */
	/*   EMPTY   */
	
	/* Operators */
	
	/**
	 * pre setGauntlet(g) require 
	 * 		g != null && 
	 * 		g.getNature() == ItemType.Gauntlet &&
	 * 		hasGauntlet() == false
	 */
	public void setGauntlet(Item gauntlet);
	public void hitRight();
	public void hitLeft();
	public void init(EngineService e,Coord c);
	/**
	 * post :  	getEnvi().getCellNature(player.getWdt()@pre,player.getHgt()@pre) \not \in {LAD,HDR} &&
	 * 			getEnvi().getCellNature(player.getWdt()@pre,player.getHgt()@pre-1) != EMP &&
	 * 			getEnvi().getCellContent(player.getWdt()@pre,player.getHgt()@pre-1).getCharacter()@pre == null
	 * 			\implies player.getHgt() == player.getHgt()@pre -1
	 * 
	 * post : getEngine().getNextCommand() == DIGL 
	 * 		  && (getEnvi().getCellNature(player.getWdt()@pre, player.getHgt()@pre-1) \in {PLT,MTL} 
	 * 			 || getEnvi().getCellContent(player.getWdt()@pre,player.getHgt()@pre-1) != null )
	 * 		  && getEnvi().getCellNature(player.getWdt()@pre-1,player.getHgt()@pre) \in {EMP,LAD,HDR,HOL}
	 * 		  && getEnvi().getCellNature(player.getWdt()@pre-1,player.getHgt()@pre-1) == PLT
	 * 		    \implies getEnvi().getCellNature(player.getWdt()@pre-1,player.getHgt()@pre-1) == HOL
	 *			
	 * post : getEngine().getNextCommand() == DIGR 
	 * 		  && (getEnvi().getCellNature(player.getWdt()@pre, player.getHgt()@pre-1) \in {PLT,MTL} 
	 * 			 || getEnvi().getCellContent(player.getWdt()@pre,player.getHgt()@pre-1) != null )
	 * 		  && getEnvi().getCellNature(player.getWdt()@pre+1,player.getHgt()@pre) \in {EMP,LAD,HDR,HOL}
	 * 		  && getEnvi().getCellNature(player.getWdt()@pre+1,player.getHgt()@pre-1) == PLT
	 * 		    \implies getEnvi().getCellNature(player.getWdt()@pre+1,player.getHgt()@pre-1) == HOL
	 */
	public void step();
	
	public PlayerService clonePlayer();
	public PlayerService clonePlayer2();
}
