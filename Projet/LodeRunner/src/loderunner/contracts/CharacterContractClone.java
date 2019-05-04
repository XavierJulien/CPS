package loderunner.contracts;

import loderunner.data.Cell;
import loderunner.decorators.CharacterDecorator;
import loderunner.errors.InvariantError;
import loderunner.errors.PostconditionError;
import loderunner.services.CharacterService;
import loderunner.services.EnvironnementService;
import loderunner.services.ScreenService;

public class CharacterContractClone extends CharacterDecorator {

	public CharacterContractClone(CharacterService delegate) {
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
	public int getHgt() {
		//1.pre
		//none
		//2.run
		return super.getHgt();
	}

	@Override
	public int getWdt() {
		//1.pre
		//none
		//2.run
		return super.getWdt();
	}

	@Override
	public void init(ScreenService s, int x, int y) {
		//1.pre
		/*if(s.getCellNature(x, y) != Cell.EMP) {
			throw new PreconditionError("init : la case ou on veut init le player n'est pas Cell.EMP");
		}*/
		//2.checkInvariants
		//none
		//3.capture
		//none
		//4.run
		super.init(s, x, y);
		this.getEnvi().getCellContent(x, y).setCharacter(this);
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(getHgt() != y || getWdt() != x) {
			throw new PostconditionError("init : le personnage � mal �t� initialis� au niveau de sa position");
		}
		/*if(getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.MTL && 
		   getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.PLT) {
			throw new PostconditionError("init : le personnage devrait se situ� sur une Cell.MTL ou Cell.PLT � l'initialisation");
		}*/
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
		if(getHgt() != hgt_capture) throw new PostconditionError("goLeft : la hauteur du personnage � chang�");
		if(wdt_capture == 0 ) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage � franchi la gauche de l'�cran");
			return;
		}
		if(getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.MTL || 
		   getEnvi().getCellNature(wdt_capture-1, hgt_capture) == Cell.PLT) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage � franchi un mur");
		}
		if(getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD && 
		   getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.HDR) {
			if(getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.PLT && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.MTL && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.LAD) {
				if(getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() == null) {
					if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage n'�tait pas cens� se d�placer");
				}
			}
		}
		if(getEnvi().getCellContent(wdt_capture-1, hgt_capture).getCharacter() != null) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage � travers� un personnage");
		}
		if(wdt_capture != 0) {
			if(getEnvi().getCellNature(wdt_capture-1, hgt_capture) != Cell.MTL && getEnvi().getCellNature(wdt_capture-1, hgt_capture) != Cell.PLT) {
				if((getEnvi().getCellNature(wdt_capture, hgt_capture) == Cell.LAD || getEnvi().getCellNature(wdt_capture, hgt_capture) == Cell.HDR)
				   ||
				   (getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT || getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.MTL || getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.LAD)
				   ||
				   (getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() != null)) {
					  if(getEnvi().getCellContent(wdt_capture-1, hgt_capture).getCharacter() == null && getEnvi().getCellContent(wdt_capture-1, hgt_capture).getGuard() == null) {
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
		if(getHgt() != hgt_capture) throw new PostconditionError("goRight : la hauteur du personnage � chang�");
		if(wdt_capture == getEnvi().getWidth()-1 ) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goRight : le personnage � franchi la droite de l'�cran");
			return;
		}
		if(getEnvi().getCellNature(wdt_capture+1, hgt_capture) == Cell.MTL || 
		   getEnvi().getCellNature(wdt_capture+1, hgt_capture) == Cell.PLT) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goRight : le personnage � franchi un mur");
		}
		if(getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD && 
		   getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.HDR) {
			if(getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.PLT && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.MTL && 
			   getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.LAD) {
				if(getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() == null) {
					if(getWdt() != wdt_capture) throw new PostconditionError("goRight : le personnage n'�tait pas cens� se d�placer");
				}
			}
		}
		if(getEnvi().getCellContent(wdt_capture+1, hgt_capture).getCharacter() != null) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goRight : le personnage � travers� un personnage");
		}
		if(wdt_capture != getEnvi().getWidth()-1) {
			if(getEnvi().getCellNature(wdt_capture+1, hgt_capture) != Cell.MTL && getEnvi().getCellNature(wdt_capture+1, hgt_capture) != Cell.PLT) {
				if((getEnvi().getCellNature(wdt_capture, hgt_capture) == Cell.LAD || getEnvi().getCellNature(wdt_capture, hgt_capture) == Cell.HDR )
				   ||
				   (getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT || getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.MTL || getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.LAD)
				   ||
				   (getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() != null)) {
					  if(getEnvi().getCellContent(wdt_capture+1, hgt_capture).getCharacter() == null && getEnvi().getCellContent(wdt_capture+1, hgt_capture).getGuard() == null) {
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
		if (getWdt() != wdt_capture) throw new PostconditionError("goUp : la largeur du personnage a chang�");
		if (hgt_capture == getEnvi().getHeight()-1) {
			if (getHgt() != hgt_capture) throw new PostconditionError("goUp : le personnage a d�pass� le plafond");
			return;
		}
		if (getEnvi().getCellNature(wdt_capture, hgt_capture) != Cell.LAD) {
			if (getHgt() != hgt_capture) throw new PostconditionError("goUp : le personnage est mont� sans �chelle");
		}else {
			if (getEnvi().getCellContent(wdt_capture, hgt_capture+1).getCharacter() == null
				&& 
				getEnvi().getCellContent(wdt_capture, hgt_capture+1).getGuard() == null
			    &&
			    getEnvi().getCellNature(wdt_capture, hgt_capture+1) != Cell.MTL
			    &&
			    getEnvi().getCellNature(wdt_capture, hgt_capture+1) != Cell.PLT
			    &&
			    getEnvi().getCellNature(wdt_capture, hgt_capture+1) != Cell.HOL) {
				if (getHgt() != hgt_capture+1) throw new PostconditionError("goUp : le personnage n'est pas mont� apres un goUp permis");
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
		if (getWdt() != wdt_capture) throw new PostconditionError("goDown : la largeur du personnage a �t� modifi�e");
		if (hgt_capture == 1) {//peut etre pas utile car on le verifie en dessous avec le metal
			if (getHgt() != hgt_capture) throw new PostconditionError("goDown : le personnage est descendu alors qu'il ne pouvais pas");
			return;
		}
		if(getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.PLT 
		   ||
		   getEnvi().getCellNature(wdt_capture, hgt_capture-1) == Cell.MTL) {
			if (getHgt() != hgt_capture) throw new PostconditionError("goDown : le personnage a pu descendre � travers la mati�re");
		}
		if(getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() != null)
			throw new PostconditionError("goDown : le joueur est descendu alors qu'il y avait un personnage");
		if(hgt_capture != 1) {
			if(getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.MTL 
			   && getEnvi().getCellNature(wdt_capture, hgt_capture-1) != Cell.PLT 
			   && getEnvi().getCellContent(wdt_capture, hgt_capture-1).getCharacter() == null
			   && getEnvi().getCellContent(wdt_capture, hgt_capture-1).getGuard() == null) {
				if (getHgt() != hgt_capture-1) throw new PostconditionError("goDown : le joueur n'est pas descendu alors qu'il devait");
			}
		}
	}


}
