package loderunner.contracts;

import java.util.ArrayList;
import java.util.List;

import loderunner.data.Cell;
import loderunner.data.CellContent;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.data.ItemType;
import loderunner.decorators.EngineDecorator;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
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

	/**
	 *  c : CellContent \is getEnvi().getCellContent(getPlayer.getWdt(),getPlayer().getHgt()) 
	 * 		\implies c.getCharacter() == getPlayer() &&
	 *  \forall guard : Guard \in getGuards() 
	 *  	c : CellContent \is getEnvi().getCellContent(guard.getWdt(),guard.getHgt())
	 *  		\implies c.getCharacter() == guard &&
	 *  \forall treasure : Treasure \in t 
	 *		c : CellContent \is getEnvi().getCellContent(treasure.getX(),treasure.getY())
	 *  		\implies c.getItem() == ItemType.Treasure 
	 */
	
	public void checkInvariants() {
		CellContent cell_check = getEnvi().getCellContent(getPlayer().getWdt(), getPlayer().getHgt());
		if(cell_check.getCharacter() != getPlayer()) throw new InvariantError("checkInvariants : Le player aux position du player n'est pas le player");
		for(GuardService g : getGuards()) {
			cell_check = getEnvi().getCellContent(g.getWdt(), g.getHgt());
			if(cell_check.getCharacter() != g) throw new InvariantError("checkInvariants : Le guard aux position du guard n'est pas le guard");
			
		}
		for(Coord t : getTreasures()) {
			cell_check = getEnvi().getCellContent(t.getX(), t.getY());
			if(cell_check.getItem().getNature() != ItemType.Treasure) throw new InvariantError("checkInvariants : Il devrait y avoir un trésor en ("+t.getX()+","+t.getY()+")");
			
		}
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
	 *  	&& \forall t : Treasure \in t \without treasure
	 *  		t.getX() != treasure.getX() || t.getY() != treasure.getY()
	 *  
	 */
	@Override
	public void init(EditableScreenService e, Coord player, List<Coord> guards, List<Coord> treasures) {
		//1.pre
		if(!e.isPlayable()) throw new PreconditionError("init : l'ecran n'est pas défini comme jouable");
		// les vérifications concernant la position d'initialisation des éléments sont faites dans charactere, besoin ici aussi ??
		if(e.getCellNature(player.getX(), player.getY()) != Cell.EMP) throw new PreconditionError("init : le player ne peut pas être init dans une case de l'envi non Cell.EMP");
		for(Coord guard : guards) {
			if(e.getCellNature(guard.getX(), guard.getY()) != Cell.EMP) throw new PreconditionError("init : un guard ne peut pas être init dans une case de l'envi non Cell.EMP");
			//check coordonnées égal à un player ou trésor
			if(guard.getX() == player.getX() && guard.getY() == player.getX()) throw new PreconditionError("un guard est sur la même case que le player");
			for(Coord treasure : getTreasures()) {
				if(guard.getX() == treasure.getX() && guard.getY() == treasure.getY()) throw new PreconditionError("un guard est sur la même case qu'un trésor");
			}
		}
		for(Coord treasure : treasures) {
			if(treasure.getX() == player.getX() && treasure.getY() == player.getX()) throw new PreconditionError("un trésor est sur la même case que le player");
			if(e.getCellNature(treasure.getX(), treasure.getY()) != Cell.EMP ||
			   (e.getCellNature(treasure.getX(), treasure.getY()-1) != Cell.PLT && e.getCellNature(treasure.getX(), treasure.getY()-1) != Cell.MTL)) {
				throw new PreconditionError("init : un trésor ne peut pas être init dans une case de l'envi non Cell.EMP");
			}
			for(Coord other : treasures) {
				if (other.equals(treasure)) {
					continue;
				}else {
					if (other.getX()==treasure.getX() && other.getY()==treasure.getY())
						throw new PreconditionError("init : les trésors doivent être initialisés sur des cases distinctes");
				}
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
		// tous les éléments ont été correctement initialisés sur les bonnes positions
		// à revoir pas vraiment très bon
		for(int i = 0;i<getEnvi().getWidth();i++) {
			for(int j = 0;j<getEnvi().getHeight();j++) {
				if(i == getPlayer().getWdt() && j == getPlayer().getHgt()) {
					if(getEnvi().getCellContent(i, j).getCharacter() == getPlayer()) throw new PostconditionError("init : le player à mal été initialisé");	
				}
				for(GuardService g : getGuards()) {
					if(i == g.getWdt() && j == g.getHgt()) {
						if(getEnvi().getCellContent(i, j) != g) throw new PostconditionError("init : un guard à mal été initialisé");	
					}
				}
				for(Coord treasure : getTreasures()) {
					if(i == treasure.getX() && j == treasure.getY()) {
						if(getEnvi().getCellContent(i, j).getItem().getNature() != ItemType.Treasure) throw new PostconditionError("init : un trésor à mal été initialisé");	
					}
				}
			}
		}
	}

	@Override
	public void step() {
		//1.pre
		//none
		//2.checkInvariants
		checkInvariants();
		//3.captures
		ArrayList<Coord> treasure_capture = getTreasures();
		//4.run
		super.step();
		//5.checkInvariants
		checkInvariants();
		//6.post
		//verif que le joueur a fait ce qu'il devait faire en terme de changement de position sur le screen -> voir le step de player peut etre que c'est ici que les post que j'ai ecrit dans player sont veirifiées  
		//verif tous les tresors qui devaient disparaitre ont disparue et ceux qui ne le devaient pas, n'ont pas diparus 
		//si le nombre de trésor a changé, on vérifie qu'un trésor n'as pas été oublié sinon on vérifie que justement un n'aurais pas du disparaitre
		//si l'on est a proximité d'un guard on doit mourrir et changer le gamestate
		//idem si on as ramassé tous les trésors 
		if(getTreasures().size() == 0) {
			if (getStatus() != GameState.Win) throw new PostconditionError("le joueur à ramassé tous les trésors, il aurait du gagné");
		}else {
			if (getStatus() != GameState.Playing) throw new PostconditionError("le joueur n'as pas tout ramassé");
		}
		if(treasure_capture.size() != getTreasures().size()) {
			for(Coord treasure : getTreasures()) {
				if(treasure.getX() == getPlayer().getWdt() && treasure.getY() == getPlayer().getHgt()) throw new PostconditionError("un trésor supplémentaire aurait du disparaître");
			}
		}else {
			for(Coord treasure : treasure_capture) {
				if(treasure.getX() == getPlayer().getWdt() && treasure.getY() == getPlayer().getHgt()) throw new PostconditionError("un trésor aurait du disparaître");
			}
		}
		for(GuardService g : getGuards()) {
			if(g.getWdt()-getPlayer().getWdt() == 1 || g.getWdt()-getPlayer().getWdt() == -1) {
				if(getStatus() != GameState.Loss) throw new PostconditionError("le player aurait du mourir");
			}
			if(g.getHgt()-getPlayer().getHgt() == 1 || g.getHgt()-getPlayer().getHgt() == -1) {
				if(getStatus() != GameState.Loss) throw new PostconditionError("le player aurait du mourir");
			}
		}
	}
}
