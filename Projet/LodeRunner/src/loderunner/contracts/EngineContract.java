package loderunner.contracts;

import java.util.ArrayList;
import java.util.List;

import loderunner.data.Cell;
import loderunner.data.Item;
import loderunner.data.CellContent;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.data.Hole;
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

	protected EngineService delegate;
	
	public EngineContract(EngineService delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	/*protected EngineService getDelegate() {
		return (EngineService) super.delegate;
	}*/
	
	public void checkInvariants() {
		CellContent cell_check = getEnvi().getCellContent(getPlayer().getWdt(), getPlayer().getHgt());
		if(!cell_check.getCharacter().equals(getPlayer())) throw new InvariantError("checkInvariants : Le player aux position du player n'est pas le player");
		for(GuardService g : getGuards()) {
			cell_check = getEnvi().getCellContent(g.getWdt(), g.getHgt());
			if(cell_check.getCharacter() != g) throw new InvariantError("checkInvariants : Le guard aux position du guard n'est pas le guard");
			
		}
		for(Item t : getTreasures()) {
			cell_check = getEnvi().getCellContent(t.getCol(), t.getHgt());
			if(cell_check.getItem().getNature() != ItemType.Treasure) throw new InvariantError("checkInvariants : Il devrait y avoir un trésor en ("+t.getCol()+","+t.getHgt()+")");
			
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
	public ArrayList<Item> getTreasures() {
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
	
	@Override
	public ArrayList<Hole> getHoles() {
		//1.pre
		//2.checkInvariants
		//none
		//4.run
		return super.getHoles();
	}
	
	@Override
	public ArrayList<Command> getCommands() {
		//1.pre
		//2.checkInvariants
		//none
		//4.run
		return super.getCommands();
	}
	
	@Override
	public void init(EditableScreenService e, Coord player, List<Coord> guards, List<Item> treasures) {
		//1.pre
		if(!e.isPlayable()) throw new PreconditionError("init : l'ecran n'est pas défini comme jouable");
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
					if(getEnvi().getCellContent(i, j).getCharacter() != getPlayer()) {
						throw new PostconditionError("init : le player à mal été initialisé");	
					}
				}
				for(GuardService g : getGuards()) {
					if(i == g.getWdt() && j == g.getHgt()) {
						if(getEnvi().getCellContent(i, j) != g) throw new PostconditionError("init : un guard à mal été initialisé");	
					}
				}
				for(Item treasure : getTreasures()) {
					if(i == treasure.getCol() && j == treasure.getHgt()) {
						if(getEnvi().getCellContent(i, j).getItem().getNature() != ItemType.Treasure) throw new PostconditionError("init : un trésor à mal été initialisé");	
					}
				}
			}
		}
		for(Item treasure : treasures) {
			if(treasure.getCol() == player.getX() && treasure.getHgt() == player.getY()) throw new PreconditionError("un trésor est sur la même case que le player");
			if(e.getCellNature(treasure.getCol(), treasure.getHgt()) != Cell.EMP &&
			   (e.getCellNature(treasure.getCol(), treasure.getHgt()-1) != Cell.PLT || e.getCellNature(treasure.getCol(), treasure.getHgt()-1) != Cell.MTL)) {
				throw new PreconditionError("init : un trésor ne peut pas être init dans une case de l'envi non Cell.EMP");
			}
			for(Item other : treasures) {
				if (other.equals(treasure)) {
					continue;
				}else {
					if (other.getCol()==treasure.getCol() && other.getHgt()==treasure.getHgt())
						throw new PreconditionError("init : les trésors doivent être initialisés sur des cases distinctes");
				}
			}
		}
		for(Coord guard : guards) {
			if(e.getCellNature(guard.getX(), guard.getY()) != Cell.EMP) throw new PreconditionError("init : un guard ne peut pas être init dans une case de l'envi non Cell.EMP");
			//check coordonnées égal à un player ou trésor
			if(guard.getX() == player.getX() && guard.getY() == player.getX()) throw new PreconditionError("un guard est sur la même case que le player");
			for(Item treasure : getTreasures()) {
				if(guard.getX() == treasure.getCol() && guard.getY() == treasure.getHgt()) throw new PreconditionError("un guard est sur la même case qu'un trésor");
			}
		}
	}

	@Override
	public void addCommand(Command c) {
		//1.pre
		//none
		//2.checkInvariants
		checkInvariants();
		//3.capture
		int size_command = getCommands().size();
		//4.run
		super.addCommand(c);
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(getCommands().size() != size_command+1)
			throw new PostconditionError("addCommand : on n'as pas ajouté de commande après un addCommand");
		//peut etre verifier que ya un nouvel ajout dans la liste de commandes avec la capture
			
	}
	
	@Override
	public void step() {
		//1.pre
		//none
		//2.checkInvariants
		checkInvariants();
		//3.captures
		ArrayList<Item> treasure_capture = getTreasures();
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
			if (getStatus() == GameState.Win) throw new PostconditionError("le joueur n'as pas tout ramassé");
		}
		if(treasure_capture.size() != getTreasures().size()) {
			for(Item treasure : getTreasures()) {
				if(treasure.getCol() == getPlayer().getWdt() && treasure.getHgt() == getPlayer().getHgt()) throw new PostconditionError("un trésor supplémentaire aurait du disparaître");
			}
		}else {
			for(Item treasure : treasure_capture) {
				if(treasure.getCol() == getPlayer().getWdt() && treasure.getHgt() == getPlayer().getHgt()) throw new PostconditionError("un trésor aurait du disparaître");
			}
		}
		for(GuardService g : getGuards()) {
			if(g.getWdt()-getPlayer().getWdt() == 1 || g.getWdt()-getPlayer().getWdt() == -1) {
				if(getStatus() != GameState.Loss) throw new PostconditionError("le player aurait du mourir");
			}
		}
	}
	
}
