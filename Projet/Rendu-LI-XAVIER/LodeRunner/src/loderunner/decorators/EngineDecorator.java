package loderunner.decorators;

import java.util.ArrayList;
import java.util.List;

import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.GameState;
import loderunner.services.EditableScreenService;
import loderunner.services.EngineService;
import loderunner.services.EnvironnementService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class EngineDecorator implements EngineService{

	protected final EngineService delegate;
	
	public EngineDecorator(EngineService delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public EnvironnementService getEnvi() {
		return delegate.getEnvi();
	}

	@Override
	public PlayerService getPlayer() {
		return delegate.getPlayer();
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		return delegate.getGuards();
	}

	@Override
	public ArrayList<Coord> getTreasures() {
		return delegate.getTreasures();
	}

	@Override
	public GameState getStatus() {
		return delegate.getStatus();
	}

	@Override
	public Command getNextCommand() {
		return delegate.getNextCommand();
	}

	@Override
	public void init(EditableScreenService e, Coord player, List<Coord> guards, List<Coord> treasures) {
		delegate.init(e, player, guards, treasures);
		
	}

	@Override
	public void step() {
		delegate.step();
	}

}
