package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.decorators.CharacterDecorator;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
import loderunner.errors.PreconditionError;
import loderunner.services.CharacterService;
import loderunner.services.EnvironnementService;
import loderunner.services.ScreenService;

public class CharacterContract extends CharacterDecorator {

	public CharacterContract(CharacterService delegate) {
		super(delegate);
	}
	
	public void checkInvariants() {
		if(getEnvi().getCellNature(getWdt(), getHgt()) != Cell.EMP && 
		   getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HOL &&
		   getEnvi().getCellNature(getWdt(), getHgt()) != Cell.LAD &&
		   getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HDR)
			throw new InvariantError("le joueur n'est pas dans une case valide");
	}
	
	public EnvironnementService getEnvi() {
		//1.pre
		//none
		//2.run
		return super.getEnvi();
	}

	@Override
	public int getWdt() {
		//1.pre
		//none
		//2.run
		return super.getWdt();
	}
	
	@Override
	public int getHgt() {
		//1.pre
		//none
		//2.run
		return super.getHgt();
	}

	@Override
	public void setWdt(int wdt) {
		//1.pre
		if(wdt>getEnvi().getWidth()-1 || wdt<0) throw new PreconditionError("wdt n'est pas bon");
		//2.checkInvariants
		checkInvariants();
		//3.capture
		int wdt_capture = getWdt();
		//4.run		
		super.setWdt(wdt);
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(wdt_capture == getWdt()) throw new PostconditionError("wdt n'as pas été changé");
	}
	
	@Override
	public void setHgt(int hgt) {
		//1.pre
		if(hgt>getEnvi().getHeight()-1 || hgt<0) throw new PreconditionError("hgt n'est pas bon");
		//2.checkInvariants
		checkInvariants();
		//3.capture
		int hgt_capture = getHgt();
		//4.run
		super.setHgt(hgt);
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(hgt_capture == getHgt()) throw new PostconditionError("hgt n'as pas été changé");
		
	}
	
	@Override
	public int getId() {
		return delegate.getId();
	}
	
	@Override
	public void init(ScreenService s, int x, int y,int id) {
		//1.pre
		if(s.getCellNature(x, y) != Cell.EMP) {
			throw new PreconditionError("init : la case ou on veut init le player n'est pas Cell.EMP");
		}
		if(id < -1) throw new PreconditionError("init character : l'id est inférieur à -1");

		//2.checkInvariants
		//none
		//3.capture 
		//none
		//4.run
		super.init(s, x, y, id);
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(getHgt() != y || getWdt() != x) {
			throw new PostconditionError("init : le personnage à mal été initialisé au niveau de sa position");
		}

	}

