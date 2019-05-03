package loderunner.impl;

import java.util.concurrent.atomic.AtomicInteger;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Item;
import loderunner.errors.PreconditionError;
import loderunner.services.EngineService;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;

public class GuardImpl extends CharacterImpl implements GuardService {
	private static final AtomicInteger cpt = new AtomicInteger(0); 
	
	private final int id;
	private PlayerService target;
	private int timeInHole = 0;
	private EngineService engine;
	private Item treasure = null;
	
	public GuardImpl(int id) {
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
		if(treasure == null) throw new PreconditionError("setTreasure : trésor à null");
		//2.run
		this.treasure = treasure;
	}
	
	
	@Override
	public Command getBehaviour() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		Cell nat_under = getEnvi().getCellNature(getWdt(), getHgt()-1);
		if (nat==Cell.LAD 
				&& (nat_under==Cell.PLT || nat_under==Cell.MTL || getEnvi().getCellContent(getWdt(), getHgt()).getCharacter() != null)) {
			if (Math.abs(target.getWdt()-getWdt()) > Math.abs(target.getHgt()-getHgt())){
				//suivre l'axe  horizontal
				if(target.getWdt()-getWdt() > 0) return Command.RIGHT;
				if(target.getWdt()-getWdt() < 0) return Command.LEFT;
				if(target.getWdt()-getWdt() == 0) return Command.NEUTRAL; // Ã  revoir
			}else{
				//suivre l'axe vertical
				if(target.getHgt()-getHgt() > 0) return Command.UP;
				if(target.getHgt()-getHgt() < 0) return Command.DOWN;
				if(target.getHgt()-getHgt() == 0) return Command.NEUTRAL;
			}
		}else{
		if (nat == Cell.LAD) {
			if (target.getHgt()>getHgt()) return Command.UP;
			if (target.getHgt()<getHgt()) return Command.DOWN;
			if (target.getHgt()==getHgt()) return Command.NEUTRAL;
		}else{
		if (nat==Cell.HOL || nat==Cell.HDR 
			|| nat_under==Cell.MTL || nat_under==Cell.PLT
			|| getEnvi().getCellContent(getWdt(), getHgt()).getCharacter() != null) {
			if (target.getWdt()>getWdt()) return Command.RIGHT;
			if (target.getWdt()<getWdt()) return Command.LEFT;
			if (target.getWdt()==getWdt()) return Command.NEUTRAL;
		}
		}}
		return Command.NEUTRAL;//aucun des cas
	}

	@Override
	public int getTimeInHole() {
		return timeInHole;
	}
	

	@Override
	public void init(EngineService e, int x, int y, PlayerService target) {
		super.init(e.getEnvi(), x, y);
		this.target = target;
		engine = e;
	}

	@Override
	public void climbLeft() {
		if (getEnvi().getCellNature(getWdt(), getHgt()+1) != Cell.PLT 
			&& getEnvi().getCellNature(getWdt(), getHgt()+1) != Cell.MTL) {
			if (getEnvi().getCellNature(getWdt()-1, getHgt()+1) != Cell.PLT 
				&& getEnvi().getCellNature(getWdt()-1, getHgt()+1) != Cell.MTL) {
				if (getEnvi().getCellContent(getWdt()-1, getHgt()+1).getCharacter() == null) {
					setWdt(getWdt()-1);
					setHgt(getHgt()+1);
					timeInHole=0;
				}
			}
		}
	}

	@Override
	public void climbRight() {
		if (getEnvi().getCellNature(getWdt(), getHgt()+1) != Cell.PLT 
			&& getEnvi().getCellNature(getWdt(), getHgt()+1) != Cell.MTL) {
			if (getEnvi().getCellNature(getWdt()+1, getHgt()+1) != Cell.PLT 
				&& getEnvi().getCellNature(getWdt()+1, getHgt()+1) != Cell.MTL) {
				if (getEnvi().getCellContent(getWdt()+1, getHgt()+1).getCharacter() == null) {
					setWdt(getWdt()+1);
					setHgt(getHgt()+1);
					timeInHole=0;
				}
			}
		}
	}

	
	@Override
	public void step() {
		getEngine().getEnvi().getCellContent(getWdt(), getHgt()).setGuard(null);
		if (willFall()) {
			goDown();
			if(getEngine().getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.HOL) {
				if(treasure != null) {
					getEngine().getEnvi().getCellContent(getWdt(), getHgt()+1).setItem(treasure);
					treasure = null;
				}
			}
		}else{
		if (willWaitInHole()) {
			timeInHole+=timeEpsilon;
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
						System.out.println("goleft");
					}
					break;
				case RIGHT:
					goRight();
					if(treasure != null) {
						treasure.setCol(this.getWdt()+1);
						treasure.setHgt(this.getHgt());
						System.out.println("goright");
					}
					break;
				case DOWN : 
					goDown();
					if(treasure != null) {
						treasure.setCol(this.getWdt());
						treasure.setHgt(this.getHgt()-1);
						System.out.println("goup");
					}
					break;
				case UP:
					goUp();
					if(treasure != null) {
						treasure.setCol(this.getWdt()+1);
						treasure.setHgt(this.getHgt());
						System.out.println("godown");
					}
					break;
				default:
					break;
			}}}}}}
		getEngine().getEnvi().getCellContent(getWdt(), getHgt()).setGuard(this);	
	}



}