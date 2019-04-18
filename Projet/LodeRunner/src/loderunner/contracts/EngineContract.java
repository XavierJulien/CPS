package loderunner.contracts;

import java.util.ArrayList;
import java.util.List;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.decorators.EngineDecorator;
import loderunner.errors.PreconditionError;
import loderunner.services.EditableScreenService;
import loderunner.services.EngineService;
import loderunner.services.EnvironnementService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class EngineContract extends EngineDecorator{

	public EngineContract(EngineService delegate) {
		super(delegate);
	}

	public void checkInvariants() {
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
	}
	@Override
	public EnvironnementService getEnvi() {
		//1.pre
		//none
		//4.run
		return super.getEnvi();
	}

	@Override
	public PlayerService getPlayer() {
		//1.pre
		//none
		//4.run
		return super.getPlayer();
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		//1.pre
		//none
		//4.run
		return super.getGuards();
	}

	@Override
	public ArrayList<Coord> getTreasures() {
		//1.pre
		//none
		//4.run
		return super.getTreasures();
	}

	@Override
	public GameState getStatus() {
		//1.pre
		//none
		//4.run
		return super.getStatus();
	}

	@Override
	public Command getNextCommand() {
		//1.pre
		//none
		//4.run
		return super.getNextCommand();
	}

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
	@Override
	public void init(EditableScreenService e, Coord player, List<Coord> guards, List<Coord> treasures) {
		//1.pre
		if(!e.isPlayable()) throw new PreconditionError("init : l'ecran n'est pas défini comme jouable");
		if(e.getCellNature(player.getX(), player.getY()) != Cell.EMP) throw new PreconditionError("init : le player ne peut pas être init dans une case de l'envi non Cell.EMP");
		for(Coord guard : guards) {
			if(e.getCellNature(guard.getX(), guard.getY()) != Cell.EMP) throw new PreconditionError("init : un guard ne peut pas être init dans une case de l'envi non Cell.EMP");
		}
		for(Coord treasure : treasures) {
			if(e.getCellNature(treasure.getX(), treasure.getY()) != Cell.EMP ||
			   (e.getCellNature(treasure.getX(), treasure.getY()-1) != Cell.PLT && e.getCellNature(treasure.getX(), treasure.getY()-1) != Cell.MTL)) {
				throw new PreconditionError("init : un guard ne peut pas être init dans une case de l'envi non Cell.EMP");
			}
		}		
		//2.checkInvariants
		//none
		//3.captures
		//none
		//4.run
		super.init(e, player, guards, treasures);
		//5.checkInvariants
		checkInvariants();
		//6.post
		
	}

	@Override
	public void step() {
		//1.pre
		//none
		//2.checkInvariants
		checkInvariants();
		//3.captures
		//4.run
		super.step();
		//5.checkInvariants
		checkInvariants();
		//6.post
		//none
	}
}
