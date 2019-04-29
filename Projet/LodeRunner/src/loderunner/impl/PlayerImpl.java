package loderunner.impl;


import loderunner.data.Command;
import loderunner.data.GameState;
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
		if(getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).getItem() != null) {
			getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).setItem(null);
			for(int i = 0;i<engine.getTreasures().size();i++) {
				if(engine.getTreasures().get(i).getCol() == engine.getPlayer().getWdt() && engine.getTreasures().get(i).getHgt() == engine.getPlayer().getHgt()) {
					engine.getTreasures().remove(i);
				}
			}
			if(engine.getTreasures().isEmpty()) engine.status = GameState.Win;
		}
		getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).setCharacter(this);
	}
	
	@Override
	public String toString() {
		return "["+engine.getPlayer().getWdt()+","+engine.getPlayer().getHgt()+"]";
	}
}
