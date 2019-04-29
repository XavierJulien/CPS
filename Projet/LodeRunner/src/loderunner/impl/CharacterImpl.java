package loderunner.impl;

import loderunner.services.CharacterService;
import loderunner.contracts.EnvironnementContract;
import loderunner.data.Cell;
import loderunner.services.EnvironnementService;
import loderunner.services.ScreenService;

public class CharacterImpl implements CharacterService{

	private EnvironnementService envi;
	private int hgt,wdt;
	
	@Override
	public EnvironnementService getEnvi() {
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
		envi = (EnvironnementContract)s;
		wdt = x;
		hgt = y;
	}

	@Override
	public void goLeft() {
		if(wdt != 0) {
			if(getEnvi().getCellNature(wdt-1, hgt) != Cell.MTL && getEnvi().getCellNature(wdt-1, hgt) != Cell.PLT) {
				if((getEnvi().getCellNature(wdt, hgt) == Cell.LAD || getEnvi().getCellNature(wdt, hgt) == Cell.HDR)
				   ||
				   (getEnvi().getCellNature(wdt, hgt-1) == Cell.PLT || getEnvi().getCellNature(wdt, hgt-1) == Cell.MTL || getEnvi().getCellNature(wdt, hgt-1) == Cell.LAD)
				   ||
				   (getEnvi().getCellContent(wdt, hgt-1).getCharacter() != null)) {
					  if(getEnvi().getCellContent(wdt-1, hgt).getCharacter() == null) {
							wdt -= 1;
					  }	
				}
			}
		}
	}

	@Override
	public void goRight() {
		if(wdt != getEnvi().getWidth()-1) {
			if(getEnvi().getCellNature(wdt+1, hgt) != Cell.MTL && getEnvi().getCellNature(wdt+1, hgt) != Cell.PLT) {
				if((getEnvi().getCellNature(wdt, hgt) == Cell.LAD || getEnvi().getCellNature(wdt, hgt) == Cell.HDR )
				   ||
				   (getEnvi().getCellNature(wdt, hgt-1) == Cell.PLT || getEnvi().getCellNature(wdt, hgt-1) == Cell.MTL || getEnvi().getCellNature(wdt, hgt-1) == Cell.LAD)
				   ||
				   (getEnvi().getCellContent(wdt, hgt-1).getCharacter() != null)) {
					  if(getEnvi().getCellContent(wdt+1, hgt).getCharacter() == null) {
							wdt += 1;
					  }	
				}
			}
		}
	}

	@Override
	public void goUp() {
		if(hgt != getEnvi().getHeight()-1 && getEnvi().getCellNature(wdt, hgt) == Cell.LAD) {
			if(getEnvi().getCellNature(wdt, hgt+1) != Cell.MTL 
					&& getEnvi().getCellNature(wdt, hgt+1) != Cell.PLT && getEnvi().getCellNature(wdt, hgt+1) != Cell.HOL && getEnvi().getCellContent(wdt, hgt+1).getCharacter() == null) {
				hgt +=1;
			}
		}
	}

	@Override
	public void goDown() {
		if(hgt != 1) {
			if(getEnvi().getCellNature(wdt, hgt-1) != Cell.MTL && getEnvi().getCellNature(wdt, hgt-1) != Cell.PLT && getEnvi().getCellContent(wdt, hgt-1).getCharacter() == null) {
				hgt -=1;
			}
		}
	}
}
