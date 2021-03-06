package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.errors.PostconditionError;
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
		//empty ?
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
		int hgt_capture = getHgt();
		int wdt_capture = getWdt();
		//4.run
		delegate.step();
		//5.checkInvariants
		checkInvariants();
		//6.post
		if (getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD 
				&& getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.HDR) {
			if (getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.EMP) {
				if (getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() == null) {
					if (getHgt() != hgt_capture-1) throw new PostconditionError("Player step : le joueur devrait être en chute libre");
				}
			}
		}
		if (getEngine().getNextCommand() == Command.DIGL) {
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
		if (getEngine().getNextCommand() == Command.DIGR) {
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

	
}
