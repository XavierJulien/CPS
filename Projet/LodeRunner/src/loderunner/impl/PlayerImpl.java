package loderunner.impl;


import loderunner.contracts.EngineContractClone;
import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.Hole;
import loderunner.data.Item;
import loderunner.data.Teleporteur;
import loderunner.services.EngineService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class PlayerImpl extends CharacterImpl implements PlayerService{

	private EngineService engine;
	private Item gauntlet;
	
	@Override
	public EngineService getEngine() {
		return engine;
	}

	@Override
	public void init(EngineService e,Coord player) {
		this.engine = e;
		this.gauntlet = null;
		//this.gauntlet = e.getGauntlet();
		super.init(e.getEnvi(), player.getX(), player.getY(),-1);
	}
	
	@Override
	public void step() {
		getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).setCharacter(null);
		Command cmd = engine.getNextCommand();
		if(engine.getEnvi().getCellNature(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()) != Cell.HDR) {
			if((engine.getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.HOL || 
			    engine.getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.EMP || 
			    engine.getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.HDR) &&
				engine.getEnvi().getCellContent(getWdt(), getHgt()-1).getGuard() == null) {
				super.goDown();
				if(getEngine().getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.TLP) {
					for(Teleporteur tel : getEngine().getTeleporteurs()) {
						if(tel.getPosB().getX() == getWdt() && tel.getPosB().getY() == getHgt()-1) {
							setPos(tel.getPosA().getX(),tel.getPosA().getY()+1);
							break;
						}
						if(tel.getPosA().getX() == getWdt() && tel.getPosA().getY() == getHgt()-1) {
							setPos(tel.getPosB().getX(),tel.getPosB().getY()+1);
						}
					}
				}
				getEnvi().getCellContent(engine.getPlayer().getWdt(), engine.getPlayer().getHgt()).setCharacter(this);
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
				engine.getHoles().add(new Hole(super.getWdt()-1, super.getHgt()-1,0,-1));
				break;
			case DIGR : 
				super.getEnvi().dig(super.getWdt()+1, super.getHgt()-1);
				engine.getHoles().add(new Hole(super.getWdt()+1, super.getHgt()-1,0,-1));
				break;	
			case NEUTRAL :
				break;
			case HITL : 
				hitLeft();
				break;
			case HITR : 
				hitRight();
				break;
		}
		if(getEngine().getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.TLP) {
			for(Teleporteur tel : getEngine().getTeleporteurs()) {
				if(tel.getPosB().getX() == getWdt() && tel.getPosB().getY() == getHgt()-1) {
					setPos(tel.getPosA().getX(),tel.getPosA().getY()+1);
					break;
				}
				if(tel.getPosA().getX() == getWdt() && tel.getPosA().getY() == getHgt()-1) {
					setPos(tel.getPosB().getX(),tel.getPosB().getY()+1);
				}
			}
		}
		getEnvi().getCellContent(getWdt(),getHgt()).setCharacter(this);
	}
	
	@Override
	public String toString() {
		return "["+engine.getPlayer().getWdt()+","+engine.getPlayer().getHgt()+"]";
	}

	@Override
	public PlayerService clonePlayer() {
		PlayerImplClone p = new PlayerImplClone();
		EngineImplClone eng = new EngineImplClone();
		EngineContractClone engContract = new EngineContractClone(eng);
		EditableScreenImpl edit = new EditableScreenImpl();
		edit.init(getEngine().getEnvi().getWidth(), getEngine().getEnvi().getHeight());
		for(int i  = 0;i<edit.getWidth();i++) {
			for(int j = 0;j<edit.getHeight();j++) {
				edit.setNature(i, j, getEngine().getEnvi().getCellNature(i, j));
			}
		}
		engContract.init(edit, new Coord(getWdt(), getHgt()), getEngine().getGuardsCoord(), getEngine().getTreasures(),getEngine().getTeleporteurs(),getEngine().getGauntlet());
		p.init(engContract, new Coord(this.getWdt(), this.getHgt()));
		return p;
	}

	@Override
	public boolean hasGauntlet() {
		if(gauntlet == null) return false;
		return true;
	}
	
	@Override
	public Item getGauntlet() {
		return gauntlet;
	}

	@Override
	public void setGauntlet(Item gauntlet) {
		this.gauntlet = gauntlet;
	}

	@Override
	public void hitRight() {
		for(int i = getWdt(); i<getEnvi().getWidth();i++) {
			if(getEnvi().getCellNature(i, getHgt()) == Cell.MTL ||
			   getEnvi().getCellNature(i, getHgt()) == Cell.PLT ||
			   getEnvi().getCellNature(i, getHgt()) == Cell.TLP) {
				break;
			}
			if(getEnvi().getCellContent(i, getHgt()).getGuard() != null) {
				GuardService g = getEnvi().getCellContent(i, getHgt()).getGuard();
				getEnvi().getCellContent(i, getHgt()).setGuard(null);
				getEngine().getGuards().remove(g);
				getEnvi().getCellContent(i, getHgt()).setItem(g.getTreasure());
				break;
			}
		}
		setGauntlet(null);
	}
	
	@Override
	public void hitLeft() {
		for(int i = getWdt(); i>=0;i--) {
			if(getEnvi().getCellNature(i, getHgt()) == Cell.MTL ||
			   getEnvi().getCellNature(i, getHgt()) == Cell.PLT ||
			   getEnvi().getCellNature(i, getHgt()) == Cell.TLP) {
				break;
			}
			if(getEnvi().getCellContent(i, getHgt()).getGuard() != null) {
				GuardService g = getEnvi().getCellContent(i, getHgt()).getGuard();
				getEnvi().getCellContent(i, getHgt()).setGuard(null);
				getEngine().getGuards().remove(g);
				getEnvi().getCellContent(i, getHgt()).setItem(g.getTreasure());
				break;
			}
		}
		setGauntlet(null);
	}
}
