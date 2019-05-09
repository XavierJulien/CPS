package loderunner.impl;

import loderunner.services.CharacterService;
import loderunner.contracts.EnvironnementContract;
import loderunner.services.EnvironnementService;
import loderunner.services.ScreenService;

public class CharacterImplBug implements CharacterService{

	private EnvironnementService envi;
	private int hgt,wdt;
	private int id;
	
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
	public int getId() {
		return id;
	}

	@Override
	public void setPos(int wdt, int hgt) {
		this.wdt = wdt;
		this.hgt = hgt;
	}
	
	@Override
	public void init(ScreenService s, int x, int y, int id) {
		envi = (EnvironnementContract)s;
		wdt = x;
		hgt = y;
		this.id = id;
	}

	@Override
	public void goLeft() {
		wdt -= 1;
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
		hgt -=1;
	}
}
