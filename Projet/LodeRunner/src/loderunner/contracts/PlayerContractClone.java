package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.Hole;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
import loderunner.errors.PreconditionError;
import loderunner.main.Creator;
import loderunner.services.EngineService;
import loderunner.services.PlayerService;

public class PlayerContractClone extends CharacterContractClone implements PlayerService{

	private final PlayerService delegate;
	
	public PlayerContractClone(PlayerService delegate) {
		super(delegate);
		this.delegate = delegate;
	}
	
	public void checkInvariants() {
		super.checkInvariants();
		if(getEnvi().getCellContent(getWdt(), getHgt()).getCharacter() != null) {
			if(!getEnvi().getCellContent(getWdt(), getHgt()).getCharacter().equals(this)) {
				throw new InvariantError("le joueur dans la case de notre joueur n'est pas lui-m�me");				
			}
		}
	}
	
	@Override
	public EngineService getEngine() {
		//1.pre
		//none
		//2.run
		return delegate.getEngine();
	}

	@Override
	public void step() {
		//1.pre
		//none
		//2.checkInvariants
		checkInvariants();
		
		//3.capture
		Cell digl_capture = null, digr_capture = null;
		int hgt_capture = getHgt(),wdt_capture = getWdt();
		PlayerContractClone capture_self = this;
		Command command_capture = getEngine().getNextCommand();
		if(getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.HDR && 
		   getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD && 
		   getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() == null &&
		  (getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.EMP || getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.HOL)) {
			command_capture = Command.DOWN;
		}
		PlayerContractClone clone;
		if(getEngine().getEnvi().getCellNature(getWdt(), getHgt()) == Cell.EMP) {
			clone = Creator.createPlayerContractClone(delegate.clonePlayer());
			clone.getEnvi().getCellContent(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()).setCharacter(null);
			clone.init(getEngine(), new Coord(getEngine().getPlayer().getWdt(),getEngine().getPlayer().getHgt()));
			clone.getEnvi().getCellContent(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()).setCharacter(clone);

		}else {
			clone = Creator.createPlayerContractClone(delegate.clonePlayer2());
			clone.getEnvi().getCellContent(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()).setCharacter(null);
			clone.init(getEngine(), new Coord(getEngine().getPlayer().getWdt(),getEngine().getPlayer().getHgt()));
			clone.getEnvi().getCellContent(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()).setCharacter(clone);

		}
		if(getEngine().getPlayer().getWdt() >= 1) {
			digl_capture = getEngine().getEnvi().getCellNature(wdt_capture-1, hgt_capture-1);
		}
		if(getEngine().getPlayer().getWdt() <= getEngine().getEnvi().getWidth()-2) {
			digr_capture = getEngine().getEnvi().getCellNature(wdt_capture+1, hgt_capture-1);
		}
		
		//4.run
		getEngine().addCommand(command_capture);
		if(getEngine().getEnvi().getCellNature(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()) != Cell.HDR &&
		   getEngine().getEnvi().getCellContent(getWdt(), getHgt()-1).getGuard() == null &&
		  (getEngine().getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.HOL || 
		   getEngine().getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.EMP || 
		   getEngine().getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.HDR)) {
				clone.goDown();
		}else {
			switch(command_capture) {
				case UP : {clone.goUp();break;}
				case DOWN : {clone.goDown();break;}
				case RIGHT : {clone.goRight();break;}
				case LEFT : {clone.goLeft();break;}
				case NEUTRAL : break;
				case DIGL : {clone.getEnvi().dig(clone.getWdt()-1, clone.getHgt()-1);clone.getEngine().getHoles().add(new Hole(clone.getWdt()-1, clone.getHgt()-1,0));break;}
				case DIGR : {clone.getEnvi().dig(clone.getWdt()+1, clone.getHgt()-1);clone.getEngine().getHoles().add(new Hole(clone.getWdt()+1, clone.getHgt()-1,0));break;}
			}
		}
		
		getEnvi().getCellContent(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()).setCharacter(null);
		delegate.step();
		getEnvi().getCellContent(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()).setCharacter(capture_self);
		
		//5.checkInvariants
		checkInvariants();
		
		//6.post
		
		
		if(clone.getHgt() != getHgt() || clone.getWdt() != getWdt()) throw new PostconditionError("Player Step : le joueur n'as pas la bonne position");
		if (getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD && 
			getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.HDR) {
			if (getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.MTL && 
				getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.PLT && 
				getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.LAD) {
				if (getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() == null) {
					if (getHgt() != hgt_capture-1) throw new PostconditionError("Player step : le joueur devrait être en chute libre");
				}
			}
		}
		if (command_capture == Command.DIGL) {
			if(digl_capture == Cell.PLT && clone.getEngine().getEnvi().getCellNature(wdt_capture-1, hgt_capture-1) != Cell.HOL) throw new PostconditionError("player step : une case aurait du etre creuser a gauche coté contract");
			if (getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT|| 
				getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.MTL|| 
				getEnvi().getCellContent(wdt_capture, hgt_capture-1) != null ) {
				if (getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.EMP ||
					getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.LAD ||
					getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.HDR ||
					getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.HOL) {
					if (getEnvi().getCellNature(wdt_capture-1, hgt_capture-1) == Cell.PLT &&
						getEnvi().getCellNature(wdt_capture-1, hgt_capture-1) != Cell.HOL) {
							throw new PostconditionError("player step : le joueur devait creuser à gauche");
					}
				}
			}
		}
		if (command_capture == Command.DIGR) {
			if(digr_capture == Cell.PLT && clone.getEngine().getEnvi().getCellNature(wdt_capture+1, hgt_capture-1) != Cell.HOL) throw new PostconditionError("player step : une case aurait du etre creuser a droite coté contract");
			if (getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT
					|| getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.MTL
					|| getEnvi().getCellContent(wdt_capture, hgt_capture-1) != null ) {
				if (getEnvi().getCellNature(wdt_capture+1, hgt_capture) == Cell.EMP
						||getEnvi().getCellNature(wdt_capture+1, hgt_capture) == Cell.LAD
						||getEnvi().getCellNature(wdt_capture+1, hgt_capture) == Cell.HDR
						||getEnvi().getCellNature(wdt_capture+1, hgt_capture) == Cell.HOL) {
					if (getEnvi().getCellNature(wdt_capture+1, hgt_capture-1) == Cell.PLT && 
						getEnvi().getCellNature(wdt_capture+1, hgt_capture-1) != Cell.HOL) {
							throw new PostconditionError("player step : le joueur devait creuser à gauche");
					}
				}
				
			}
		}
	}

	@Override
	public void init(EngineService e, Coord c) {
		//1.pre
		if(e == null || c == null) throw new PostconditionError("init player : un des paramètres est null");
		if(c.getX() >= e.getEnvi().getWidth() || c.getX() < 0 || c.getY() >= e.getEnvi().getHeight() || c.getY() < 0) {
			throw new PreconditionError("init player : une coordonnée est mauvaise");
		}
		//2.checkInvariants
		//none
		//3.capture
		//4.run
		delegate.init(e, c);
		super.init(e.getEnvi(), c.getX(), c.getY(),-1);
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(this.getWdt() != c.getX() || this.getHgt() != c.getY()) throw new PostconditionError("init player : une coordonnée à mal été initialisé");
		if(!this.getEngine().equals(e)) throw new PostconditionError("init player : engine à mal été initialisé");
		
	}

	@Override
	public PlayerService clonePlayer() {
		return null;//never used
	}

	@Override
	public PlayerService clonePlayer2() {
		return null;//never used
	}
	
}
