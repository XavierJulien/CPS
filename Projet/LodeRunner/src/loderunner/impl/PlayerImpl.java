package loderunner.impl;


import loderunner.contracts.EngineContract;
import loderunner.contracts.EngineContract2;
import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.Hole;
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
		if(engine.getEnvi().getCellNature(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()) != Cell.HDR) {
			if(engine.getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.HOL || engine.getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.EMP) {
				super.goDown();
				return;
			}
		}
		if(cmd == null) return;//neutral 
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
				engine.getHoles().add(new Hole(super.getWdt()-1, super.getHgt()-1,0));
				break;
			case DIGR : 
				super.getEnvi().dig(super.getWdt()+1, super.getHgt()-1);
				engine.getHoles().add(new Hole(super.getWdt()+1, super.getHgt()-1,0));
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
		EngineImpl eng = new EngineImpl();
		EngineContract engContract = new EngineContract(eng);
		EditableScreenImpl edit = new EditableScreenImpl();
		edit.init(getEngine().getEnvi().getWidth(), getEngine().getEnvi().getHeight());
		for(int i  = 0;i<edit.getWidth();i++) {
			for(int j = 0;j<edit.getHeight();j++) {
				edit.setNature(i, j, engine.getEnvi().getCellNature(i, j));
			}
		}
		engContract.init(edit, new Coord(getWdt(), getHgt()), null, getEngine().getTreasures());
		p.init(engContract, new Coord(this.getWdt(), this.getHgt()));
		return p;
	}

	@Override
	public PlayerService clonePlayer2() {
		PlayerImpl p = new PlayerImpl();
		EngineImpl eng = new EngineImpl();
		EngineContract2 engContract = new EngineContract2(eng);
		EditableScreenImpl2 edit = new EditableScreenImpl2();
		edit.init(getEngine().getEnvi().getWidth(), getEngine().getEnvi().getHeight());
		for(int i  = 0;i<edit.getWidth();i++) {
			for(int j = 0;j<edit.getHeight();j++) {
				edit.setNature(i, j, engine.getEnvi().getCellNature(i, j));
			}
		}
		engContract.init(edit, new Coord(getWdt(), getHgt()), null, getEngine().getTreasures());
		p.init(engContract, new Coord(this.getWdt(), this.getHgt()));
		return p;
	}


}
