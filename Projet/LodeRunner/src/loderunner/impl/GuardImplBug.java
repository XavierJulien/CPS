package loderunner.impl;

import java.util.concurrent.atomic.AtomicInteger;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Item;
import loderunner.errors.PreconditionError;
import loderunner.services.EngineService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class GuardImplBug extends CharacterImpl implements GuardService {
	private static final AtomicInteger cpt = new AtomicInteger(0); 
	
	private final int id;
	private PlayerService target;
	private int timeInHole = 0;
	private EngineService engine;
	private Item treasure = null;
	
	public GuardImplBug(int id) {
		if (id==-1) {			
			this.id = cpt.incrementAndGet();
		}else{			
			this.id = id;
		}
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public PlayerService getTarget() {
		return target;
	}

	@Override
	public EngineService getEngine() {
		return engine;
	}

	@Override
	public boolean hasItem() {
		if(treasure != null) return true;
		return false;
	}
	
	@Override
	public void setTreasure(Item treasure) {
		//1.pre 
		if(treasure == null) throw new PreconditionError("setTreasure : tr�sor � null");
		//2.run
		this.treasure = treasure;
	}
	
	
	@Override
	public Command getBehaviour() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		Cell nat_under = getEnvi().getCellNature(getWdt(), getHgt()-1);
		switch(nat) {
			case EMP : {
				if (nat_under==Cell.PLT || 
					nat_under==Cell.MTL ||
					nat_under==Cell.TLP ||
					getEnvi().getCellContent(getWdt(), getHgt()-1).getGuard() != null){
								if(target.getWdt()-getWdt() > 0) return Command.RIGHT;
								if(target.getWdt()-getWdt() < 0) return Command.LEFT;
								if(target.getWdt()-getWdt() == 0) return Command.NEUTRAL;
					}
				if(nat_under==Cell.LAD) {
					if (Math.abs(target.getWdt()-getWdt()) > Math.abs(target.getHgt()-getHgt())){
						//suivre l'axe  horizontal
						if(target.getWdt()-getWdt() > 0) return Command.RIGHT;
						if(target.getWdt()-getWdt() < 0) return Command.LEFT;
						if(target.getWdt()-getWdt() == 0) return Command.NEUTRAL;
					}else{
						//suivre l'axe vertical
						if(target.getHgt()-getHgt() > 0) return Command.UP;
						if(target.getHgt()-getHgt() < 0) return Command.DOWN;
						if(target.getHgt()-getHgt() == 0) return Command.NEUTRAL;
					}
				}
				return Command.DOWN;
			}
			case LAD :{
				if(nat_under==Cell.PLT || 
				   nat_under==Cell.MTL || 
				   nat_under==Cell.TLP ||
				   getEnvi().getCellContent(getWdt(), getHgt()-1).getGuard() != null) {
					if (Math.abs(target.getWdt()-getWdt()) > Math.abs(target.getHgt()-getHgt())){
						//suivre l'axe  horizontal
						if(target.getWdt()-getWdt() > 0) return Command.RIGHT;
						if(target.getWdt()-getWdt() < 0) return Command.LEFT;
						if(target.getWdt()-getWdt() == 0) return Command.NEUTRAL;
					}else{
						//suivre l'axe vertical
						if(target.getHgt()-getHgt() > 0) return Command.UP;
						if(target.getHgt()-getHgt() < 0) {
							if(target.getWdt()-getWdt() > 0) return Command.RIGHT;
							if(target.getWdt()-getWdt() < 0) return Command.LEFT;
						}
						if(target.getHgt()-getHgt() == 0) return Command.NEUTRAL;
					}
				}else {
					if (Math.abs(target.getWdt()-getWdt()) > Math.abs(target.getHgt()-getHgt())){
						//suivre l'axe  horizontal
						if(target.getWdt()-getWdt() > 0) return Command.RIGHT;
						if(target.getWdt()-getWdt() < 0) return Command.LEFT;
						if(target.getWdt()-getWdt() == 0) return Command.NEUTRAL;
					}else{
						//suivre l'axe vertical
						if(target.getHgt()-getHgt() > 0) return Command.UP;
						if(target.getHgt()-getHgt() < 0) return Command.DOWN;
						if(target.getHgt()-getHgt() == 0) return Command.NEUTRAL;
					}
				}
			}
			case HDR : {
				if(nat_under == Cell.EMP || nat_under == Cell.LAD) {
					if (Math.abs(target.getWdt()-getWdt()) > Math.abs(target.getHgt()-getHgt())){
						//suivre l'axe  horizontal
						if(target.getWdt()-getWdt() > 0) return Command.RIGHT;
						if(target.getWdt()-getWdt() < 0) return Command.LEFT;
						if(target.getWdt()-getWdt() == 0) return Command.NEUTRAL;
					}else{
						//suivre l'axe vertical
						if(target.getHgt()-getHgt() > 0) return Command.UP;
						if(target.getHgt()-getHgt() < 0) return Command.DOWN;
						if(target.getHgt()-getHgt() == 0) return Command.NEUTRAL;
					}
				}
				if (target.getWdt()>getWdt()) return Command.RIGHT;
				if (target.getWdt()<getWdt()) return Command.LEFT;
				if (target.getWdt()==getWdt()) return Command.NEUTRAL;
			}
			case HOL : {
				if (target.getWdt()>getWdt()) return Command.RIGHT;
				if (target.getWdt()<getWdt()) return Command.LEFT;
				if (target.getWdt()==getWdt()) return Command.LEFT;
			}
			default : 
				return Command.NEUTRAL;
		
		}
		
	}

	@Override
	public int getTimeInHole() {
		return timeInHole;
	}
	

	@Override
	public void init(EngineService e, int x, int y, PlayerService target) {
		super.init(e.getEnvi(), x, y, id);
		this.target = target;
		engine = e;
	}

	@Override
	public void climbLeft() {//bug
		setWdt(getWdt()-1);
		setHgt(getHgt()+1);
		timeInHole=0;
	}

	@Override
	public void climbRight() {//bug
		setWdt(getWdt()+1);
		setHgt(getHgt()+1);
		timeInHole=0;
	}

	
	@Override
	public void step() {
		getEngine().getEnvi().getCellContent(getWdt(), getHgt()).setGuard(null);
		if (willFall()) {
			goDown();
			if(getEngine().getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL) {
				if(treasure != null) {
					getEngine().getEnvi().getCellContent(getWdt(), getHgt()+1).setItem(treasure);
					treasure = null;
				}
			}
		}else{
		if (willWaitInHole()) {
			waitInHole();
		}else{
		if (willClimbLeft()) {
			climbLeft();
		}else{
		if (willClimbRight()) {
			climbRight();
		}else{
		if (willClimbNeutral()) {
			//nothing to do ?
		}else {
			Command behaviour = getBehaviour();
			switch (behaviour) {
				case LEFT:
					goLeft();
					if(treasure != null) {
						treasure.setCol(this.getWdt()-1);
						treasure.setHgt(this.getHgt());
					}
					break;
				case RIGHT:
					goRight();
					if(treasure != null) {
						treasure.setCol(this.getWdt()+1);
						treasure.setHgt(this.getHgt());
					}
					break;
				case DOWN : 
					goDown();
					if(treasure != null) {
						treasure.setCol(this.getWdt());
						treasure.setHgt(this.getHgt()-1);
					}
					break;
				case UP:
					goUp();
					if(treasure != null) {
						treasure.setCol(this.getWdt()+1);
						treasure.setHgt(this.getHgt());
					}
					break;
				default:
					break;
			}}}}}}
		getEngine().getEnvi().getCellContent(getWdt(), getHgt()).setGuard(this);	
	}

	@Override
	public void waitInHole() {
		timeInHole = timeInHole+(2*timeEpsilon); //bug
	}
}