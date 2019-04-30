package loderunner.impl;

import java.util.concurrent.atomic.AtomicInteger;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;
import loderunner.services.ScreenService;

public class GuardImpl extends CharacterImpl implements GuardService {
	private static final AtomicInteger cpt = new AtomicInteger(0); 
	
	private final int id = cpt.incrementAndGet();
	private PlayerService target;
	private int timeInHole = 0;
	
	
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
				if(target.getWdt()-getWdt() > 0) return Command.RIGHT;
				if(target.getWdt()-getWdt() < 0) return Command.LEFT;
				if(target.getWdt()-getWdt() == 0) return Command.NEUTRAL; // à revoir
			}else{
				//suivre l'axe vertical
				if(target.getHgt()-getHgt() > 0) return Command.UP;
				if(target.getHgt()-getHgt() < 0) return Command.DOWN;
				if(target.getHgt()-getHgt() == 0) return Command.NEUTRAL;
			}
		}
		return Command.NEUTRAL;//aucun des cas
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
					//setWdt(getWdt()-1);//a remplacer par des goUp/goLeft car n'implique pas de commande donc pas de step
					//setHgt(getHgt()+1);
					goUp();//equivalent à un setter
					goLeft();
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
					//setWdt(getWdt()+1);//idem
					//setHgt(getHgt()+1);
					goUp();
					goRight();
				}
			}
		}
	}

	
	@Override
	public void step() {
		Cell nat = getEnvi().getCellNature(getWdt(), getHgt());
		Cell nat_under = getEnvi().getCellNature(getWdt(), getHgt()-1);
		if(nat != Cell.LAD && nat != Cell.HDR && nat != Cell.HOL /*suite*/) {} 
	}

}