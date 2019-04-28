package loderunner.impl;


import loderunner.data.Command;
import loderunner.services.EngineService;
import loderunner.services.PlayerService;

public class PlayerImpl extends CharacterImpl implements PlayerService{

	private EngineImpl engine;
	
	@Override
	public EngineService getEngine() {
		return engine;
	}

	public void setEngine(EngineImpl engine) {
		this.engine = engine;
	}
	
	@Override
	public void step() {
		if(getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).getItem() != null) {
			getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).setItem(null);
		}
		getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).setCharacter(null);
		Command cmd = engine.getCommands().get(0);
		engine.getCommands().remove(0);
		//Command cmd = engine.getNextCommand();
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
		}
		
		getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).setCharacter(this);
	}
	
	@Override
	public String toString() {
		return "["+engine.getPlayer().getWdt()+","+engine.getPlayer().getHgt()+"]";
	}
}
