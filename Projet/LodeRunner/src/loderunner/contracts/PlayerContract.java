package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.data.Coord;
import loderunner.data.Hole;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
import loderunner.main.Creator;
import loderunner.services.CharacterService;
import loderunner.services.EngineService;
import loderunner.services.PlayerService;

public class PlayerContract extends CharacterContract implements PlayerService{

	private final PlayerService delegate;
	
	public PlayerContract(PlayerService delegate) {
		super(delegate);
		this.delegate = delegate;
	}
	
	/*@Override
	protected PlayerService getDelegate() {
		return (PlayerService) super.getDelegate();
	}*/

	public void checkInvariants() {
		super.checkInvariants();
		if(getEnvi().getCellContent(getWdt(), getHgt()).getCharacter() != null) {
			System.out.println("dede:"+this);
			System.out.println("sese:"+getEnvi().getCellContent(getWdt(), getHgt()).getCharacter());
			if(!getEnvi().getCellContent(getWdt(), getHgt()).getCharacter().equals(this))
				throw new InvariantError("le joueur dans la case de notre joueur n'est pas lui-même");
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
		Cell digl_capture = null,digr_capture = null;
		int hgt_capture = getHgt();
		int wdt_capture = getWdt();
		Command command_capture = getEngine().getNextCommand();
		if(getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.HDR && getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD && getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.EMP || getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.HOL)command_capture = Command.DOWN;
		getEngine().addCommand(command_capture);
		PlayerContract clone;
		if(getEngine().getEnvi().getCellNature(getWdt(), getHgt()) == Cell.EMP) {
			clone = Creator.createPlayerContract(delegate.clonePlayer());			
		}else {
			clone = Creator.createPlayerContract(delegate.clonePlayer2());
		}
		if(getEngine().getPlayer().getWdt() >= 1) digl_capture = getEngine().getEnvi().getCellNature(wdt_capture-1, hgt_capture-1);
		if(getEngine().getPlayer().getWdt() <= getEngine().getEnvi().getWidth()-2) digr_capture = getEngine().getEnvi().getCellNature(wdt_capture+1, hgt_capture-1);
		
		//4.run
		System.out.println("clone    "+clone);
		System.out.println("clone delegate"+clone.delegate);
		switch(command_capture) {
			case UP : {clone.goUp();break;}
			case DOWN : {clone.goDown();break;}
			case RIGHT : {clone.goRight();break;}
			case LEFT : {clone.goLeft();break;}
			case NEUTRAL : break;
			case DIGL : {clone.getEnvi().dig(clone.getWdt()-1, clone.getHgt()-1);clone.getEngine().getHoles().add(new Hole(clone.getWdt()-1, clone.getHgt()-1,0));break;}
			case DIGR : {clone.getEnvi().dig(clone.getWdt()+1, clone.getHgt()-1);clone.getEngine().getHoles().add(new Hole(clone.getWdt()+1, clone.getHgt()-1,0));break;}
		}
		CharacterService c = getEnvi().getCellContent(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()).getCharacter();
		getEnvi().getCellContent(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()).setCharacter(null);
		delegate.step();
		getEnvi().getCellContent(getEngine().getPlayer().getWdt(), getEngine().getPlayer().getHgt()).setCharacter(c);
		
		//5.checkInvariants
		checkInvariants();
		
		//6.post
		
		
		if(clone.getHgt() != getHgt() || clone.getWdt() != getWdt()) throw new PostconditionError("Player Step : le joueur n'as pas la bonne position");
		if (getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD 
				&& getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.HDR) {
			if (getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.MTL && getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.PLT && getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.LAD) {
				if (getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() == null) {
					if (getHgt() != hgt_capture-1) throw new PostconditionError("Player step : le joueur devrait être en chute libre");
				}
			}
		}
		if (command_capture == Command.DIGL) {
			if(digl_capture == Cell.PLT && clone.getEngine().getEnvi().getCellNature(wdt_capture-1, hgt_capture-1) != Cell.HOL) throw new PostconditionError("player step : une case aurait du etre creuser a gauche coté contract");
			if (getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT
					|| getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.MTL
					|| getEnvi().getCellContent(wdt_capture, hgt_capture-1) != null ) {
				if (getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.EMP
						||getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.LAD
						||getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.HDR
						||getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.HOL) {
					if (getEnvi().getCellNature(wdt_capture-1, hgt_capture-1) == Cell.PLT) {
						if (getEnvi().getCellNature(wdt_capture-1, hgt_capture-1) != Cell.HOL)
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
					if (getEnvi().getCellNature(wdt_capture+1, hgt_capture-1) == Cell.PLT) {
						if (getEnvi().getCellNature(wdt_capture+1, hgt_capture-1) != Cell.HOL)
							throw new PostconditionError("player step : le joueur devait creuser à gauche");
					}
				}
				
			}
		}
	}

	@Override
	public void init(EngineService e, Coord c) {
		//1.pre
		//2.checkInvariants
		//3.capture
		//4.run
		delegate.init(e, c);
		//super.init(e.getEnvi(), c.getX(), c.getY());
		//5.checkInvariants
		checkInvariants();
		//6.post
	}

	@Override
	public PlayerService clonePlayer() {
		return new PlayerContract(delegate);
	}

	@Override
	public PlayerService clonePlayer2() {
		return new PlayerContract(delegate);
	}
	
}
