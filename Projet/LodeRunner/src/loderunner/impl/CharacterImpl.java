package loderunner.impl;

import loderunner.services.CharacterService;
import loderunner.data.Cell;
import loderunner.errors.PostconditionError;
import loderunner.impl.EnvironnementImpl;
import loderunner.services.ScreenService;

public class CharacterImpl implements CharacterService{

	private EnvironnementImpl envi;
	private int hgt,wdt;
	
	@Override
	public EnvironnementImpl getEnvi() {
		return envi;
	}

	@Override
	public int getHgt() {
		return hgt;
	}

	@Override
	public int getWdt() {
		return wdt;
	}

	@Override
	public void init(ScreenService s, int x, int y) {
		envi = (EnvironnementImpl)s;
		wdt = x;
		hgt = y;
	}

	@Override
	public void goLeft() {
		if(wdt != 0) {
			if(getEnvi().getCellNature(wdt-1, hgt) != Cell.MTL && getEnvi().getCellNature(wdt-1, hgt) != Cell.PLT) {
				if((getEnvi().getCellNature(wdt-1, hgt) == Cell.LAD || getEnvi().getCellNature(wdt-1, hgt) == Cell.HDR)
				   || 
				   (getEnvi().getCellNature(wdt, hgt-1) == Cell.PLT || getEnvi().getCellNature(wdt, hgt-1) == Cell.MTL || getEnvi().getCellNature(wdt, hgt-1) == Cell.LAD)
				   ||
				   (getEnvi().getCellContent(wdt, hgt-1).getCharacter() != null)){
					if(getEnvi().getCellContent(wdt-1, hgt).getCharacter() == null) {
						if(getWdt() == wdt-1) wdt -= 1;
					}
				}
					
			}
		}
		
	}

	@Override
	public void goRight() {
		wdt += 1;
	}

	@Override
	public void goUp() {
		hgt +=1;
	}

	@Override
	public void goDown() {
		hgt -= 1;
	}
}
