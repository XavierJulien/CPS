package loderunner.impl;

import java.util.ArrayList;
import java.util.List;

import loderunner.contracts.EnvironnementContract;
import loderunner.contracts.GuardContract;
import loderunner.contracts.PlayerContract;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.data.Hole;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.services.EditableScreenService;
import loderunner.services.EngineService;
import loderunner.services.EnvironnementService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class EngineImpl implements EngineService{

	protected EnvironnementService envi;
	protected PlayerService player;
	protected ArrayList<GuardService> guards;
	protected ArrayList<Item> treasures;
	protected GameState status;
	protected ArrayList<Command> commands;
	protected ArrayList<Hole> holes;
	protected int score;
	
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
	public void init(EditableScreenService e, Coord player, List<Coord> guards, List<Item> treasures) {
		envi = new EnvironnementContract(new EnvironnementImpl());
		envi.init(e);
		
		this.guards = new ArrayList<>();
		this.status = GameState.Playing;
		this.player = new PlayerContract(new PlayerImpl());
		envi.getCellContent(player.getX(), player.getY()).setCharacter(this.player);
		this.player.init(this,player);
		this.guards = new ArrayList<>();
		for(Coord co : guards) {
			GuardContract guard = new GuardContract(new GuardImpl(-1));
			guard.init(this, co.getX(), co.getY(), getPlayer());
			envi.getCellContent(co.getX(), co.getY()).setGuard(guard);		
			this.guards.add(guard);
		}
		this.treasures = (ArrayList<Item>) treasures;
		for(Item i : treasures) {
			envi.getCellContent(i.getCol(), i.getHgt()).setItem(new Item(i.getCol(), i.getHgt(), ItemType.Treasure));;
		}
		commands = new ArrayList<>();
		holes = new ArrayList<>();
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
				if(envi.getCellContent(h.getX(), h.getY()).getCharacter() != null) {
					status = GameState.Loss;
				}
				if(envi.getCellContent(h.getX(), h.getY()).getGuard() != null) {
					guards.remove(envi.getCellContent(h.getX(), h.getY()).getGuard());
					envi.getCellContent(h.getX(), h.getY()).setGuard(null);
				}
				//pour les gardes
				envi.fill(h.getX(), h.getY());
			}
		}
		if(status == GameState.Playing) {
			//step du player
			player.step();
			if(getEnvi().getCellContent(player.getWdt(), player.getHgt()).getGuard() != null) {
				status = GameState.Loss;
				return;
			}
			//step du guard
			for(GuardService guard : guards) {
				guard.step();
				if(getEnvi().getCellContent(guard.getWdt(), guard.getHgt()).getCharacter() != null) {
					status = GameState.Loss;
					return;
				}
		}
			if(envi.getCellContent(player.getWdt(), player.getHgt()).getItem() != null) {
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
					if(treasures.get(i).getCol() == g.getWdt() && treasures.get(i).getHgt() == g.getHgt() && !g.hasItem()) {
						envi.getCellContent(treasures.get(i).getCol(), treasures.get(i).getHgt()).setItem(null);
						g.setTreasure(treasures.remove(i));
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
		commands.add(c);
	}

	@Override
	public ArrayList<Command> getCommands() {
		return commands;
	}

}
