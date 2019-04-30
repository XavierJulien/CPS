package loderunner.impl;


import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.services.EngineService;
import loderunner.services.PlayerService;

public class PlayerImpl extends CharacterImpl implements PlayerService{

	private EngineService engine;
	
	@Override
	public EngineService getEngine() {
		return engine;
	}

	@Override
	public void init(EngineService e,Coord player) {
		this.engine = e;
		super.init(e.getEnvi(), player.getX(), player.getY());
	}
	
	@Override
	public void step() {
		getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).setCharacter(null);
		Command cmd = engine.getNextCommand();
		if(cmd == null) return;
		switch(cmd) {
			case UP : 
				super.goUp();
				break;
			case DOWN : 
				super.goDown();
				break;
			case LEFT : 
				super.goLeft();
				break;
			case RIGHT : 
				super.goRight();
				break;
			case DIGL :
				super.getEnvi().dig(super.getWdt()-1, super.getHgt()-1);
				break;
			case DIGR : 
				super.getEnvi().dig(super.getWdt()+1, super.getHgt()-1);
				break;	
			case NEUTRAL :
				break;
		}
		getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).setCharacter(this);
	}
	
	@Override
	public String toString() {
		return "["+engine.getPlayer().getWdt()+","+engine.getPlayer().getHgt()+"]";
	}

	@Override
	public PlayerService clonePlayer() {
		PlayerImpl p = new PlayerImpl();
		p.init(this.engine, new Coord(this.getWdt(), this.getHgt()));
		return p;
	}


}
