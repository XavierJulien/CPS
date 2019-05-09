package loderunner.impl;

import java.util.ArrayList;
import java.util.List;

import loderunner.contracts.EnvironnementContract;
import loderunner.contracts.GuardContract;
import loderunner.contracts.PlayerContract;
import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.data.Hole;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.data.Teleporteur;
import loderunner.services.EditableScreenService;
import loderunner.services.EngineService;
import loderunner.services.EnvironnementService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class EngineImpl implements EngineService{

	protected final EnvironnementService envi = new EnvironnementContract(new EnvironnementImpl());
	protected final ArrayList<GuardService> guards_at_init = new ArrayList<>();
	protected PlayerService player;
	protected ArrayList<GuardService> guards;
	protected ArrayList<Item> treasures;
	protected GameState status;
	protected ArrayList<Command> commands;
	protected ArrayList<Hole> holes;
	protected int score;

	protected ArrayList<Teleporteur> teleporteurs;
	protected Item gauntlet;

	@Override
	public EnvironnementService getEnvi() {
		return envi;
	}

	@Override
	public PlayerService getPlayer() {
		return player;
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		return guards;
	}

	@Override
	public ArrayList<Item> getTreasures() {
		return treasures;
	}

	@Override
	public GameState getStatus() {
		return status;
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}

	@Override
	public Command getNextCommand() {
		return commands.remove(0);
	}

	@Override
	public ArrayList<Hole> getHoles() {
		return holes;
	}

	public ArrayList<Coord> getGuardsCoord(){
		ArrayList<Coord> res = new ArrayList<>();
		for(GuardService g: guards) {
			res.add(new Coord(g.getWdt(),g.getHgt()));
		}
		return res;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public ArrayList<Teleporteur> getTeleporteurs() {
		return teleporteurs;
	}

	 @Override
	public Item getGauntlet() {
		return gauntlet;
	}

	@Override
	public void init(EditableScreenService e,
			         Coord player,
			         List<Coord> guards,
			         List<Item> treasures,
			         List<Teleporteur> teleporteurs,
			         Item gauntlet) {
		for(Teleporteur t : teleporteurs) {
			e.setNature(t.getPosA().getX(), t.getPosA().getY(), Cell.TLP);
			e.setNature(t.getPosB().getX(), t.getPosB().getY(), Cell.TLP);
		}
		envi.init(e);

		this.guards = new ArrayList<>();
		this.status = GameState.Playing;
		this.treasures = (ArrayList<Item>) treasures;
		this.player = new PlayerContract(new PlayerImpl());
		this.teleporteurs = (ArrayList<Teleporteur>) teleporteurs;
		commands = new ArrayList<>();
		holes = new ArrayList<>();
		envi.getCellContent(player.getX(), player.getY()).setCharacter(this.player);
		this.player.init(this,player);
		for(Coord co : guards) {
			GuardContract guard = new GuardContract(new GuardImpl(-1));
			guard.init(this, co.getX(), co.getY(), getPlayer());
			envi.getCellContent(co.getX(), co.getY()).setGuard(guard);
			this.guards.add(guard);
		}
		for(GuardService g : this.guards) {
			GuardContract guardcopy = new GuardContract(new GuardImpl(g.getId()));
			guardcopy.init(this, g.getWdt(), g.getHgt(), getPlayer());
			this.guards_at_init.add(guardcopy);
		}
		for(Item i : treasures) {
			envi.getCellContent(i.getCol(), i.getHgt()).setItem(i);;
		}
		this.gauntlet = gauntlet;
		envi.getCellContent(gauntlet.getCol(), gauntlet.getHgt()).setItem(gauntlet);
		score = 0;
	}

	@Override
	public void step() {
		//step des trous
		for(int i = 0;i<holes.size();i++) {
			Hole h = holes.get(i);
			h.setT(h.getT()+1);
			if(h.getT() == 15) {
				holes.remove(h);
				i = i-1;
				if(envi.getCellContent(h.getX(), h.getY()).getCharacter() != null) {
					PlayerContract playercapture = (PlayerContract) envi.getCellContent(h.getX(), h.getY()).getCharacter();
					status = GameState.Loss;
					envi.getCellContent(h.getX(), h.getY()).setCharacter(null);
					envi.fill(h.getX(), h.getY());
					envi.getCellContent(h.getX(), h.getY()).setCharacter(playercapture);
					getNextCommand();
					return;
				}
				if(envi.getCellContent(h.getX(), h.getY()).getGuard() != null) {
					for(GuardService g : guards_at_init) {
						if(g.getId() == envi.getCellContent(h.getX(), h.getY()).getGuard().getId()) {
							guards.remove(envi.getCellContent(h.getX(), h.getY()).getGuard());
							GuardContract guardcopy = new GuardContract(new GuardImpl(g.getId()));
							guardcopy.init(this, g.getWdt(), g.getHgt(), getPlayer());
							guards.add(guardcopy);
							envi.getCellContent(g.getWdt(), g.getHgt()).setGuard(guardcopy);
						}
					}
					Item item = null;
					if(envi.getCellContent(h.getX(), h.getY()+1).getItem() != null) {
						item = envi.getCellContent(h.getX(), h.getY()+1).getItem();
						envi.getCellContent(h.getX(), h.getY()+1).setItem(null);
					}
					envi.getCellContent(h.getX(), h.getY()).setGuard(null);
					envi.fill(h.getX(), h.getY());
					if(item != null) envi.getCellContent(h.getX(), h.getY()+1).setItem(item);
				}else {
					envi.fill(h.getX(), h.getY());
				}
			}
		}
		if(status == GameState.Playing) {
			//step du player
			player.step();
			if(envi.getCellContent(player.getWdt(), player.getHgt()).getItem() != null &&
			   envi.getCellContent(player.getWdt(), player.getHgt()).getItem().getNature() == ItemType.Gauntlet) {
				player.setGauntlet(envi.getCellContent(player.getWdt(), player.getHgt()).getItem());
				envi.getCellContent(player.getWdt(), player.getHgt()).setItem(null);
			}
			if(getEnvi().getCellContent(player.getWdt(), player.getHgt()).getGuard() != null) {
				status = GameState.Loss;
				return;
			}
			//step du guard
			for(GuardService guard : guards) {
				if(guard.willClimbLeft() || guard.willClimbRight()) {
					if(getEnvi().getCellContent(guard.getWdt(), guard.getHgt()+1).getCharacter() != null) {
						status = GameState.Loss;
						return;
					}
					if(getEnvi().getCellContent(guard.getWdt(), guard.getHgt()+1).getItem() != null && 
					   getEnvi().getCellContent(guard.getWdt(), guard.getHgt()+1).getItem().getNature() == ItemType.Treasure) {
						guard.setTreasure(getEnvi().getCellContent(guard.getWdt(), guard.getHgt()+1).getItem());
						treasures.remove(getEnvi().getCellContent(guard.getWdt(), guard.getHgt()+1).getItem());
						getEnvi().getCellContent(guard.getWdt(), guard.getHgt()+1).setItem(null);
					}
				}
				guard.step();
				if(getEnvi().getCellContent(guard.getWdt(), guard.getHgt()).getCharacter() != null) {
					status = GameState.Loss;
					return;
				}
			}
			if(envi.getCellContent(player.getWdt(), player.getHgt()).getItem() != null &&
			   envi.getCellContent(player.getWdt(), player.getHgt()).getItem().getNature() == ItemType.Treasure) {
				envi.getCellContent(player.getWdt(), player.getHgt()).setItem(null);
				for(int i = 0;i<treasures.size();i++) {
					if(treasures.get(i).getCol() == player.getWdt() && treasures.get(i).getHgt() == player.getHgt()) {
						treasures.remove(i);
						score +=1;
					}
				}
			}
			for(int i = 0;i<treasures.size();i++) {
				for(GuardService g : guards) {
					if(treasures.get(i).getCol() == g.getWdt() &&
					   treasures.get(i).getHgt() == g.getHgt() &&
					   !g.hasItem()) {
						envi.getCellContent(treasures.get(i).getCol(), treasures.get(i).getHgt()).setItem(null);
						g.setTreasure(treasures.remove(i));
						continue;
					}
					if(treasures.get(i).getCol() == g.getWdt() &&
					   treasures.get(i).getHgt() == g.getHgt()) {
					}
				}
			}
		}
		if(treasures.isEmpty()) {
			for(GuardService g : guards) {
				if(g.hasItem()) {
					return;
				}
			}
			status = GameState.Win;
		}
	}

	@Override
	public void addCommand(Command c) {
		commands.add(0,c);
	}


	public void setPlayer(PlayerService player) {
		this.player = player;
	}


}
