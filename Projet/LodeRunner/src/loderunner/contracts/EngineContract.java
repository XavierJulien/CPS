package loderunner.contracts;

import java.util.ArrayList;
import java.util.List;


import loderunner.data.Cell;
import loderunner.data.Item;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.data.Hole;
import loderunner.data.Teleporteur;
import loderunner.decorators.EngineDecorator;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
import loderunner.errors.PreconditionError;
import loderunner.impl.EngineImpl;
import loderunner.impl.GuardImpl;
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

	public EngineImpl getDelegate() {
		return (EngineImpl)delegate;
	}

	public void checkInvariants() {

		/**
		 *  Lâ€™observateur Environment doit eË†tre synchronise Ì� avec les observateurs Guards, Player et Treasure.
		 *  Ainsi si G âˆˆ Guards(E) est tel que Hgt(G)=4 et Col(G)=3, alors G âˆˆ CellContent(Environment(E),3,4).
		 */
		for (int i=0;i<getEnvi().getWidth();i++) {
			for (int j=0;j<getEnvi().getHeight();j++) {
				//player
				if (i==getPlayer().getWdt() && j==getPlayer().getHgt()) {
					if (getEnvi().getCellContent(i, j).getCharacter() != getPlayer())
						throw new InvariantError("l'environnement n'est pas synchronisÃ© avec le joueur" );
				}
				//guards
				for (GuardService g : getGuards()) {
					if (i==g.getWdt() && j==g.getHgt()) {
						if (getEnvi().getCellContent(i, j).getGuard() != g)
							throw new InvariantError("l'environnement n'est pas synchronisÃ© avec un garde");
					}
				}
				//treasures
				for (Item t : getTreasures()) {
					if (i==t.getCol() && j==t.getHgt()) {
						if (getEnvi().getCellContent(i, j).getItem() != t) {
							throw new InvariantError("l'environnement n'est pas synchronisÃ© avec un trÃ©sor");
						}
					}
				}
			}
		}

		/**
		 *  *  Le jeu est gagne Ì� quand il nâ€™y a plus de tre Ì�sors.
		 */

		if (getTreasures().size() == 0) { // plus de tresor sur la map
			boolean treasureOnGuard = false;
			for (GuardService g : getGuards()) { // plus de tresor sur aucun garde
				if (g.hasItem()) {
					treasureOnGuard = true;
					break;
				}
			}
			if (!treasureOnGuard) {
				if (getStatus() != GameState.Win) throw new InvariantError("le joueur a ramassÃ© tous les trÃ©sors, il aurait du gagnÃ©");
			}else {
				if (getStatus() == GameState.Win) throw new InvariantError("statut Ã  WIN alors que le joueur n'as pas tout ramassÃ©");
			}
		} else {
			if (getStatus() == GameState.Win) throw new InvariantError("statut Ã  WIN alors que le joueur n'as pas tout ramassÃ©");
		}


		/*
		CellContent cell_check = getEnvi().getCellContent(getPlayer().getWdt(), getPlayer().getHgt());
		if(!cell_check.getCharacter().equals(getPlayer())) throw new InvariantError("checkInvariants : Le player aux position du player n'est pas le player");
		for(GuardService g : getGuards()) {
			cell_check = getEnvi().getCellContent(g.getWdt(), g.getHgt());
			if(!cell_check.getGuard().equals(g)) throw new InvariantError("checkInvariants : Le guard aux position du guard n'est pas le guard");
			for(Item t : getTreasures()) {
				cell_check = getEnvi().getCellContent(t.getCol(), t.getHgt());
				if(cell_check.getItem() != null && t.getCol() == g.getWdt() && t.getHgt() == g.getHgt() && !g.hasItem()) throw new InvariantError("checkInvariants : il ne devrait plus y avoir de trï¿½sor ï¿½ cette case : ["+g.getWdt()+","+g.getHgt()+"]");

			}
		}
		for(Item t : getTreasures()) {
			cell_check = getEnvi().getCellContent(t.getCol(), t.getHgt());
			if(cell_check.getItem().getNature() != ItemType.Treasure) throw new InvariantError("checkInvariants : Il devrait y avoir un trï¿½sor en ("+t.getCol()+","+t.getHgt()+")");
			for(GuardService g : getGuards()) {
				if(cell_check.getItem() == null && (t.getCol() != g.getWdt() || t.getHgt() != g.getHgt())) throw new InvariantError("checkInvariants : il devrait y avoir un trï¿½sor ï¿½ cette case : ["+t.getCol()+","+t.getHgt()+"]");

			}

		}*/
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
		if (getCommands().isEmpty())
			throw new PostconditionError("getNextCommand : il n'y a pas de commande en attente");
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
	public ArrayList<Teleporteur> getTeleporteurs() {
		//1.pre
		//2.checkInvariants
		//none
		//4.run
		return super.getTeleporteurs();
	}

	@Override
	public Item getGauntlet() {
		//1.pre
		//2.checkInvariants
		//none
		//4.run
		return super.getGauntlet();
	}

	@Override
	public void init(EditableScreenService e, Coord player, List<Coord> guards, List<Item> treasures,List<Teleporteur> teleporteurs,Item gauntlet) {
		//1.pre
		if(!e.isPlayable()) throw new PreconditionError("init : l'ecran n'est pas dÃ©fini comme jouable");
		if(player.getY() == gauntlet.getCol() && player.getY() == gauntlet.getHgt()) throw new PreconditionError("init : un player ne peut pas Ãªtre init dans une case qui contient un gant");
		for(Item treasure : treasures) {
			if(treasure.getCol() == player.getX() && treasure.getHgt() == player.getY()) throw new PreconditionError("un trÃ©sor est sur la mÃªme case que le player");
			if(e.getCellNature(treasure.getCol(), treasure.getHgt()) != Cell.EMP &&
			   (e.getCellNature(treasure.getCol(), treasure.getHgt()-1) != Cell.PLT ||
			    e.getCellNature(treasure.getCol(), treasure.getHgt()-1) != Cell.MTL ||
			    e.getCellNature(treasure.getCol(), treasure.getHgt()-1) != Cell.TLP)) {
				throw new PreconditionError("init : un trÃ©sor ne peut pas Ãªtre init dans une case de l'envi non Cell.EMP");
			}
			if(treasure.getCol() == gauntlet.getCol() && treasure.getHgt() == gauntlet.getHgt()) throw new PreconditionError("init : un trÃ©sor ne peut pas Ãªtre init dans une case qui contient un gant");
		}
		for(Coord guard : guards) {
			//deja check dans le init du joueur ->if(e.getCellNature(guard.getX(), guard.getY()) != Cell.EMP) throw new PreconditionError("init : un guard ne peut pas Ãªtre init dans une case de l'envi non Cell.EMP");
			//check coordonnÃ©es Ã©gal Ã  un player ou trÃ©sor ou un autre guard
			if(guard.getX() == player.getX() && guard.getY() == player.getY()) throw new PreconditionError("un guard est sur la mÃªme case que le player");
			for(Item treasure : treasures) {
				if(guard.getX() == treasure.getCol() && guard.getY() == treasure.getHgt()) throw new PreconditionError("un guard est sur la mÃªme case qu'un trÃ©sor");
			}
			for(Coord other : guards) {
				if (other.equals(guard)) continue;
				if (other.getX()==guard.getX() && other.getY()==guard.getY())
					throw new PreconditionError("init : un garde ne peut avoir les mÃªme coord que le joueur Ã  l'initialisation");

			}
		}
		for(Item treasure : treasures) {
			if(treasure.getCol() < 0 || treasure.getCol() >= e.getWidth() || treasure.getHgt() < 0 || treasure.getHgt() > e.getHeight())
				throw new PreconditionError("init : un trÃ©sor n'a pas les bons coordonnÃ©es pour fit l'environnnement");
			if(treasure.getCol() == player.getX() && treasure.getHgt() == player.getY())
				throw new PreconditionError("un trÃ©sor est sur la mÃªme case que le player");
			if(e.getCellNature(treasure.getCol(), treasure.getHgt()) != Cell.EMP &&
			   (e.getCellNature(treasure.getCol(), treasure.getHgt()-1) != Cell.PLT || e.getCellNature(treasure.getCol(), treasure.getHgt()-1) != Cell.MTL)) {
				throw new PreconditionError("init : un trÃ©sor ne peut pas Ãªtre init dans une case de l'envi non Cell.EMP sans support PLT MTL en dessous ");
			}

			for(Item other : treasures) {
				if (other.equals(treasure)) {
					continue;
				}else {
					if (other.getCol()==treasure.getCol() && other.getHgt()==treasure.getHgt())
						throw new PreconditionError("init : les trÃ©sors doivent Ãªtre initialisÃ©s sur des cases distinctes");
				}
			}
		}

		for(Coord guard : guards) {
			if(e.getCellNature(guard.getX(), guard.getY()) != Cell.EMP) throw new PreconditionError("init : un guard ne peut pas Ãªtre init dans une case de l'envi non Cell.EMP");
			//check coordonnÃ©es Ã©gal Ã  un player ou trÃ©sor
			if(guard.getX() == player.getX() && guard.getY() == player.getY()) throw new PreconditionError("un guard est sur la mÃªme case que le player");
			if(guard.getX() == gauntlet.getCol() && guard.getY() == gauntlet.getHgt()) throw new PreconditionError("init : un guard ne peut pas Ãªtre init dans une case qui contient un gant");
			for(Item treasure : treasures) {
				if(guard.getX() == treasure.getCol() && guard.getY() == treasure.getHgt()) throw new PreconditionError("un guard est sur la mÃªme case qu'un trÃ©sor");
			}
		}
		for(Teleporteur teleporteur : teleporteurs) {
			if(e.getCellNature(teleporteur.getPosA().getX(), teleporteur.getPosA().getY()) != Cell.PLT ||
			   e.getCellNature(teleporteur.getPosB().getX(), teleporteur.getPosB().getY()) != Cell.PLT) throw new PreconditionError("un teleporteur n'est pas init dans une case PLT");
		}

		//2.checkInvariants
		//none
		//3.captures
		//none
		//4.run
		super.init(e, player, guards, treasures,teleporteurs,gauntlet);
		//5.checkInvariants
		checkInvariants();
		//6.post
		// tous les Ã©lÃ©ments ont Ã©tÃ© correctement initialisÃ©s sur les bonnes positions
		for(int i = 0;i<getEnvi().getWidth();i++) {
			for(int j = 0;j<getEnvi().getHeight();j++) {
				if(e.getCellNature(i, j) != getEnvi().getCellNature(i, j))
					throw new PostconditionError("init : une case de l'envi est de nature diffÃ©rente du screen passÃ© en argument");
			}
		}

		if (getStatus() != GameState.Playing)
			throw new PostconditionError("init : status n'est pas Ã  playing");

		if (getScore() != 0) {
			throw new PostconditionError("init : le score n'est pas 0");
		}

		if (!getEnvi().getCellContent(player.getX(), player.getY()).getCharacter().equals(getPlayer()))
			throw new PostconditionError("init : le joueur a Ã©tÃ© mal initialisÃ© dans l'environnement");

		if (!getCommands().isEmpty())
			throw new PostconditionError("init : il devrait y avoir 0 commande dans la liste Ã  l'initalisation");

		if (!getHoles().isEmpty())
			throw new PostconditionError("init : il devrait y avoir 0 trou Ã  l'initialisation");

		for (Coord g : guards) {
			if (getEnvi().getCellContent(g.getX(), g.getY()).getGuard() == null)
				throw new PostconditionError("init : un guard Ã  mal Ã©tÃ© initialisÃ©");
		}

		for (Item i : treasures) {
			if (getEnvi().getCellContent(i.getCol(),i.getHgt()).getItem() == null)
				throw new PostconditionError("les items n'ont pas Ã©tÃ© correctement initialisÃ© dans l'environement");
		}

		for(Teleporteur teleporteur : teleporteurs) {
			if(e.getCellNature(teleporteur.getPosA().getX(), teleporteur.getPosA().getY()) != Cell.TLP ||
				 e.getCellNature(teleporteur.getPosB().getX(), teleporteur.getPosB().getY()) != Cell.TLP) throw new PreconditionError("un teleporteur n'est pas init correctement");
		}

		/*
		for (int i = 0;i<getEnvi().getWidth();i++) {
			for (int j = 0;j<getEnvi().getHeight();j++) {
				if (e.getCellNature(i, j) != getEnvi().getCellNature(i, j))
					throw new PostconditionError("init : une case de l'envi est de nature diffÃ©rente du screen passÃ© en argument");
				if (i == getPlayer().getWdt() && j == getPlayer().getHgt()) {
					if (getEnvi().getCellContent(i, j).getCharacter() != getPlayer()) {
						throw new PostconditionError("init : le player a mal Ã©tÃ© initialisÃ©");
					}
				}
				for (GuardService g : getGuards()) {
					if (i == g.getWdt() && j == g.getHgt()) {
						if (!getEnvi().getCellContent(i, j).getGuard().equals(g)) throw new PostconditionError("init : un guard Ã  mal Ã©tÃ© initialisÃ©");
					}
				}
				for (Item treasure : getTreasures()) {
					if (i == treasure.getCol() && j == treasure.getHgt()) {
						if (getEnvi().getCellContent(i, j).getItem().getNature() != ItemType.Treasure) throw new PostconditionError("init : un trÃ©sor Ã  mal Ã©tÃ© initialisÃ©");
					}
				}
			}

		}
		}*/

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
			throw new PostconditionError("addCommand : on n'as pas ajoutÃ© de commande aprÃ¨s un addCommand");
		//peut etre verifier que ya un nouvel ajout dans la liste de commandes avec la capture

	}

	@Override
	public void step() {
		//1.pre
		//none
		//2.checkInvariants
		checkInvariants();
		//3.captures

		PlayerService player_capture ;
		player_capture = getPlayer().clonePlayer();
		ArrayList<GuardService> guards_capture = new ArrayList<>();
		for (GuardService g : getGuards()) {
			GuardService newguard = new GuardImpl(g.getId());
			newguard.init(this, g.getWdt(), g.getHgt(), player_capture);
			guards_capture.add(newguard);

		}
		ArrayList<Item> treasures_capture = new ArrayList<>();
		for (Item i : getTreasures()) {
			treasures_capture.add(i);
		}

		ArrayList<Hole> holes_capture = new ArrayList<>();
		for (Hole h : getHoles()) {
			holes_capture.add(new Hole(h.getX(), h.getY(), h.getT(), h.getId()));
		}
		int score_capture = getScore();
		int commandsSize_capture = getCommands().size();

		//4.run
		super.step();
		//5.checkInvariants
		checkInvariants();
		//6.post

		/**
		 * Si au de Ì�but dâ€™un tour, le joueur se trouve sur une case contenant un tre Ì�sor, ce tre Ì�sor disparait.
		 **/
		for (Item i : treasures_capture) {
			boolean staying = true;
			if (i.getCol() == getPlayer().getWdt() && i.getHgt() == getPlayer().getHgt()) {
				staying = false;
				for (Item t : getTreasures()) {
					if (i.equals(t)) // si je retrouve le meme item dans la post liste -> erreur
						throw new PostconditionError("un trÃ©sor n'a pas disparu de la liste alors que le joueur est dans la mÃªme case");
				}
				if (getScore() != score_capture+1)
					throw new PostconditionError("le score ne s'est pas incrÃ©mentÃ© alors que le joueur a rÃ©cupÃ©rÃ© un trÃ©sor");
			}
			for (GuardService g : getGuards()) {
				if (i.getCol() == g.getWdt() && i.getHgt() == g.getHgt()) {
					staying = false;
					for (Item t : getTreasures()) {
						if (i.equals(t))
							throw new PostconditionError("un trÃ©sor n'a pas disparu de la liste alors qu'un garde est dans la mÃªme case");
					}
				}
			}
			if (staying) {
				boolean ok = false;
				for (Item t : getTreasures()) {
					if (t.getCol() == i.getCol() && t.getHgt() == i.getHgt()) {
						ok=true;
						break;
					}
				}
				if (!ok)
					throw new PostconditionError("un trÃ©sor qui ne devait pas disparaitre a disparu");
			}
		}
		/**
		*  Si au de Ì�but dâ€™un tour, un garde est dans la meË†me case que le joueur, le jeu est perdu
		*/
		for (GuardService g : guards_capture) {
			if (g.getWdt() == player_capture.getWdt() && g.getHgt() == player_capture.getHgt()) {
				if (getStatus() != GameState.Loss)
					throw new PostconditionError("garde et joueur se sont croisÃ©s mais le statut du jeu n'est pas passÃ© Ã  loss");
			}
		}

		/**
		 * Verifier les trous
		 */
		for (Hole h : holes_capture) {
			if (h.getT() < 14) {
				boolean stillthere = false;
				for (Hole hpost : getHoles()) {
					if (h.getId() == hpost.getId()) {
						stillthere = true;
						if (hpost.getT() != h.getT()+1)
							throw new PostconditionError("le timer d'un trou ne s'est pas correctement incrÃ©mentÃ©");
					}
				}
				if (!stillthere)
					throw new PostconditionError("un trou a disparu alors que son timer n'est pas encore a 15");
			}else {
				if (getEnvi().getCellContent(h.getX(), h.getY()).getCharacter() != null)
					if (getStatus() != GameState.Loss)
						throw new PostconditionError("le trou s'est fill avec le joueur a l'intÃ©rieur, le statut du jeu aurait du devenir LOSS");
				for (GuardService g : guards_capture) { // verif que le garde coincÃ© s'est rÃ©initalise a une autre position
					if (g.getWdt() == h.getX() && g.getHgt() == h.getY())
						for (GuardService gpost : getGuards()) {
							if (gpost.getId() == g.getId())
								if (gpost.getWdt() == g.getWdt() && gpost.getHgt() == g.getHgt())
									throw new PostconditionError("un garde Ã©tait dans le trou quand fill, mais le garde ne s'est pas rÃ©initialisÃ© a sa pos de dÃ©part");
						}
				}
				for (Hole hpost : getHoles()) {
					if (h.getId() == hpost.getId())
						throw new PostconditionError("timer d'un trou Ã  15 , ce trou aurait du disparaitre");
				}
			}
		}


		if (getCommands().size() != commandsSize_capture-1)
			throw new PostconditionError("le nombre de commande n'a pas dÃ©crÃ©mentÃ©");
	}

}