	@Override
	public void goLeft() {
		//1.pre
		//none
		//2.checkInvariants
		checkInvariants();
		//3.capture
		int hgt_capture = getHgt();
		int wdt_capture = getWdt();
		//4.run
		super.goLeft();
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(getHgt() != hgt_capture) throw new PostconditionError("goLeft : la hauteur du personnage à changé");
		if(wdt_capture == 0 ) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage à franchi la gauche de l'écran");
			return;
		}
		if(getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.MTL || 
		   getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.PLT ||
		   getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.TLP) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage à franchi un mur");
		}
		if(getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD && 
		   getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.HDR) {
			if(getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.PLT && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.MTL && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.LAD && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.TLP) {
				if(getEnvi().getCellContent(wdt_capture-1, hgt_capture).getGuard() != null) {
					if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage n'était pas censé se déplacer");
				}
			}
		}
		if(getEnvi().getCellContent(wdt_capture-1, hgt_capture).getGuard() != null) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage à traversé un personnage");
		}
		if(wdt_capture != 0) {
			if(getEnvi().getCellNature(wdt_capture-1, hgt_capture) != Cell.MTL && 
			   getEnvi().getCellNature(wdt_capture-1, hgt_capture) != Cell.PLT &&
			   getEnvi().getCellNature(wdt_capture-1, hgt_capture) != Cell.TLP) {
				if((getEnvi().getCellNature(wdt_capture, hgt_capture) == Cell.LAD || 
					getEnvi().getCellNature(wdt_capture, hgt_capture) == Cell.HDR)
				   ||
				   (getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT || 
				    getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.MTL || 
				    getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.LAD ||
				    getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT)
				   ||
				   (getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() != null)) {
					  if(getId() == -1 ||  getEnvi().getCellContent(wdt_capture-1, hgt_capture).getGuard() == null) {
						  if(getWdt() != wdt_capture-1) throw new PostconditionError("goLeft : le personnage aurait du bouger");
					  }	
				}
			}
		}
	}

	@Override
	public void goRight() {
		//1.pre
		//2.checkInvariants
		checkInvariants();
		//3.capture
		int hgt_capture = getHgt();
		int wdt_capture = getWdt();
		//4.run
		super.goRight();
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(getHgt() != hgt_capture) throw new PostconditionError("goRight : la hauteur du personnage à changé");
		if(wdt_capture == getEnvi().getWidth()-1 ) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goRight : le personnage à franchi la droite de l'écran");
			return;
		}
		if(getEnvi().getCellNature(wdt_capture+1, hgt_capture) == Cell.MTL || 
		   getEnvi().getCellNature(wdt_capture+1, hgt_capture) == Cell.PLT ||
		   getEnvi().getCellNature(wdt_capture+1, hgt_capture) == Cell.TLP) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goRight : le personnage à franchi un mur");
		}
		if(getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD && 
		   getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.HDR) {
			if(getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.PLT && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.MTL && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.LAD && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.TLP) {
				if(getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() == null) {
					if(getWdt() != wdt_capture) throw new PostconditionError("goRight : le personnage n'était pas censé se déplacer");
				}
			}
		}
		if(getId() != 1 && getEnvi().getCellContent(wdt_capture+1, hgt_capture).getGuard() != null) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goRight : le guard à traversé un guard");
		}
		if(wdt_capture != getEnvi().getWidth()-1) {
			if(getEnvi().getCellNature(wdt_capture+1, hgt_capture) != Cell.MTL && 
			   getEnvi().getCellNature(wdt_capture+1, hgt_capture) != Cell.PLT &&
			   getEnvi().getCellNature(wdt_capture+1, hgt_capture) != Cell.TLP) {
				if((getEnvi().getCellNature(wdt_capture, hgt_capture) == Cell.LAD || 
					getEnvi().getCellNature(wdt_capture, hgt_capture) == Cell.HDR ) 
				   ||
				   (getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT || 
				    getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.MTL || 
				    getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.LAD ||
				    getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.TLP) 
				   ||
				   (getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() != null)) {
					  if(getId() == -1 ||  getEnvi().getCellContent(wdt_capture+1, hgt_capture).getGuard() == null) {
						  if(getWdt() != wdt_capture+1) throw new PostconditionError("goRight : le personnage aurait du bouger");
					  }	
				}
			}
		}
	}

	@Override
	public void goUp() {
		//1.pre
		//2.checkInvariants
		checkInvariants();
		//3.capture
		int wdt_capture = getWdt();
		int hgt_capture = getHgt();
		//4.run
		super.goUp();
		//5.checkInvariants
		checkInvariants();
		//6.post
		if (getWdt() != wdt_capture) throw new PostconditionError("goUp : la largeur du personnage a changé");
		if (hgt_capture == getEnvi().getHeight()-1) {
			if (getHgt() != hgt_capture) throw new PostconditionError("goUp : le personnage a dépassé le plafond");
			return;
		}
		if (getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD) {
			if (getHgt() != hgt_capture) throw new PostconditionError("goUp : le personnage est monté sans échelle");
		}else {
			if ((getId() == -1 || getEnvi().getCellContent(wdt_capture, hgt_capture+1).getGuard() == null)
			    &&
			    getEnvi().getCellNature(wdt_capture, hgt_capture+1) != Cell.MTL &&
			    getEnvi().getCellNature(wdt_capture, hgt_capture+1) != Cell.PLT &&
			    getEnvi().getCellNature(wdt_capture, hgt_capture+1) != Cell.TLP &&
			    getEnvi().getCellNature(wdt_capture, hgt_capture+1) != Cell.HOL) {
				if (getHgt() != hgt_capture+1) throw new PostconditionError("goUp : le personnage n'est pas monté apres un goUp permis");
			}
		}
	}

	@Override
	public void goDown() {
		//1.pre
		//2.checkInvariants
		checkInvariants();
		//3.capture
		int wdt_capture = getWdt();
		int hgt_capture = getHgt();
		//4.run
		super.goDown();
		//5.checkInvariants
		checkInvariants();
		//6.post
		if (getWdt() != wdt_capture) throw new PostconditionError("goDown : la largeur du personnage a été modifiée");
		if (hgt_capture == 1) {//peut etre pas utile car on le verifie en dessous avec le metal
			if (getHgt() != hgt_capture) throw new PostconditionError("goDown : le personnage est descendu alors qu'il ne pouvais pas");
			return;
		}
		if(getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT ||
		   getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.MTL ||
		   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.TLP) {
			if (getHgt() != hgt_capture) throw new PostconditionError("goDown : le personnage a pu descendre à travers la matière");
		}
		if(getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() != null) {
			if(getHgt() != hgt_capture)
				throw new PostconditionError("goDown : le joueur est descendu alors qu'il y avait un personnage");
		}
		if(hgt_capture != 1) {
			if(getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.MTL && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.PLT &&
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.TLP &&
			   (getId() == -1 ||  getEnvi().getCellContent(wdt_capture, hgt_capture+1).getGuard() == null)) {
				if (getHgt() != hgt_capture-1) throw new PostconditionError("goDown : le joueur n'est pas descendu alors qu'il devait");
			}
		}
	}


}
