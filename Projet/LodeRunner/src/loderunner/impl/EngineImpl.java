package loderunner.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.data.Item;
import loderunner.data.ItemType;
import loderunner.services.EditableScreenService;
import loderunner.services.EngineService;
import loderunner.services.GuardService;

public class EngineImpl implements EngineService{

	protected EnvironnementImpl envi;
	protected PlayerImpl player;
	protected ArrayList<GuardService> guards;
	protected ArrayList<Coord> treasures;
	protected GameState status;
	protected ArrayList<Command> commands;
	protected ListIterator<Command> lit;
	
	@Override
	public EnvironnementImpl getEnvi() {
		
		return envi;
	}

	@Override
	public PlayerImpl getPlayer() {
		
		return player;
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		return guards;
	}

	@Override
	public ArrayList<Coord> getTreasures() {
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

	public ListIterator<Command> getLI(){
		return lit;
	}
	
	@Override
	public void init(EditableScreenService e, Coord player, List<Coord> guards, List<Coord> treasures) {
		envi = new EnvironnementImpl();
		envi.init(e);
		
		this.player = new PlayerImpl();
		this.player.init(envi, player.getX(), player.getY());
		this.player.setEngine(this);
		
		/*for(Coord co : guards) {
			this.guards.add(new GuardService(co.getX(),co.getY()));
		}*/
		
		this.treasures = (ArrayList<Coord>)treasures;
		for(int i = 0;i<treasures.size();i++) envi.getCellContent(treasures.get(i).getX(), treasures.get(i).getY()).setItem(new Item(treasures.get(i).getX(), treasures.get(i).getY(), ItemType.Treasure));;
		
		envi.getCellContent(player.getX(), player.getY()).setCharacter(this.player);
		commands = new ArrayList<>();
		lit = commands.listIterator();
	}

	@Override
	public void step() {
		player.step();
		//for(GuardService guard : guards) guard.step();
		if(treasures.isEmpty()) {
			System.out.println("WELLPLAYED");
		}
	}

	
	public ArrayList<Command> getCommands() {
		return commands;
	}

}
