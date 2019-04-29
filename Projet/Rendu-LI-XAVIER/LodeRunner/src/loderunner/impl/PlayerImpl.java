package loderunner.impl;

import loderunner.data.Command;
import loderunner.services.EngineService;
import loderunner.services.PlayerService;

public class PlayerImpl extends CharacterImpl implements PlayerService{

	private EngineService engine;
	
	@Override
	public EngineService getEngine() {
		return engine;
	}

	@Override
	public void step() {
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
		}
	}
}
