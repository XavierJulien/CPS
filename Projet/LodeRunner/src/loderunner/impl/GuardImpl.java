package loderunner.impl;

import java.util.concurrent.atomic.AtomicInteger;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.errors.PostconditionError;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;
import loderunner.services.ScreenService;

public class GuardImpl extends CharacterImpl implements GuardService {
	private static final AtomicInteger cpt = new AtomicInteger(0); 
	
	private final int id = cpt.incrementAndGet();
	private PlayerService target;
	private int timeInHole;
	
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public PlayerService getTarget() {
		return target;
	}

	@Override
	public Command getBehaviour() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		Cell nat_under = getEnvi().getCellNature(getWdt(), getHgt()-1);
		if (nat == Cell.LAD) {
			if (target.getHgt()>getHgt()) return Command.UP;
			if (target.getHgt()<getHgt()) return Command.DOWN;
			if (target.getHgt()==getHgt()) return Command.NEUTRAL;
		}
		if (nat==Cell.HOL || nat==Cell.HDR 
			|| nat_under==Cell.MTL || nat_under==Cell.PLT
			|| getEnvi().getCellContent(getWdt(), getHgt()).getCharacter() != null) {
			if (target.getWdt()>getWdt()) return Command.RIGHT;
			if (target.getWdt()<getWdt()) return Command.LEFT;
			if (target.getWdt()==getWdt()) return Command.NEUTRAL;
		}
		if (nat==Cell.LAD 
			&& (nat_under==Cell.PLT || nat_under==Cell.MTL || getEnvi().getCellContent(getWdt(), getHgt()).getCharacter() != null)) {
			if (Math.abs(target.getWdt()-getWdt()) > Math.abs(target.getHgt()-getHgt())){
				//suivre l'axe  horizontal
				
			}else{
				//suivre l'axe vertical
			}
		}
	}

	@Override
	public int getTimeInHole() {
		return timeInHole;
	}
	

	@Override
	public void init(ScreenService s, int x, int y, PlayerService target) {
		super.init(s, x, y);
		this.target = target;
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
				}
			}
		}
	}

}