package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.data.Command;
import loderunner.errors.PostconditionError;
import loderunner.errors.PreconditionError;
import loderunner.services.GuardService;
import loderunner.services.PlayerService;
import loderunner.services.ScreenService;

public class GuardContract extends CharacterContract implements GuardService {

	private final GuardService delegate;
	
	public GuardContract(GuardService delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@Override
	public int getId() {
		return delegate.getId();
	}

	@Override
	public PlayerService getTarget() {
		return delegate.getTarget();
	}

	@Override
	public Command getBehaviour() {
		return delegate.getBehaviour();
	}

	@Override
	public int getTimeInHole() {
		return delegate.getTimeInHole();
	}
	
	

	@Override
	public void init(ScreenService s, int x, int y, PlayerService target) {
		//pre 
		if(s.getCellNature(x, y) != Cell.EMP) {
			throw new PreconditionError("init : la case ou on veut init le player n'est pas Cell.EMP");
		}
		if(getEnvi().getCellContent(x, y).getCharacter() != null)
			throw new PreconditionError("init : le case contient déjà qqn"); 
		//inv
		//appel
		delegate.init(s, x, y, target);
		//inv
		super.checkInvariants();
		//post
		if (!getTarget().equals(target))
			throw new PostconditionError("init guard : target");
		if (getTimeInHole()!=0)
			throw new PostconditionError("init guard : timeinhole");
	}

	@Override
	public void climbLeft() {
		//pre
		if (getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HOL)
			throw new PreconditionError("climbleft : n'est pas dans un trou");
		//inv
		super.checkInvariants();
		//captures
		int wdt_atpre = getWdt();
		int hgt_atpre = getHgt();
		//appel
		delegate.climbLeft();
		//inv
		super.checkInvariants();
		//post
		if (getEnvi().getCellNature(wdt_atpre, hgt_atpre+1) != Cell.PLT 
			&& getEnvi().getCellNature(wdt_atpre, hgt_atpre+1) != Cell.MTL) {
			if (getEnvi().getCellNature(wdt_atpre-1, hgt_atpre+1) != Cell.PLT 
				&& getEnvi().getCellNature(wdt_atpre-1, hgt_atpre+1) != Cell.MTL) {
				if (getEnvi().getCellContent(wdt_atpre-1, hgt_atpre+1).getCharacter() == null) {
					if (getWdt() != wdt_atpre-1 || getHgt() != hgt_atpre+1)
						throw new PostconditionError("climbLeft : le guard n'a pas correctement grimpé à gauche");
				}
			}
		}
	}

	@Override
	public void climbRight() {
		//pre
		if (getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HOL)
			throw new PreconditionError("climbright : n'est pas dans un trou");
		//inv
		super.checkInvariants();
		//captures
		int wdt_atpre = getWdt();
		int hgt_atpre = getHgt();
		//appel
		delegate.climbRight();
		//inv
		super.checkInvariants();
		//post
		if (getEnvi().getCellNature(wdt_atpre, hgt_atpre+1) != Cell.PLT 
			&& getEnvi().getCellNature(wdt_atpre, hgt_atpre+1) != Cell.MTL) {
			if (getEnvi().getCellNature(wdt_atpre+1, hgt_atpre+1) != Cell.PLT 
				&& getEnvi().getCellNature(wdt_atpre+1, hgt_atpre+1) != Cell.MTL) {
				if (getEnvi().getCellContent(wdt_atpre+1, hgt_atpre+1).getCharacter() == null) {
					if (getWdt() != wdt_atpre+1 || getHgt() != hgt_atpre+1)
						throw new PostconditionError("climbRight : le guard n'a pas correctement grimpé à droite");
				}
			}
		}

	}

}