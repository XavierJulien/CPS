package loderunner.services;

public interface PlayerService extends CharacterService{
	
	/* Observators */
	public EngineService getEngine();
	
	/* Constructors */
	/*    EMPTY     */
	
	/* Invariant */
	/*   EMPTY   */
	
	/* Operators */
	/**
	 * post :   getEnvi().getCellNature(player.getWdt()@pre,player.getHgt()@pre)@pre \not \in {LAD,HDR} &&
	 * 			getEnvi().getCellNature(player.getWdt()@pre,player.getHgt()@pre-1)@pre \not \in {EMP} &&
	 * 			getEnvi().getCellContent(player.getWdt()@pre,player.getHgt()@pre-1)@pre.getCharacter()@pre == null
	 * 			\implies player.getHgt() == player.getHgt()@pre -1
	 */
	public void step();
}
