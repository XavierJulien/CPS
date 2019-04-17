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
		if(getEnvi().getCellNature(getWdt(), getHgt()) == Cell.EMP || 
		   getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL ||
		   getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD ||
		   getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HDR){
			if(getEnvi().getCellContent(getWdt(), getHgt()).getCharacter() != super.delegate) {
				throw new InvariantError("le joueur dans la case de notre joueur n'est pas lui-même");
			}
			
		}
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
		if(s.getCellNature(x, y) != Cell.EMP) {
			throw new PreconditionError("init : la case ou on veut init le player n'est pas Cell.EMP");
		}
		//2.checkInvariants
		//none
		//3.capture
		//none
		//4.run
		super.init(s, x, y);
		//5.checkInvariants
		checkInvariants();
		//6.post
		if(getHgt() != y || getWdt() != x) {
			throw new PostconditionError("init : le personnage à mal été initialisé au niveau de sa position");
		}
		if(getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.MTL && 
		   getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.PLT) {
			throw new PostconditionError("init : le personnage devrait se situé sur une Cell.MTL ou Cell.PLT à l'innitialisation");
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
		}
		if(getEnvi().getCellNature(getWdt()-1, getHgt()) == Cell.MTL || 
		   getEnvi().getCellNature(getWdt()-1, getHgt()) == Cell.PLT) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage à franchi un mur");
		}
		if(getEnvi().getCellNature(getWdt(), getHgt()) != Cell.LAD && 
		   getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HDR) {
			if(getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.PLT && 
			   getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.MTL && 
			   getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.LAD) {
				if(getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() == null) {
					if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage n'était pas censé se déplacer");
				}
			}
		}
		if(getEnvi().getCellContent(getWdt()-1, getHgt()).getCharacter() != null) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage à traversé un personnage");
		}
		if(getWdt() != 0) {
			if(getEnvi().getCellNature(getWdt()-1, getHgt()) != Cell.MTL &&
			   getEnvi().getCellNature(getWdt()-1, getHgt()) != Cell.PLT) {
				if((getEnvi().getCellNature(getWdt()-1, getHgt()) == Cell.LAD ||
				   getEnvi().getCellNature(getWdt()-1, getHgt()) == Cell.HDR)
				   || 
				   (getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.PLT ||
				    getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.MTL ||
				    getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.LAD)
				   ||
				   (getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() != null)){
					if(getEnvi().getCellContent(getWdt()-1, getHgt()).getCharacter() == null) {
						if(getWdt() == wdt_capture-1) throw new PostconditionError("goLeft : le personnage aurait du bouger");
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
		if(getHgt() != hgt_capture) throw new PostconditionError("goLeft : la hauteur du personnage à changé");
		if(wdt_capture == getEnvi().getWidth()-1 ) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage à franchi la droite de l'écran");
		}
		if(getEnvi().getCellNature(getWdt()+1, getHgt()) == Cell.MTL || 
		   getEnvi().getCellNature(getWdt()+1, getHgt()) == Cell.PLT) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage à franchi un mur");
		}
		if(getEnvi().getCellNature(getWdt(), getHgt()) != Cell.LAD && 
		   getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HDR) {
			if(getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.PLT && 
			   getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.MTL && 
			   getEnvi().getCellNature(getWdt(), getHgt()-1) != Cell.LAD) {
				if(getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() == null) {
					if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage n'était pas censé se déplacer");
				}
			}
		}
		if(getEnvi().getCellContent(getWdt()+1, getHgt()).getCharacter() != null) {
			if(getWdt() != wdt_capture) throw new PostconditionError("goLeft : le personnage à traversé un personnage");
		}
		if(getWdt() != 0) {
			if(getEnvi().getCellNature(getWdt()+1, getHgt()) != Cell.MTL &&
			   getEnvi().getCellNature(getWdt()+1, getHgt()) != Cell.PLT) {
				if((getEnvi().getCellNature(getWdt()+1, getHgt()) == Cell.LAD ||
				   getEnvi().getCellNature(getWdt()+1, getHgt()) == Cell.HDR)
				   || 
				   (getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.PLT ||
				    getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.MTL ||
				    getEnvi().getCellNature(getWdt(), getHgt()-1) == Cell.LAD)
				   ||
				   (getEnvi().getCellContent(getWdt(), getHgt()-1).getCharacter() != null)){
					if(getEnvi().getCellContent(getWdt()+1, getHgt()).getCharacter() == null) {
						if(getWdt() == wdt_capture+1) throw new PostconditionError("goLeft : le personnage aurait du bouger");
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
		//4.run
		super.goUp();
		//5.checkInvariants
		checkInvariants();
		//6.post
		
	}

	@Override
	public void goDown() {
		//1.pre
		//2.checkInvariants
		checkInvariants();
		//3.capture
		//4.run
		super.goDown();
		//5.checkInvariants
		checkInvariants();
		//6.post
		
	}


}
