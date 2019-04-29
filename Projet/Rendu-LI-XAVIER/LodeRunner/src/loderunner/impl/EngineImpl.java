package loderunner.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.services.EditableScreenService;
import loderunner.services.EngineService;
import loderunner.services.EnvironnementService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class EngineImpl implements EngineService{

	protected EnvironnementService envi;
	protected PlayerService player;
	protected ArrayList<GuardService> guards;
	protected ArrayList<Coord> treasures;
	protected GameState status;
	protected ArrayList<Command> commands;
	protected ListIterator<Command> lit = commands.listIterator(commands.size());
	
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
	public ArrayList<Coord> getTreasures() {
		return treasures;
	}

	@Override
	public GameState getStatus() {
		return status;
	}

	@Override
	public Command getNextCommand() {
		if(lit.hasNext()) {
			return lit.next();
		}
		return null;
	}

	public ListIterator<Command> getLI(){
		return lit;
	}
	
	@Override
	public void init(EditableScreenService e, Coord player, List<Coord> guards, List<Coord> treasures) {
		envi.init(e);
		this.player.init(envi, player.getX(), player.getY());
		/*for(Coord co : guards) {
			this.guards.add(new GuardService(co.getX(),co.getY()));
		}*/
		this.treasures = (ArrayList<Coord>)treasures;
	}

	@Override
	public void step() {
		player.step();
		//for(GuardService guard : guards) guard.step();
	}

}
